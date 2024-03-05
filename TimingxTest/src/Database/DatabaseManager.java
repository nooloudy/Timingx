package Database;

import Accounts.Admin;
import Accounts.TimingxUser;
import Accounts.User;
import Goals.Goal;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DatabaseManager implements Serializable {

    private static final String DATABASE_FILE = "database.ser";
    private static final String PREFERENCES_FILE = "preferences.ser";
    private static final String GOALS_FILE = "goals.ser";
    private static final long serialVersionUID = 1L;
    private static List<User> users = new ArrayList<>();
    private static User loggedInUser;
    private static Map<Integer, TimingxUser> userPreferences = new HashMap<>();
    private static List<Goal> goals = new ArrayList<>();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(DatabaseManager::shutdown));
    }

    private static void shutdown() {
        serializeDatabase();
        System.out.println("Exiting the application. Database serialized.");
    }

    public static void serializeDatabase() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATABASE_FILE))) {
            oos.writeObject(users);
            oos.writeObject(loggedInUser);
            System.out.println("Database serialized successfully.");
        } catch (IOException e) {
            System.err.println("Error during database serialization: " + e.getMessage());
        }
    }

    public static void deserializeDatabase() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATABASE_FILE))) {
        	Object readObject = ois.readObject();
        	if (readObject instanceof List<?>) {
        	    users = (List<User>) readObject;
        	}

        	readObject = ois.readObject();
        	if (readObject instanceof User) {
        	    loggedInUser = (User) readObject;
        	}

            System.out.println("Database deserialized successfully.");
        } catch (EOFException e) {
            initializeEmptyDatabase();
            System.out.println("Database file is empty. Initialized with empty data.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void initializeEmptyDatabase() {
        users = new ArrayList<>();
        loggedInUser = null;
    }

    public static User login() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.next();

        System.out.print("Enter password: ");
        String password = scanner.next();

        for (User user : users) {
            if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                loggedInUser = user;
                return loggedInUser;
            }
        }

        System.out.println("Login failed. User not found.");
        return null;
    }

    public static void addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
            System.out.println("User added: " + user.toString());
            serializeDatabase();
        } else {
            System.out.println("User already exists.");
        }
    }

    public static void deleteUser(User user) {
        if (users.contains(user)) {
            users.remove(user);
            System.out.println("User deleted: " + user.toString());
            serializeDatabase();
        } else {
            System.out.println("User not found.");
        }
    }

    public static void updateUser(User user, String newPassword) {
        if (users.contains(user)) {
            user.setPassword(newPassword);
            System.out.println("User updated: " + user.toString());
            serializeDatabase();
        } else {
            System.out.println("User not found.");
        }
    }

    public static void displayAllUsers() {
        System.out.println("All Users:");
        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public static User findUserById(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    public static List<User> getUsers() {
        return users;
    }

    public static void initializeDatabaseIfEmpty() {
        deserializeDatabase();

        if (users.isEmpty()) {
            Admin admin = new Admin(1, "admin", "admin");
            addUserIfNotExists(admin);

            TimingxUser nooloudy = new TimingxUser(2, "nooloudy", "nooloudy");
            addUserIfNotExists(nooloudy);
        }
    }

    private static void addUserIfNotExists(User user) {
        if (!users.contains(user)) {
            addUser(user);
        }
    }
    
    
    
    public static void savePreferences(int userId, TimingxUser preferences) {
        userPreferences.put(userId, preferences);
        serializePreferences();
        System.out.println("User preferences saved successfully.");
    }

    private static void serializePreferences() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PREFERENCES_FILE))) {
            oos.writeObject(userPreferences);
            System.out.println("Preferences serialized successfully.");
        } catch (IOException e) {
            System.err.println("Error during preferences serialization: " + e.getMessage());
        }
    }

    private static void deserializePreferences() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PREFERENCES_FILE))) {
            Object readObject = ois.readObject();
            if (readObject instanceof Map<?, ?>) {
                userPreferences = (Map<Integer, TimingxUser>) readObject;
            }
            System.out.println("Preferences deserialized successfully.");
        } catch (EOFException e) {
            System.out.println("Preferences file is empty. Initializing with empty data.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static TimingxUser getPreferences(int userId) {
        return userPreferences.get(userId);
    }

    public static void initializePreferences() {
        deserializePreferences();
    }
    
    
    public static void saveGoals(User user) {
    	serializeGoals(user);
        System.out.println("Goals successfully saved.");
    }
    private static void serializeGoals(User user) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GOALS_FILE))) {
            oos.writeObject(user.getGoals());
            System.out.println("User goals serialized successfully.");
        } catch (IOException e) {
            System.err.println("Error during user goals serialization: " + e.getMessage());
        }
    }
    public static void deserializeGoals() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(GOALS_FILE))) {
            Object readObject = ois.readObject();
            if (readObject instanceof List<?>) {
                goals = (List<Goal>) readObject;
            }

            System.out.println("Goals deserialized successfully.");
        } catch (EOFException e) {
            System.out.println("Goals file is empty. No goals loaded.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void initializeGoals() {
        deserializeGoals();
    }
}
