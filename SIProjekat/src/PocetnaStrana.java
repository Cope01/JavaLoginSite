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
    private JButton deleteButton;
    private JButton editButton;
    private JButton deleteButton1;
    private JButton editButton1;
    private JButton deleteButton2;
    private JButton editButton2;
    private JButton deleteButton3;
    private JButton editButton3;
    private JLabel DragAndDrop;

    public PocetnaStrana(String userRole) {
        if ("guest".equals(userRole) || "student".equals(userRole)) {

            addButton.setVisible(false);
            editButton.setVisible(false);
            deleteButton.setVisible(false);
            addButton1.setVisible(false);
            editButton1.setVisible(false);
            deleteButton1.setVisible(false);
            addButton2.setVisible(false);
            editButton2.setVisible(false);
            deleteButton2.setVisible(false);
            addButton3.setVisible(false);
            editButton3.setVisible(false);
            deleteButton3.setVisible(false);
        }

        // Set up signOutButton ActionListener
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
