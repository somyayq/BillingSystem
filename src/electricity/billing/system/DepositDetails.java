package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class DepositDetails extends JFrame implements ActionListener {

    Choice meternumber, cmonth;
    JTable table;
    JButton search, print;

    DepositDetails() {
        super("Deposit Details");

        setSize(700, 700); // Set the window size
        setLocation(400, 100); // Set window location

        setLayout(null);
        getContentPane().setBackground(Color.WHITE); // Set background color

        // Label for meter number search
        JLabel lblmeternumber = new JLabel("Search By Meter Number");
        lblmeternumber.setBounds(20, 20, 150, 20);
        add(lblmeternumber);

        // Dropdown for meter number selection
        meternumber = new Choice();
        meternumber.setBounds(180, 20, 150, 20);
        add(meternumber);

        // Populate meter number dropdown from database
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("Select * from customer");
            while (rs.next()) {
                meternumber.add(rs.getString("meter_no"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Label for month search
        JLabel lblmonth = new JLabel("Search By Month");
        lblmonth.setBounds(400, 20, 100, 20);
        add(lblmonth);

        // Dropdown for selecting month
        cmonth = new Choice();
        cmonth.setBounds(510, 20, 150, 20);
        cmonth.add("January");
        cmonth.add("Feburary");
        cmonth.add("March");
        cmonth.add("April");
        cmonth.add("May");
        cmonth.add("June");
        cmonth.add("July");
        cmonth.add("August");
        cmonth.add("September");
        cmonth.add("Octuber");
        cmonth.add("November");
        cmonth.add("December");
        add(cmonth);

        table = new JTable(); // Table to display bill data

        // Load all bill data initially
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("Select * from bill");
            table.setModel(DbUtils.resultSetToTableModel(rs)); // Display result in table
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add scroll pane for table
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(0, 100, 700, 600);
        add(sp);

        // Search button to filter data by meter and month
        search = new JButton("Search");
        search.setBounds(20, 70, 80, 20);
        search.addActionListener(this);
        add(search);

        // Print button to print table
        print = new JButton("Print");
        print.setBounds(120, 70, 80, 20);
        print.addActionListener(this);
        add(print);

        setVisible(true); // Show the frame
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            // Construct query to filter based on selected meter number and month
            String query = "Select * from bill where meter_no='" + meternumber.getSelectedItem() + "' and month='" + cmonth.getSelectedItem() + "'";

            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(rs)); // Update table with filtered data
            } catch (Exception e) {
                // Handle exceptions silently here (can be improved)
            }
        } else {
            try {
                table.print(); // Print the table
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new DepositDetails(); // Run the program
    }
}
