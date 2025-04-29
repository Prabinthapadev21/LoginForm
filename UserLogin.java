package Swing;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class UserLogin {
    JFrame frame;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;

   public UserLogin() {
        frame = new JFrame("Login Form");

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 100, 30);
        usernameField = new JTextField();
        usernameField.setBounds(150, 50, 150, 30);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 100, 100, 30);
        passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 150, 30);

        loginButton = new JButton("Save");
        loginButton.setBounds(150, 150, 80, 30);

        // Button action
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveToDatabase();
            }
        });

        frame.add(userLabel);
        frame.add(usernameField);
        frame.add(passLabel);
        frame.add(passwordField);
        frame.add(loginButton);

        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void saveToDatabase() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        try {
            // Step 1: Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Connect to the database
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Login_schema", "root", "prabin2062"
            );

            // Step 3: Prepare SQL statement
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            // Step 4: Execute update
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(frame, "User saved successfully!");
            }

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new UserLogin();
    }
}
