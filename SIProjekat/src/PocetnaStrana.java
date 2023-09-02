import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PocetnaStrana {
    public JPanel Panel3;
    private JTabbedPane tabbedPane1;
    private JButton signOutButton;
    private JButton[] addButtons;
    private JButton[] editButtons; // Added edit buttons
    private JButton[] deleteButtons; // Added delete buttons
    private JTable[] tables;
    private JTextArea[] textAreas;
    private List<Object[]>[] tableData;
    private final String[] dataFileNames;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JTextArea textArea4;
    private JTextArea textArea5;
    private JTextArea textArea6;
    private JTextArea textArea7;
    private JTextArea textArea8;
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
        // Define the number of tables and their data file names
        int numTables = 4;
        dataFileNames = new String[]{"table1_data.csv", "table2_data.csv", "table3_data.csv", "table4_data.csv"};

        // Initialize arrays for buttons, tables, text areas, and table data
        addButtons = new JButton[]{addButton, addButton1, addButton2, addButton3};
        editButtons = new JButton[]{editButton, editButton1, editButton2, editButton3}; // Added edit buttons
        deleteButtons = new JButton[]{deleteButton, deleteButton1, deleteButton2, deleteButton3}; // Added delete buttons
        tables = new JTable[]{table1, table2, table3, table4};
        textAreas = new JTextArea[]{textArea1, textArea2, textArea3, textArea4, textArea5, textArea6, textArea7, textArea8};
        tableData = new List[numTables];

        // Hide elements for certain user roles
        if ("guest".equals(userRole) || "student".equals(userRole)) {
            for (int i = 0; i < numTables; i++) {
                addButtons[i].setVisible(false);
                editButtons[i].setVisible(false); // Hide edit buttons for certain user roles
                deleteButtons[i].setVisible(false); // Hide delete buttons for certain user roles
                for (int j = i * 2; j < (i + 1) * 2; j++) {
                    textAreas[j].setVisible(false);
                }
            }
        }

        // Initialize the table models and table data
        for (int i = 0; i < numTables; i++) {
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.setColumnIdentifiers(new Object[]{"Column 1", "Column 2"});
            tables[i].setModel(tableModel);

            tableData[i] = loadDataFromCSV(dataFileNames[i]);

            if (!tableData[i].isEmpty()) {
                DefaultTableModel model = (DefaultTableModel) tables[i].getModel();
                for (Object[] rowData : tableData[i]) {
                    model.addRow(rowData);
                }
            }
        }

        // Set up addActionListeners for the add buttons
        for (int i = 0; i < numTables; i++) {
            int tableIndex = i;
            addButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addDataToTable(tableIndex);
                }
            });
        }

        // Set up editButton action listeners for each table
        for (int i = 0; i < numTables; i++) {
            int tableIndex = i;
            editButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editDataInTable(tableIndex);
                }
            });
        }

        // Set up deleteButton action listeners for each table
        for (int i = 0; i < numTables; i++) {
            int tableIndex = i;
            deleteButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteDataFromTable(tableIndex);
                }
            });
        }

        // Set up signOutButton ActionListener
        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save the table data before logging out
                for (int i = 0; i < numTables; i++) {
                    saveDataToCSV(tableData[i], dataFileNames[i]);
                }

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

    // Add data to the specified table
    private void addDataToTable(int tableIndex) {
        int textAreaStartIndex = tableIndex * 2;
        String text1 = textAreas[textAreaStartIndex].getText();
        String text2 = textAreas[textAreaStartIndex + 1].getText();

        if (!text1.isEmpty() && !text2.isEmpty()) {
            DefaultTableModel model = (DefaultTableModel) tables[tableIndex].getModel();
            model.addRow(new Object[]{text1, text2});
            tableData[tableIndex].add(new Object[]{text1, text2});
            textAreas[textAreaStartIndex].setText("");
            textAreas[textAreaStartIndex + 1].setText("");
        } else {
            JOptionPane.showMessageDialog(Panel3, "Both text fields must be filled.");
        }
    }

    // Edit data in the specified table
    private void editDataInTable(int tableIndex) {
        DefaultTableModel model = (DefaultTableModel) tables[tableIndex].getModel();
        int selectedRow = tables[tableIndex].getSelectedRow();

        if (selectedRow != -1) { // Check if a row is selected
            int textAreaStartIndex = tableIndex * 2;
            String text1 = textAreas[textAreaStartIndex].getText();
            String text2 = textAreas[textAreaStartIndex + 1].getText();

            if (!text1.isEmpty() && !text2.isEmpty()) {
                // Update the selected row with the new data
                model.setValueAt(text1, selectedRow, 0);
                model.setValueAt(text2, selectedRow, 1);
                tableData[tableIndex].set(selectedRow, new Object[]{text1, text2});
                textAreas[textAreaStartIndex].setText("");
                textAreas[textAreaStartIndex + 1].setText("");
            } else {
                JOptionPane.showMessageDialog(Panel3, "Both text fields must be filled.");
            }
        } else {
            JOptionPane.showMessageDialog(Panel3, "Please select a row to edit.");
        }
    }

    // Delete data from the specified table
    private void deleteDataFromTable(int tableIndex) {
        DefaultTableModel model = (DefaultTableModel) tables[tableIndex].getModel();
        int selectedRow = tables[tableIndex].getSelectedRow();

        if (selectedRow != -1) { // Check if a row is selected
            model.removeRow(selectedRow);
            tableData[tableIndex].remove(selectedRow);
        } else {
            JOptionPane.showMessageDialog(Panel3, "Please select a row to delete.");
        }
    }

    // Save data to a CSV file
    private void saveDataToCSV(List<Object[]> data, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Object[] rowData : data) {
                String line = String.join(",", Arrays.stream(rowData).map(Object::toString).toArray(String[]::new));
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load data from a CSV file
    private List<Object[]> loadDataFromCSV(String fileName) {
        List<Object[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    data.add(parts);
                }
            }
        } catch (IOException e) {
            // Handle the case when the file does not exist or cannot be read
            // You can create the file here if it doesn't exist initially
        }
        return data;
    }
}
