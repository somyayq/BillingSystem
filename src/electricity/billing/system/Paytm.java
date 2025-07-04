package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Paytm extends JFrame implements ActionListener {

    String meter;
    JButton back;

    Paytm(String meter) {
        this.meter = meter;

        JEditorPane j = new JEditorPane();
        j.setEditable(false);

        try {
            // Load Razorpay page
            j.setPage("https://paytm.com/online-payments");
        } catch (Exception e) {
            // In case the page fails to load, show an error message
            j.setContentType("text/html");
            j.setText("<html>Could not load<html>");
        }

        JScrollPane pane = new JScrollPane(j);
        add(pane);

        back = new JButton("Back");
        back.setBounds(640, 20, 80, 30);
        back.addActionListener(this);  // Add action listener for back button
        j.add(back);

        setSize(800, 600);  // Set window size
        setLocation(400, 150);  // Set window location
        setVisible(true);  // Display the frame
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);  // Hide current window
        new PayBill(meter);  // Open PayBill window again
    }

    public static void main(String[] args) {
        new Paytm("");  // Launch Paytm window with empty meter number
    }
}
