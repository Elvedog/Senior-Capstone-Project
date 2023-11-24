package employedatabasesystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;

public class EmployeeDataSystem extends JFrame {
    private ArrayList<Employee> employees = new ArrayList<>();
    private JTextField nameField, idField, addressField, phoneField, timeWorkedField;
    private DefaultTableModel tableModel;
    private JTable dataTable;
    private EmployeeEditor employeeEditor = new EmployeeEditor(employees);
    private JButton editTasksButton;
    private JButton editWorkScheduleButton; // Button to edit work schedule
    private JDialog detailsDialog;
    public EmployeeDataSystem() {
        setTitle("Employee Data System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for proper alignment
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Employee Name:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        nameField = new JTextField(20);
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Employee ID:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        idField = new JTextField(10);
        inputPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        addressField = new JTextField(30);
        inputPanel.add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Phone Number:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        phoneField = new JTextField(15);
        inputPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Time Worked (hours):"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        timeWorkedField = new JTextField(5);
        inputPanel.add(timeWorkedField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // Span two columns for the "Add Employee" button
        gbc.anchor = GridBagConstraints.CENTER; // Center-align the button
        JButton addButton = new JButton("Add Employee");
        inputPanel.add(addButton, gbc);

        add(inputPanel, BorderLayout.NORTH);

        // Create a table to display employee data
        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        tableModel.addColumn("Name");
        tableModel.addColumn("ID");
        tableModel.addColumn("Address");
        tableModel.addColumn("Phone Number");
        tableModel.addColumn("Time Worked");
        tableModel.addColumn("Work Schedule");
        JScrollPane scrollPane = new JScrollPane(dataTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton editButton = new JButton("Edit Employee");
        JButton deleteButton = new JButton("Delete Employee");
        JButton viewDetailsButton = new JButton("View Employee Details");
        JButton editSalaryButton = new JButton("Edit Employee Salary");
        JButton editScheduledTimeButton = new JButton("Edit Employee Scheduled Time");
        editTasksButton = new JButton("Edit Employee Tasks");
        editWorkScheduleButton = new JButton("Edit Work Schedule"); // Button for editing work schedule
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(editSalaryButton);
        buttonPanel.add(editTasksButton);
        buttonPanel.add(editWorkScheduleButton); // Add the "Edit Work Schedule" button
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String id = idField.getText();
                String address = addressField.getText();
                String phoneNumber = phoneField.getText();
                int timeWorked = Integer.parseInt(timeWorkedField.getText());

                Employee employee = new Employee(name, id, address, phoneNumber, timeWorked);
                employees.add(employee);
                displayEmployees();
                clearFields();
                saveEmployeesToFile();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = dataTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String newName = JOptionPane.showInputDialog("Enter the new name:");
                    String newAddress = JOptionPane.showInputDialog("Enter the new address:");
                    String newPhoneNumber = JOptionPane.showInputDialog("Enter the new phone number:");
                    int newTimeWorked = Integer.parseInt(JOptionPane.showInputDialog("Enter the new Time Worked"));
                    Employee employee = employees.get(selectedRow);
                    employee.setName(newName);
                    employee.setAddress(newAddress);
                    employee.setPhoneNumber(newPhoneNumber);
                    employee.setTimeWorked(newTimeWorked);
                    displayEmployees();
                    saveEmployeesToFile();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an employee to edit.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = dataTable.getSelectedRow();
                if (selectedRow >= 0) {
                    employees.remove(selectedRow);
                    displayEmployees();
                    saveEmployeesToFile();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an employee to delete.");
                }
            }
        });

        viewDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = dataTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Employee employee = employees.get(selectedRow);
                    showEmployeeDetailsDialog(employee);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an employee to view details.");
                }
            }
        });

        editSalaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = dataTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Employee employee = employees.get(selectedRow);
                    double newSalary = Double.parseDouble(JOptionPane.showInputDialog("Enter new salary:"));
                    employeeEditor.editEmployeeSalary(employee, newSalary);
                    displayEmployees();
                    saveEmployeesToFile();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an employee to edit salary.");
                }
            }
        });

        editTasksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = dataTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Employee employee = employees.get(selectedRow);
                    String newTasks = JOptionPane.showInputDialog("Enter new tasks:");
                    employeeEditor.editEmployeeTasks(employee, newTasks);
                    displayEmployees();
                    saveEmployeesToFile();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an employee to edit tasks.");
                }
            }
        });

     // Add an ActionListener to the "Edit Work Schedule" button
        editWorkScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = dataTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Employee employee = employees.get(selectedRow);
                    showWorkScheduleDialog(employee);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an employee to edit work schedule.");
                }
            }
        });
        loadEmployees();
        displayEmployees();
        setVisible(true);
    }

    private void showWorkScheduleDialog(Employee employee) {
        // Create an array of input fields for each day of the week
        JTextField[] dayFields = new JTextField[7];
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        for (int i = 0; i < 7; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            inputPanel.add(new JLabel(daysOfWeek[i]), gbc);
            gbc.gridx = 1;
            gbc.gridy = i;
            dayFields[i] = new JTextField(20);
            dayFields[i].setText(employee.getWorkScheduleForDay(i)); // Populate with existing data
            inputPanel.add(dayFields[i], gbc);
        }

        int result = JOptionPane.showConfirmDialog(
                null,
                inputPanel,
                "Edit Work Schedule",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            // Retrieve the updated schedule from input fields
            String[] updatedSchedule = new String[7];
            for (int i = 0; i < 7; i++) {
                updatedSchedule[i] = dayFields[i].getText();
            }

            employee.setWorkSchedule(updatedSchedule);
            displayEmployees();
            saveEmployeesToFile();

            // Update the "View Employee Details" dialog if it's open
            if (detailsDialog != null && detailsDialog.isVisible()) {
                JLabel[] workScheduleLabels = new JLabel[7];
                Component[] components = detailsDialog.getContentPane().getComponents();

                for (int i = 0; i < 7; i++) {
                    if (components[i * 2] instanceof JLabel) {
                        workScheduleLabels[i] = (JLabel) components[i * 2];
                    }
                }

                // Update the labels with the new work schedule data
                for (int i = 0; i < 7; i++) {
                    if (workScheduleLabels[i] != null) {
                        workScheduleLabels[i].setText("Work Schedule (" + getDayOfWeek(i) + "): " + updatedSchedule[i]);
                    }
                }
            }
        }
    }
            
        
  
       
        
    

    // Helper method to get the day of the week based on an index
    private String getDayOfWeek(int index) {
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        if (index >= 0 && index < daysOfWeek.length) {
            return daysOfWeek[index];
        }
        return "";
    }

    private void displayEmployees() {
        clearTable();
        for (Employee employee : employees) {
            tableModel.addRow(new Object[]{
                    employee.getName(),
                    employee.getId(),
                    employee.getAddress(),
                    employee.getPhoneNumber(),
                    employee.getTimeWorked(),
                    String.join(", ", employee.getWorkSchedule())
            });
        }
    }

    private void clearTable() {
        int rowCount = tableModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
    }

    private void clearFields() {
        nameField.setText("");
        idField.setText("");
        addressField.setText("");
        phoneField.setText("");
        timeWorkedField.setText("");
    }

    private void saveEmployeesToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("employees.txt"))) {
            for (Employee employee : employees) {
                writer.println(employee.getName() + ","
                        + employee.getId() + ","
                        + employee.getAddress() + ","
                        + employee.getPhoneNumber() + ","
                        + employee.getTimeWorked() + ","
                        + String.join("|", employee.getWorkSchedule()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEmployees() {
        try (Scanner scanner = new Scanner(new File("employees.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String name = parts[0];
                    String id = parts[1];
                    String address = parts[2];
                    String phoneNumber = parts[3];
                    int timeWorked = Integer.parseInt(parts[4]);
                    String[] workSchedule = parts[5].split("\\|");
                    Employee employee = new Employee(name, id, address, phoneNumber, timeWorked);
                    employee.setWorkSchedule(workSchedule);
                    employees.add(employee);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showEmployeeDetailsDialog(Employee employee) {
        JDialog dialog = new JDialog(this, "Employee Details", true);
        dialog.setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridLayout(0, 2));
        infoPanel.add(createLabel("Employee Name:"));
        infoPanel.add(new JLabel(employee.getName()));
        infoPanel.add(createLabel("Employee ID:"));
        infoPanel.add(new JLabel(employee.getId()));
        infoPanel.add(createLabel("Address:"));
        infoPanel.add(new JLabel(employee.getAddress()));
        infoPanel.add(createLabel("Phone Number:"));
        infoPanel.add(new JLabel(employee.getPhoneNumber()));
        infoPanel.add(createLabel("Time Worked (hours):"));
        infoPanel.add(new JLabel(String.valueOf(employee.getTimeWorked())));

        JPanel schedulePanel = new JPanel(new GridLayout(0, 2));
        schedulePanel.add(createLabel("Work Schedule:"));

        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < 7; i++) {
            String daySchedule = employee.getWorkScheduleForDay(i);
            JLabel dayLabel = new JLabel(" - " + daysOfWeek[i] + ": " + (daySchedule != null ? daySchedule : "N/A"));
            schedulePanel.add(dayLabel);
        }

        dialog.add(infoPanel, BorderLayout.NORTH);
        dialog.add(schedulePanel, BorderLayout.CENTER);

        dialog.setSize(400, 300);
        dialog.setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        return label;
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeeDataSystem());
    }
} 
