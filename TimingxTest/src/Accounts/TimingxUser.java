package Accounts;

import java.io.Serial;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import Database.DatabaseManager;
import Goals.Goal;
import Schedule.ScheduleManager;

public class TimingxUser extends User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    public TimingxUser(int userId, String username, String password) {
        super(userId, username, password);
    }

    
    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }
    
    

    @Override
    public void menu(Scanner scanner) {
        int choice;

        do {
            System.out.println("");
            System.out.println("----Welcome to Timingx----");
            System.out.println("1. View Profile");
            System.out.println("2. Update Password");
            System.out.println("3. Create Calendar");
            System.out.println("4. View Calendar");
            System.out.println("5. Create Goal");
            System.out.println("6. Remove Goal");
            System.out.println("7. Help");
            System.out.println("8. Logout");

            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewProfile();
                    break;
                case 2:
                    updatePassword(scanner);
                    break;
                case 3:
                    System.out.println("");
                    System.out.println("----Create Calendar----");
                    createPreferences(scanner);
                    break;
                case 4:
                    System.out.println("");
                    System.out.println("----View Calendar----");
                    System.out.print("");

                    User user = DatabaseManager.getLoggedInUser();
                    if (user instanceof TimingxUser) {
                        TimingxUser loggedInUser = (TimingxUser) user;
                        viewCalendar();
                    } else {
                        System.out.println("Error: The logged-in user is not of type TimingxUser.");
                    }
                    break;
                case 5:
                	createGoal(scanner);
                	break;
                case 6:
                	removeGoal(scanner);
                	break;
                case 7:
                    System.out.println("----Help----");
                    break;
                case 8:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 8);
    }

    private void viewProfile() {
        System.out.println("User Profile:");
        System.out.println(this.toString());
    }

    private void updatePassword(Scanner scanner) {
        System.out.print("Enter new password: ");
        String newPassword = scanner.next();

        this.setPassword(newPassword);
        System.out.println("Password updated successfully!");
    }

    private void createPreferences(Scanner scanner) {
        System.out.print("Do you like reading books? (Yes/No): ");
        scanner.nextLine();
        String bookChoice = scanner.nextLine().toLowerCase();
        setBookPreference(bookChoice);

        if (bookChoice.equals("yes")) {
            System.out.print("When do you prefer to read books? (morning/day/night): ");
            String bookTimePreference = scanner.nextLine();
            setBookTimePreference(bookTimePreference);
        }

        System.out.print("Do you like watching movies? (Yes/No): ");
        String movieChoice = scanner.nextLine().toLowerCase();
        setMoviePreference(movieChoice);

        if (movieChoice.equals("yes")) {
            System.out.print("When do you prefer to watch movies? (morning/day/night): ");
            String movieTimePreference = scanner.nextLine();
            setMovieTimePreference(movieTimePreference);
        }

        System.out.print("Are you a student or an employee? (Student/Employee): ");
        String occupationPreference = scanner.nextLine();
        setOccupationPreference(occupationPreference);

        if (occupationPreference.equalsIgnoreCase("student")) {
            System.out.print("When do you prefer to study? (morning/day/night): ");
            String studyTimePreference = scanner.nextLine();
            setStudyTimePreference(studyTimePreference);
        }

        if (occupationPreference.equalsIgnoreCase("employee")) {
            System.out.print("Tell us about your workday? (morning/day/night): ");
            String workDayPreference = scanner.nextLine();
            setWorkDayPreference(workDayPreference);
        }

        DatabaseManager.savePreferences(getUserId(), this);
        System.out.println("Calendar preferences updated and saved successfully!");

        System.out.println("");
        System.out.println("So, we already saved all your preferences. Continue?(yes/no)");
        String continueChoice = scanner.nextLine().toLowerCase();
        if (continueChoice.equals("yes")) {
            createGoal(scanner);
        } else {
            menu(scanner); 
        }
    }

    private void createGoal(Scanner scanner) {
        while (true) {
            System.out.println("----Create Goal----");

            System.out.println("1. Read Books");
            System.out.println("2. Watch a Movie");
            System.out.println("3. Study/Work");
            System.out.println("9. Continue");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice == 9) {
                menu(scanner);
            }

            scanner.nextLine();
            System.out.print("Tell a short description about the aim: ");
            String description = scanner.nextLine();

            System.out.print("DEADLINE: Enter the date (yyyy-MM-dd): ");
            String dateString = scanner.next();
            LocalDate deadline = LocalDate.parse(dateString);

            Goal goal = new Goal(getGoalName(choice), description, deadline);
            addGoal(goal);

            // Save the updated list of goals to the database
            DatabaseManager.saveGoals((TimingxUser) this); // Save the updated goals to the database

            System.out.println("Goal created successfully!");
        }
    }

    private String getGoalName(int choice) {
        switch (choice) {
            case 1:
                return "Read Books";
            case 2:
                return "Watch a Movie";
            case 3:
                return "Study/Work";
            default:
                return "Unknown";
        }
    }

    private void viewCalendar() {
        TimingxUser loggedInUser = (TimingxUser) DatabaseManager.getLoggedInUser();

        List<Goal> userGoals = loggedInUser.getGoals();
        Collections.sort(userGoals, Collections.reverseOrder());

        LocalDate maxDeadline = userGoals.isEmpty() ? LocalDate.now() : userGoals.get(0).getDeadline();

        List<String> extendedSchedule;
        extendedSchedule = ScheduleManager.generateSchedule(LocalDate.now(), maxDeadline, loggedInUser);
        
        for (Goal goal : userGoals) {
            System.out.println(goal.getDeadline() + " - " + goal.getGoalName() + ": " + goal.getDescription());
        }

        ScheduleManager.displaySchedule(extendedSchedule);
    }

    
    public void removeGoal(Scanner scanner) {
        List<Goal> userGoals = getGoals(); 

        if (userGoals.isEmpty()) {
            System.out.println("No goals to remove.");
            return;
        }

        System.out.println("Current goals:");
        for (int i = 0; i < userGoals.size(); i++) {
            System.out.println((i + 1) + ". " + userGoals.get(i).getDescription());
        }

        System.out.print("Enter the number of the goal you want to remove: ");
        int goalNumber = scanner.nextInt();

        if (goalNumber >= 1 && goalNumber <= userGoals.size()) {
            Goal goalToRemove = userGoals.get(goalNumber - 1);
            userGoals.remove(goalToRemove);
            System.out.println("Goal removed successfully!");

            setGoals(userGoals); 

            DatabaseManager.saveGoals(this); 
        } else {
            System.out.println("Invalid goal number. Please try again.");
        }
    }
    

    
    
}
