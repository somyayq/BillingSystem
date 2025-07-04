package electricity.billing.system;

// importing necessary libraries
import javax.swing.*;        // For gui components like JFrame, JTable, JScrollPane
import java.awt.*;           // For setting gui properties   
import java.sql.*;           // to work with sql database
import net.proteanit.sql.DbUtils;      // utility to convert result set to tablemodel

// class that extends Jframe to create a windowed GUI
public class BillDetails extends JFrame{
    BillDetails(String meter){   // Constructor with the name BillDetails
        super("Bill Details");
        
        setSize(700,650);
        setLocation(400,150);
        
        // Background
        getContentPane().setBackground(Color.WHITE);
        
        JTable table = new JTable();
        
        //error handling
        try{
            
            Conn c=new Conn();
            String query="Select * from bill where meter_no ='"+meter+"'";  // sql query
            ResultSet rs=c.s.executeQuery(query);   // execute the query
            
            table.setModel(DbUtils.resultSetToTableModel(rs));  
        }catch(Exception e){
            // else print the error
            e.printStackTrace();
        }
        // add the scroller at the side
        JScrollPane sp=new JScrollPane(table);
        sp.setBounds(0,0,700,650);
        add(sp);
        
        setVisible(true);
    }
    
    public static void main(String[] args){
        new BillDetails("");
    }
}
