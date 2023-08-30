import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PocetnaStrana {
    public JPanel Panel3;
    private JTabbedPane tabbedPane1;
    private JButton signOutButton;
    private JButton addButton;
    private JTable table1;
    private JButton addButton1;
    private JTable table2;
    private JButton addButton2;
    private JTable table3;
    private JButton addButton3;
    private JTable table4;


public PocetnaStrana() {
    signOutButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            LogInStrana logInStrana = new LogInStrana();
            JFrame pocetnaFrame = (JFrame) SwingUtilities.getWindowAncestor(Panel3);
            pocetnaFrame.dispose();
            JFrame logInFrame = new JFrame("Log In");
            logInFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame
            logInFrame.setContentPane(logInStrana.Panel1);
            logInFrame.pack();
            logInFrame.setVisible(true);
        }
    });
}
}