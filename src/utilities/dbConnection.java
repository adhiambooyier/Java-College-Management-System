/*
 *  Student Number : 17260
 *  Student Name : Mirza Usman
 */
package utilities;

import com.sun.jndi.ldap.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author user
 */
public class dbConnection {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/vgc_17260";
        String username = "root";
        String password = "1017";

        System.out.println("Connecting database...");

        try (java.sql.Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

}
