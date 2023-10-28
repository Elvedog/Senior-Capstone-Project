package employedatabasesystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class EmployeeDataSystem extends JFrame {
    private ArrayList<Employee> employees = new ArrayList<>();
    private JTextField nameField, idField, addressField, phoneField, timeWorkedField;
    private DefaultTableModel tableModel;
    private JTable dataTable;

    public EmployeeDataSystem() {
        setTitle("Employee Data System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Employee Name:"));
        nameField = new JTextField(20);
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Employee ID:"));
        idField = new JTextField(10);
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Address:"));
        addressField = new JTextField(30);
        inputPanel.add(addressField);
        inputPanel.add(new JLabel("Phone Number:"));
        phoneField = new JTextField(15);
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("Time Worked (hours):"));
        timeWorkedField = new JTextField(5);
        inputPanel.add(timeWorkedField);
        JButton addButton = new JButton("Add Employee");
        inputPanel.add(addButton);
        add(inputPanel, BorderLayout.NORTH);

        // Create a table to display employee data
        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        tableModel.addColumn("Name");
        tableModel.addColumn("ID");
        tableModel.addColumn("Address");
        tableModel.addColumn("Phone Number");
        tableModel.addColumn("Time Worked");
        JScrollPane scrollPane = new JScrollPane(dataTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton editButton = new JButton("Edit Employee");
        JButton deleteButton = new JButton("Delete Employee");
        JButton viewDetailsButton = new JButton("View Employee Details");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewDetailsButton);
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

        loadEmployees(); // Load employees from a file
        displayEmployees();
        setVisible(true);
    }

    private void displayEmployees() {
        clearTable();
        for (Employee employee : employees) {
            tableModel.addRow(new Object[]{employee.getName(), employee.getId(), employee.getAddress(), employee.getPhoneNumber(), employee.getTimeWorked()});
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
                writer.println(employee.getName() + "," + employee.getId() + "," + employee.getAddress() + "," + employee.getPhoneNumber() + "," + employee.getTimeWorked());
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
                if (parts.length == 5) {
                    String name = parts[0];
                    String id = parts[1];
                    String address = parts[2];
                    String phoneNumber = parts[3];
                    int timeWorked = Integer.parseInt(parts[4]);
                    employees.add(new Employee(name, id, address, phoneNumber, timeWorked));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showEmployeeDetailsDialog(Employee employee) {
        JDialog dialog = new JDialog(this, "Employee Details");
        dialog.setLayout(new BorderLayout());

        JPanel detailsPanel = new JPanel(new GridLayout(6, 2));
        detailsPanel.add(new JLabel("Employee Name:"));
        detailsPanel.add(new JLabel(employee.getName()));
        detailsPanel.add(new JLabel("Employee ID:"));
        detailsPanel.add(new JLabel(employee.getId()));
        detailsPanel.add(new JLabel("Address:"));
        detailsPanel.add(new JLabel(employee.getAddress()));
        detailsPanel.add(new JLabel("Phone Number:"));
        detailsPanel.add(new JLabel(employee.getPhoneNumber()));
        detailsPanel.add(new JLabel("Time Worked (hours):"));
        detailsPanel.add(new JLabel(String.valueOf(employee.getTimeWorked())));

        dialog.add(detailsPanel, BorderLayout.CENTER);

        dialog.setSize(300, 200);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeeDataSystem());
    }
}
