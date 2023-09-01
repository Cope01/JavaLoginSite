import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SignUpStrana extends JFrame {
    public JPanel Panel2;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton signUpButton;

    public SignUpStrana() {
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                LogInStrana LogInStrana = new LogInStrana();
                JFrame SignUpFrame = (JFrame) SwingUtilities.getWindowAncestor(Panel2);
                SignUpFrame.dispose();
                JFrame signUpFrame = new JFrame("Sign Up");
                signUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame
                signUpFrame.setContentPane(LogInStrana.Panel1);
                signUpFrame.pack();
                signUpFrame.setVisible(true);

                String name = textField1.getText();
                String surname = textField2.getText();
                String username = textField3.getText();
                String password = textField4.getText();
                // Check if all fields are filled
                if (name.isEmpty() || surname.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(SignUpStrana.this, "Morate da popunite sva polja");
                    return;
                }

                // Check if username already exists
                if (userExists(username)) {
                    JOptionPane.showMessageDialog(SignUpStrana.this, "Username vec postoji");
                    return;
                }

                // Store the user data in the text file
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("userData.txt", true));
                    writer.write(name + "," + surname + "," + username + "," + password + "," + "student");
                    writer.newLine();
                    writer.close();

                    JOptionPane.showMessageDialog(SignUpStrana.this, "Uspesno ste napravili nalog");

                    // Clear the fields
                    textField1.setText("");
                    textField2.setText("");
                    textField3.setText("");
                    textField4.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private boolean userExists(String username) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("userData.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 2 && parts[2].equals(username)) { // Compare against the username part (index 2)
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}
