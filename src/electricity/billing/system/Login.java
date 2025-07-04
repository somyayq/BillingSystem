package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    JButton login, cancel, signup;
    JTextField username, password;
    Choice logginin;

    Login() {
        super("Login Page");
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Username Label and Field
        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(300, 20, 100, 20);
        add(lblusername);

        username = new JTextField();
        username.setBounds(400, 20, 150, 20);
        add(username);

        // Password Label and Field
        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(300, 60, 100, 20);
        add(lblpassword);

        password = new JTextField();
        password.setBounds(400, 60, 150, 20);
        add(password);

        // Role Selection
        JLabel loggininas = new JLabel("Loggin in as");
        loggininas.setBounds(300, 100, 100, 20);
        add(loggininas);

        logginin = new Choice();
        logginin.add("Admin");
        logginin.add("Customer");
        logginin.setBounds(400, 100, 150, 20);
        add(logginin);

        // Login Button with Icon
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/icon/login.png"));
        Image i2 = i1.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
        login = new JButton("Login", new ImageIcon(i2));
        login.setBounds(330, 160, 100, 20);
        login.addActionListener(this);
        add(login);

        // Cancel Button with Icon
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("icon/icon/cancel.jpg"));
        Image i4 = i3.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
        cancel = new JButton("Cancel", new ImageIcon(i4));
        cancel.setBounds(450, 160, 100, 20);
        cancel.addActionListener(this);
        add(cancel);

        // Signup Button with Icon
        ImageIcon i5 = new ImageIcon(ClassLoader.getSystemResource("icon/icon/signup.png"));
        Image i6 = i5.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
        signup = new JButton("Signup", new ImageIcon(i6));
        signup.setBounds(380, 200, 100, 20);
        signup.addActionListener(this);
        add(signup);

        // Decorative Image on the Left
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icon/icon/second.jpg"));
        Image i8 = i7.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel image = new JLabel(i9);
        image.setBounds(0, 0, 250, 250);
        add(image);

        // Set window size and position
        setSize(640, 300);
        setLocation(400, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login) {
            // Get input credentials and role
            String susername = username.getText();
            String spassword = password.getText();
            String user = logginin.getSelectedItem();

            // Error Handling
            try {
                Conn c = new Conn();
                // Query to check credentials
                String query = "select * from login where username = '"+susername+"' and password = '"+spassword+"' and user = '"+user+"'";

                ResultSet rs = c.s.executeQuery(query);

                if (rs.next()) {
                    // If valid, open Project window
                    String meter = rs.getString("meter_no");
                    setVisible(false);
                    new Project(user, meter);
                } else {
                    // Show error and reset fields
                    JOptionPane.showMessageDialog(null, "Invalid Login");
                    username.setText("");
                    password.setText("");
                }

            } catch (Exception e) {
                e.printStackTrace(); // Handle SQL or connection errors
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false); // Close window
        } else if (ae.getSource() == signup) {
            setVisible(false); // Open signup window
            new Signup();
        }
    }

    public static void main(String[] args) {
        new Login(); // Launch login screen
    }
}
