package Test;

import Database.DatabaseManager;

import java.io.Serial;
import java.io.Serializable;
import java.util.Scanner;

import Accounts.User;

public class Timingx implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        DatabaseManager.initializeDatabaseIfEmpty();
        DatabaseManager.initializePreferences();
        DatabaseManager.initializeGoals();
        
        System.out.println("Existing Users:");
        for (User user : DatabaseManager.getUsers()) {
            System.out.println(user.toString());
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            User authenticatedUser = DatabaseManager.login();

            if (authenticatedUser != null) {
                System.out.println("Login successful!");
                System.out.println(authenticatedUser.toString());

                // Передаем scanner в menu
                authenticatedUser.menu(scanner);
                break;
            } else {
                System.out.println("Login failed.");
            }
        }

        // Закрываем Scanner после использования
        scanner.close();

        DatabaseManager.serializeDatabase();
    }
}
