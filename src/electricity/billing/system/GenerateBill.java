package electricity.billing.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class GenerateBill extends JFrame implements ActionListener {

    String meter;
    JButton bill;
    Choice cmonth;
    JTextArea area;

    GenerateBill(String meter) {
        this.meter = meter;

        setSize(500, 800); // Set window size
        setLocation(550, 30); // Set window position

        setLayout(new BorderLayout()); // Use BorderLayout

        JPanel panel = new JPanel();

        JLabel heading = new JLabel("Generating Bill");
        JLabel meternumber = new JLabel(meter); // Display meter number

        // Dropdown for selecting month
        cmonth = new Choice();
        cmonth.add("January");
        cmonth.add("February");
        cmonth.add("March");
        cmonth.add("April");
        cmonth.add("May");
        cmonth.add("June");
        cmonth.add("July");
        cmonth.add("August");
        cmonth.add("September");
        cmonth.add("October");
        cmonth.add("November");
        cmonth.add("December");

        // Text area to display bill
        area = new JTextArea(50, 15);
        area.setText("\n\n\t--------Click on the-------\n\t Generate Bill Button to get\n\tthe bill of Selected Month");
        area.setFont(new Font("Serif", Font.ITALIC, 20));

        JScrollPane pane = new JScrollPane(area); // Scroll pane for text area

        bill = new JButton("Generate Bill");
        bill.addActionListener(this); // Add event listener to button

        panel.add(heading);
        panel.add(meternumber);
        panel.add(cmonth);
        add(panel, "North"); // Add top panel

        add(pane, "Center"); // Add text area to center
        add(bill, "South"); // Add button to bottom

        setVisible(true); // Display window
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            Conn c = new Conn(); // Database connection
            String month = cmonth.getSelectedItem(); // Get selected month

            // Header of the bill
            area.setText("\tReliance Power Limited\n"
                       + "ELECTRICITY BILL GENERATED FOR THE MONTH\n\tOF " + month + ", 2025\n\n\n");

            // Fetch customer details
            ResultSet rs = c.s.executeQuery("select * from customer where meter_no = '"+meter+"'");
            if (rs.next()) {
                area.append("\n    Customer Name: " + rs.getString("name"));
                area.append("\n    Meter Number : " + rs.getString("meter_no"));
                area.append("\n    Address      : " + rs.getString("address"));
                area.append("\n    City         : " + rs.getString("city"));
                area.append("\n    State        : " + rs.getString("state"));
                area.append("\n    Email        : " + rs.getString("email"));
                area.append("\n    Phone        : " + rs.getString("phone"));
                area.append("\n------------------------------------------------");
                area.append("\n");
            }

            // Fetch meter info
            rs = c.s.executeQuery("select * from meter_info where meter_no = '"+meter+"'");
            if (rs.next()) {
                area.append("\n    Meter Location: " + rs.getString("meter_location"));
                area.append("\n    Meter Type    : " + rs.getString("meter_type"));
                area.append("\n    Phase Code    : " + rs.getString("phase_code"));
                area.append("\n    Bill Type     : " + rs.getString("bill_type"));
                area.append("\n    Days          : " + rs.getString("days"));
                area.append("\n------------------------------------------------");
                area.append("\n");
            }

            // Fetch tax details
            rs = c.s.executeQuery("select * from tax");
            if (rs.next()) {
                area.append("\n");
                area.append("\n    Cost Per Unit  : " + rs.getString("cost_per_unit"));
                area.append("\n    Meter Rent     : " + rs.getString("cost_per_unit"));
                area.append("\n    Service Charge    : " + rs.getString("service_charge"));
                area.append("\n    Service TAx      : " + rs.getString("service_charge"));
                area.append("\n    Swacch Bharat Cess       : " + rs.getString("swacch_bharat_cess"));
                area.append("\n    Fixed Tax       : " + rs.getString("fixed_tax"));
                area.append("\n------------------------------------------------");
                area.append("\n");
            }

            // Fetch billing data for selected month
            rs = c.s.executeQuery("select * from bill where meter_no='"+meter+"' and month='"+month+"'");
            if (rs.next()) {
                area.append("\n");
                area.append("\n    Current Month : " + rs.getString("month"));
                area.append("\n    Units Consumed     : " + rs.getString("units"));
                area.append("\n    Total Charges   : " + rs.getString("totalbill"));
                area.append("\n------------------------------------------------");
                area.append("\n    Total Payable      : " + rs.getString("totalbill"));
                area.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log any errors
        }
    }

    public static void main(String[] args) {
        new GenerateBill(""); // Launch window
    }
}
