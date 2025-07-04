package electricity.billing.system;

import java.sql.*; // Import JDBC classes

public class Conn {
    
    Connection c; // JDBC Connection object
    Statement s;  // Statement object to execute SQL queries

    Conn() {
        try {
            // Establish connection to the MySQL database "ebs" with username and password
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "Solan@8112004");
            
            // Create a Statement object to run SQL queries
            s = c.createStatement();
        } catch (Exception e) {
            // Print stack trace if connection fails
            e.printStackTrace();
        }
    }   
}
