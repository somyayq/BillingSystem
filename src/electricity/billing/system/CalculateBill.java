package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;

public class CalculateBill extends JFrame implements ActionListener {

    JTextField tfname, tfaddress, tfunits, tfcity, tfemail, tfphone;
    JButton next, cancel;
    JLabel lblname, labeladdress;
    Choice meternumber, cmonth;

    CalculateBill() {
        super("Bill Calculator");
        setSize(700, 500);
        setLocation(400, 150);

        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBackground(new Color(173, 216, 230)); // Light blue background
        add(p);

        JLabel heading = new JLabel("Calculate Electricity Bill");
        heading.setBounds(100, 10, 400, 25);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 24));
        p.add(heading);

        JLabel lblmeternumber = new JLabel("Meter Number");
        lblmeternumber.setBounds(100, 80, 100, 20);
        p.add(lblmeternumber);

        meternumber = new Choice();

        // Populate meter numbers from customer table
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from customer");
            while (rs.next()) {
                meternumber.add(rs.getString("meter_no"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        meternumber.setBounds(240, 80, 200, 20);
        p.add(meternumber);

        JLabel lblmeterno = new JLabel("Name");
        lblmeterno.setBounds(100, 120, 100, 20);
        p.add(lblmeterno);

        lblname = new JLabel("");
        lblname.setBounds(240, 120, 100, 20);
        p.add(lblname);

        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(100, 160, 100, 20);
        p.add(lbladdress);

        labeladdress = new JLabel();
        labeladdress.setBounds(240, 160, 200, 20);
        p.add(labeladdress);

        // Initially load customer name and address for the selected meter
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from customer where meter_no ='" + meternumber.getSelectedItem() + "'");
            while (rs.next()) {
                lblname.setText(rs.getString("name"));
                labeladdress.setText(rs.getString("address"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Update name and address when a new meter number is selected
        meternumber.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                try {
                    Conn c = new Conn();
                    ResultSet rs = c.s.executeQuery("select * from customer where meter_no ='" + meternumber.getSelectedItem() + "'");
                    while (rs.next()) {
                        lblname.setText(rs.getString("name"));
                        labeladdress.setText(rs.getString("address"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        JLabel lblstate = new JLabel("Units Consumed");
        lblstate.setBounds(100, 200, 100, 20);
        p.add(lblstate);

        tfunits = new JTextField();
        tfunits.setBounds(240, 200, 200, 20);
        p.add(tfunits);

        JLabel lblcity = new JLabel("Month");
        lblcity.setBounds(100, 240, 100, 20);
        p.add(lblcity);

        cmonth = new Choice();
        cmonth.setBounds(240, 240, 200, 20);

        // Add months to dropdown
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
        p.add(cmonth);

        // Submit button to calculate and save bill
        next = new JButton("Submit");
        next.setBounds(120, 350, 100, 25);
        next.setBackground(Color.BLACK);
        next.setForeground(Color.WHITE);
        next.addActionListener(this);
        p.add(next);

        // Cancel button to close window
        cancel = new JButton("Cancel");
        cancel.setBounds(250, 350, 100, 25);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        p.add(cancel);

        setLayout(new BorderLayout());
        add(p, "Center");

        // Image on the left side
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/icon/hicon2.jpg"));
        Image i2 = i1.getImage().getScaledInstance(150, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        add(image, "West");

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == next) {
            String meter = meternumber.getSelectedItem();
            String units = tfunits.getText();
            String month = cmonth.getSelectedItem();

            int totalbill = 0;
            int unit_consumed = Integer.parseInt(units); // Convert units to integer

            String query = "select * from tax";

            // Calculate bill based on tax table values
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);

                while (rs.next()) {
                    totalbill += unit_consumed * Integer.parseInt(rs.getString("cost_per_unit"));
                    totalbill += Integer.parseInt(rs.getString("meter_rent"));
                    totalbill += Integer.parseInt(rs.getString("service_charge"));
                    totalbill += Integer.parseInt(rs.getString("service_tax"));
                    totalbill += Integer.parseInt(rs.getString("swacch_bharat_cess"));
                    totalbill += Integer.parseInt(rs.getString("fixed_tax"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Insert bill into database
            String query2 = "insert into bill values('" + meter + "','" + month + "','" + units + "','" + totalbill + "','Not Paid')";

            try {
                Conn c = new Conn();
                c.s.execute(query2);

                JOptionPane.showMessageDialog(null, "Customer Bill Updated Successfully");
                setVisible(false); // Close window after saving
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // Cancel button action
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new CalculateBill(); // Launch the application
    }
}
