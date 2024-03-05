// Пакет Accounts
package Accounts;

import java.io.Serial;
import java.io.Serializable;

public class AuthenticationManager implements Serializable{
	
	@Serial
	private static final long serialVersionUID = 1L;
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    public static User authenticate(String username, String password) {

        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            return new Admin(1, ADMIN_USERNAME, ADMIN_PASSWORD);
        }

        if (username.equals("nooloudy") && password.equals("nooloudy")) {
            return new TimingxUser(2, username, password);
        }

        System.out.println("Authentication failed. User not found.");
        return null;
    }
}
