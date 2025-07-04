package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class MeterInfo extends JFrame implements ActionListener {

    JTextField tfname, tfaddress, tfstate, tfcity, tfemail, tfphone;
    JButton next, cancel;
    JLabel lblmeter;
    Choice meterlocation, metertype, phasecode, billtype;
    String meternumber;

    MeterInfo(String meternumber) {
        super("Meter Information");
        this.meternumber = meternumber;

        setSize(700, 500); // Set window size
        setLocation(400, 200); // Set window position

        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBackground(new Color(173, 216, 230));
        add(p);

        JLabel heading = new JLabel("Meter Information");
        heading.setBounds(180, 10, 200, 20);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 24));
        p.add(heading);

        // Display meter number
        JLabel lblname = new JLabel("Meter Number");
        lblname.setBounds(100, 80, 100, 20);
        p.add(lblname);

        JLabel lblmeternumber = new JLabel(meternumber);
        lblmeternumber.setBounds(240, 80, 100, 20);
        p.add(lblmeternumber);

        // Meter Location dropdown
        JLabel lblmeterno = new JLabel("Meter Location");
        lblmeterno.setBounds(100, 120, 100, 20);
        p.add(lblmeterno);

        meterlocation = new Choice();
        meterlocation.add("Outside");
        meterlocation.add("Inside");
        meterlocation.setBounds(240, 120, 200, 20);
        p.add(meterlocation);

        // Meter Type dropdown
        JLabel lbladdress = new JLabel("Meter Type");
        lbladdress.setBounds(100, 160, 100, 20);
        p.add(lbladdress);

        metertype = new Choice();
        metertype.add("Electric Meter");
        metertype.add("Solar Meter");
        metertype.add("Smart Meter");
        metertype.setBounds(240, 160, 200, 20);
        p.add(metertype);

        // Phase Code dropdown
        JLabel lblstate = new JLabel("Phase code");
        lblstate.setBounds(100, 200, 100, 20);
        p.add(lblstate);

        phasecode = new Choice();
        phasecode.add("011");
        phasecode.add("022");
        phasecode.add("033");
        phasecode.add("044");
        phasecode.add("055");
        phasecode.add("066");
        phasecode.add("077");
        phasecode.add("088");
        phasecode.add("099");
        phasecode.setBounds(240, 200, 200, 20);
        p.add(phasecode);

        // Bill Type dropdown
        JLabel lblcity = new JLabel("Bill Type");
        lblcity.setBounds(100, 240, 100, 20);
        p.add(lblcity);

        billtype = new Choice();
        billtype.add("Normal");
        billtype.add("Industrial");
        billtype.setBounds(240, 240, 200, 20);
        p.add(billtype);

        // Days and Note
        JLabel lblemail = new JLabel("Days");
        lblemail.setBounds(100, 280, 100, 20);
        p.add(lblemail);

        JLabel lblemails = new JLabel("30 Days");
        lblemails.setBounds(240, 280, 100, 20);
        p.add(lblemails);

        JLabel lblphone = new JLabel("Note");
        lblphone.setBounds(100, 320, 100, 20);
        p.add(lblphone);

        JLabel lblphones = new JLabel("By Default Bill is calculated for 30 days only");
        lblphones.setBounds(240, 320, 500, 20);
        p.add(lblphones);

        // Submit button
        next = new JButton("Submit");
        next.setBounds(220, 390, 100, 25);
        next.setBackground(Color.BLACK);
        next.setForeground(Color.WHITE);
        next.addActionListener(this);
        p.add(next);

        // Layout and side image
        setLayout(new BorderLayout());
        add(p, "Center");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/icon/hicon1.jpg"));
        Image i2 = i1.getImage().getScaledInstance(150, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        add(image, "West");

        getContentPane().setBackground(Color.WHITE);

        setVisible(true); // Show window
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == next) {
            // Collect selected input
            String meter = meternumber;
            String location = meterlocation.getSelectedItem();
            String type = metertype.getSelectedItem();
            String code = phasecode.getSelectedItem();
            String typebill = billtype.getSelectedItem();
            String days = "30";

            // Insert into database
            String query = "insert into meter_info values('" + meter + "','" + location + "','" + type + "','" + code + "','" + typebill + "','" + days + "')";
            try {
                Conn c = new Conn();
                c.s.executeUpdate(query);

                JOptionPane.showMessageDialog(null, "Meter Details Added Successfully");
                setVisible(false); // Close window after success

            } catch (Exception e) {
                e.printStackTrace(); // Show error details
            }
        } else {
            setVisible(false); // Close window if not submit
        }
    }

    public static void main(String[] args) {
        new MeterInfo(""); // Run with empty meter number
    }
}
