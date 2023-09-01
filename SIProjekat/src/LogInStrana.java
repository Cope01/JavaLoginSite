import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LogInStrana {
    private JTextField textField1;
    private JButton signInButton;
    private JButton signUpButton;
    public JPanel Panel1;
    private JPasswordField passwordField1;
    private JButton guestButton;

    public LogInStrana() {
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignUpStrana signUpStrana = new SignUpStrana();
                JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor(Panel1);
                loginFrame.dispose();
                JFrame signUpFrame = new JFrame("Sign Up");
                signUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame
                signUpFrame.setContentPane(signUpStrana.Panel2);
                signUpFrame.pack();
                signUpFrame.setVisible(true);
            }
        });

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText();
                String password = new String(passwordField1.getPassword());

                if (authenticateUser(username, password)) {
                    String userRole = getUserRole(username, password); // Get the user's role
                    if (userRole != null) {
                        // Login successful
                        JOptionPane.showMessageDialog(null, "Uspesno ste se prijavili");
                        // Navigate to PocetnaStrana
                        JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor(Panel1);
                        loginFrame.dispose();
                        JFrame pocetnaFrame = new JFrame("Pocetna Strana");
                        pocetnaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        pocetnaFrame.setContentPane(new PocetnaStrana(userRole).Panel3); // Pass the userRole
                        pocetnaFrame.pack();
                        pocetnaFrame.setVisible(true);
                    }
                } else {
                    // Login failed
                    JOptionPane.showMessageDialog(null, "Uneli ste nesto pogresno, pokusajte opet");
                }
            }
        });
        guestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    private String getUserRole(String username, String password) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("userData.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && parts[2].equals(username) && parts[3].equals(password)) {
                    reader.close();
                    return parts[4]; // Return the user's role
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private boolean authenticateUser(String username, String password) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("userData.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && parts[2].equals(username) && parts[3].equals(password)) {
                    reader.close();
                    return true; // Return true for any valid user
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("LoginStrana");
        frame.setContentPane(new LogInStrana().Panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}