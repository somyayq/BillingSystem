package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class CustomerDetails extends JFrame implements ActionListener {

    Choice meternumber, cmonth;
    JTable table;
    JButton search, print;

    CustomerDetails() {
        super("Customer Details");

        setSize(1200, 650); // Set window size
        setLocation(200, 150); // Set window location on screen

        table = new JTable(); // Create a table to display customer data

        // Fetch all customer records from the database and display them in the table
        try {
            Conn c = new Conn(); // Create database connection
            ResultSet rs = c.s.executeQuery("Select * from customer"); // Execute query to get all customers

            table.setModel(DbUtils.resultSetToTableModel(rs)); // Use DbUtils to convert ResultSet to TableModel
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane sp = new JScrollPane(table); // Add table to scroll pane
        add(sp); // Add scroll pane to the frame

        print = new JButton("Print"); // Button to print the table
        print.addActionListener(this); // Add action listener for print button
        add(print, "South"); // Add print button at the bottom

        setVisible(true); // Make the window visible
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            table.print(); // Print the table content when button is clicked
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CustomerDetails(); // Launch the application
    }
}
