
package Accounts;

import Database.DatabaseManager;
import java.io.Serial;

import java.io.Serializable;
import java.util.Objects;
import java.util.Scanner;

public class Admin extends User implements Serializable {

    /**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;
	
	public Admin() {
		
	}
	public Admin(int userId, String username, String password) {
        super(userId, username, password);
    }
	 @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null || getClass() != obj.getClass()) return false;
	        
	        Admin otherAdmin = (Admin) obj;
	        return getUserId() == otherAdmin.getUserId() && 
	               getUsername().equals(otherAdmin.getUsername()) && 
	               getPassword().equals(otherAdmin.getPassword());
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(getUserId(), getUsername(), getPassword());
	    }

    public void menu(Scanner scanner) {
        int choice;

        do {
        	System.out.println("");
            System.out.println("----Admin Main Menu----");
            System.out.println("1. Add User");
            System.out.println("2. Delete User");
            System.out.println("3. Update User");
            System.out.println("4. Display All Users");
            System.out.println("5. Logout");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addUser();
                    DatabaseManager.serializeDatabase();
                    break;
                case 2:
                    deleteUser();
                    DatabaseManager.serializeDatabase();
                    break;
                case 3:
                    updateUser();
                    DatabaseManager.serializeDatabase();
                    break;
                case 4:
                    displayAllUsers();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 5);

    }

    private void addUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter new user ID: ");
        int userId = scanner.nextInt();

        System.out.print("Enter new username: ");
        String username = scanner.next();

        System.out.print("Enter new password: ");
        String password = scanner.next();

        User newUser = new TimingxUser(userId, username, password);
        DatabaseManager.addUser(newUser);
    }

    private void deleteUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter user ID to delete: ");
        int userIdToDelete = scanner.nextInt();

        User userToDelete = DatabaseManager.findUserById(userIdToDelete);
        if (userToDelete != null) {
            DatabaseManager.deleteUser(userToDelete);
        } else {
            System.out.println("User not found.");
        }
    }

    private void updateUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter user ID to update: ");
        int userIdToUpdate = scanner.nextInt();

        User userToUpdate = DatabaseManager.findUserById(userIdToUpdate);
        if (userToUpdate != null) {
            System.out.print("Enter new password: ");
            String newPassword = scanner.next();

            DatabaseManager.updateUser(userToUpdate, newPassword);
        } else {
            System.out.println("User not found.");
        }
    }

    private void displayAllUsers() {
        DatabaseManager.displayAllUsers();
    }
    
}
