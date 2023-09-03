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

    public AdminStrana() {
        // Initialize table model for table1
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Ime", "Prezime", "Username", "Password", "Role"});
        table1.setModel(tableModel);

        // Populate table1 with data from userData.txt
        try {
            BufferedReader reader = new BufferedReader(new FileReader("userData.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    // Add a row to the table for each user
                    tableModel.addRow(new Object[]{parts[0], parts[1], parts[2], parts[3], parts[4]});
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set up addActionListener for ADDButton
        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ime = textField1.getText();
                String prezime = textField2.getText();
                String username = textField3.getText();
                String password = textField4.getText();
                String role = textField5.getText();

                if (!ime.isEmpty() && !prezime.isEmpty() && !username.isEmpty() && !password.isEmpty() && !role.isEmpty()) {
                    // Add the new user data to the table
                    tableModel.addRow(new Object[]{ime, prezime, username, password, role});

                    // Add the new user data to the userData.txt file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("userData.txt", true))) {
                        writer.write(ime + "," + prezime + "," + username + "," + password + "," + role);
                        writer.newLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    // Clear the text fields
                    textField1.setText("");
                    textField2.setText("");
                    textField3.setText("");
                    textField4.setText("");
                    textField5.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Fill in all fields.");
                }
            }
        });
        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    // Remove the selected row from the table
                    tableModel.removeRow(selectedRow);

                    // Update the userData.txt file without the deleted row
                    List<String> lines = new ArrayList<>();
                    try (BufferedReader reader = new BufferedReader(new FileReader("userData.txt"))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            lines.add(line);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("userData.txt"))) {
                        for (int i = 0; i < lines.size(); i++) {
                            if (i != selectedRow) {
                                writer.write(lines.get(i));
                                writer.newLine();
                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Select a row to delete.");
                }
            }
        });
        EDITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    String ime = textField1.getText();
                    String prezime = textField2.getText();
                    String username = textField3.getText();
                    String password = textField4.getText();
                    String role = textField5.getText();

                    if (!ime.isEmpty() && !prezime.isEmpty() && !username.isEmpty() && !password.isEmpty() && !role.isEmpty()) {
                        // Update the selected row in the table
                        tableModel.setValueAt(ime, selectedRow, 0);
                        tableModel.setValueAt(prezime, selectedRow, 1);
                        tableModel.setValueAt(username, selectedRow, 2);
                        tableModel.setValueAt(password, selectedRow, 3);
                        tableModel.setValueAt(role, selectedRow, 4);

                        // Update the userData.txt file with the edited data
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

                        // Clear the text fields
                        textField1.setText("");
                        textField2.setText("");
                        textField3.setText("");
                        textField4.setText("");
                        textField5.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Popunite sva polja");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Izaberite red koji hocete da editujete");
                }
            }
        });
    }
}
