import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class AdminStrana {
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton ADDButton;
    private JButton DELETEButton;
    private JButton EDITButton;
    private JLabel Ime;
    private JLabel Prezime;
    private JLabel Username;
    private JLabel Password;
    private JLabel Role;
    public JPanel Panel5;
    private DefaultTableModel tableModel;
    public AdminStrana() {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Ime", "Prezime", "Username", "Password", "Role"});
        table1.setModel(tableModel);
        loadUserData();
        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUserData();
            }
        });
        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUserData();
            }
        });
        EDITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editUserData();
            }
        });
    }
    private void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("userData.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    tableModel.addRow(new Object[]{parts[0], parts[1], parts[2], parts[3], parts[4]});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void addUserData() {
        String ime = textField1.getText();
        String prezime = textField2.getText();
        String username = textField3.getText();
        String password = textField4.getText();
        String role = textField5.getText();
        if (!ime.isEmpty() && !prezime.isEmpty() && !username.isEmpty() && !password.isEmpty() && !role.isEmpty()) {
            tableModel.addRow(new Object[]{ime, prezime, username, password, role});
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("userData.txt", true))) {
                writer.write(ime + "," + prezime + "," + username + "," + password + "," + role);
                writer.newLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            clearTextFields();
        } else {
            JOptionPane.showMessageDialog(null, "Fill in all fields.");
        }
    }
    private void deleteUserData() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("userData.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            lines.remove(selectedRow);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("userData.txt"))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a row to delete.");
        }
    }
    private void editUserData() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            String ime = textField1.getText();
            String prezime = textField2.getText();
            String username = textField3.getText();
            String password = textField4.getText();
            String role = textField5.getText();
            if (!ime.isEmpty() && !prezime.isEmpty() && !username.isEmpty() && !password.isEmpty() && !role.isEmpty()) {
                tableModel.setValueAt(ime, selectedRow, 0);
                tableModel.setValueAt(prezime, selectedRow, 1);
                tableModel.setValueAt(username, selectedRow, 2);
                tableModel.setValueAt(password, selectedRow, 3);
                tableModel.setValueAt(role, selectedRow, 4);
                List<String> lines = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader("userData.txt"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines.add(line);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                lines.set(selectedRow, ime + "," + prezime + "," + username + "," + password + "," + role);
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("userData.txt"))) {
                    for (String line : lines) {
                        writer.write(line);
                        writer.newLine();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                clearTextFields();
            } else {
                JOptionPane.showMessageDialog(null, "Fill in all fields.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a row to edit.");
        }
    }
    private void clearTextFields() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
    }
}
