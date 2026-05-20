import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EmployeeAdministrationSystem extends JFrame implements ActionListener {

    // LABELS
    JLabel titleLabel, nameLabel, deptLabel, salaryLabel, emailLabel;

    // TEXTFIELDS
    JTextField nameField, deptField, salaryField, emailField;

    // BUTTONS
    JButton addBtn, updateBtn, deleteBtn, searchBtn, viewBtn, clearBtn, exitBtn;

    // TABLE
    JTable table;
    DefaultTableModel model;

    // DATABASE CONNECTION
    Connection con;

    public EmployeeAdministrationSystem() {

        // FRAME SETTINGS
        setTitle("GUI-Based Employee Administration System");
        setSize(950, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(220, 235, 250));

        // TITLE
        titleLabel = new JLabel("GUI-Based Employee Administration System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(220, 10, 500, 40);
        add(titleLabel);

        // LABELS
        nameLabel = new JLabel("Employee Name");
        nameLabel.setBounds(40, 80, 120, 30);
        add(nameLabel);

        deptLabel = new JLabel("Department");
        deptLabel.setBounds(40, 130, 120, 30);
        add(deptLabel);

        salaryLabel = new JLabel("Salary");
        salaryLabel.setBounds(40, 180, 120, 30);
        add(salaryLabel);

        emailLabel = new JLabel("Email");
        emailLabel.setBounds(40, 230, 120, 30);
        add(emailLabel);

        // TEXTFIELDS
        nameField = new JTextField();
        nameField.setBounds(170, 80, 220, 30);
        add(nameField);

        deptField = new JTextField();
        deptField.setBounds(170, 130, 220, 30);
        add(deptField);

        salaryField = new JTextField();
        salaryField.setBounds(170, 180, 220, 30);
        add(salaryField);

        emailField = new JTextField();
        emailField.setBounds(170, 230, 220, 30);
        add(emailField);

        // BUTTONS
        addBtn = new JButton("ADD");
        addBtn.setBounds(40, 310, 100, 35);
        add(addBtn);

        updateBtn = new JButton("UPDATE");
        updateBtn.setBounds(160, 310, 100, 35);
        add(updateBtn);

        deleteBtn = new JButton("DELETE");
        deleteBtn.setBounds(280, 310, 100, 35);
        add(deleteBtn);

        searchBtn = new JButton("SEARCH");
        searchBtn.setBounds(40, 370, 100, 35);
        add(searchBtn);

        viewBtn = new JButton("VIEW");
        viewBtn.setBounds(160, 370, 100, 35);
        add(viewBtn);

        clearBtn = new JButton("CLEAR");
        clearBtn.setBounds(280, 370, 100, 35);
        add(clearBtn);

        exitBtn = new JButton("EXIT");
        exitBtn.setBounds(160, 430, 100, 35);
        add(exitBtn);

        // ACTION LISTENERS
        addBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        searchBtn.addActionListener(this);
        viewBtn.addActionListener(this);
        clearBtn.addActionListener(this);
        exitBtn.addActionListener(this);

        // TABLE
        model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Department");
        model.addColumn("Salary");
        model.addColumn("Email");

        table = new JTable(model);

        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(450, 80, 450, 380);

        add(pane);

        // TABLE CLICK EVENT
        table.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {

                int row = table.getSelectedRow();

                nameField.setText(model.getValueAt(row, 1).toString());
                deptField.setText(model.getValueAt(row, 2).toString());
                salaryField.setText(model.getValueAt(row, 3).toString());
                emailField.setText(model.getValueAt(row, 4).toString());
            }
        });

        // DATABASE CONNECTION
        connectDB();

        // LOAD DATA
        loadEmployees();

        setVisible(true);
    }

    // DATABASE CONNECTION METHOD
    public void connectDB() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/employee_admin_db",
                    "root",
                    "root1"
            );

            JOptionPane.showMessageDialog(this, "Database Connected Successfully");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, e);
        }
    }

    // LOAD EMPLOYEES
    public void loadEmployees() {

        try {

            model.setRowCount(0);

            String query = "SELECT * FROM employees";

            PreparedStatement pst = con.prepareStatement(query);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{

                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getDouble("salary"),
                        rs.getString("email")
                });
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, e);
        }
    }

    // ADD EMPLOYEE
    public void addEmployee() {

        try {

            String query = "INSERT INTO employees(name, department, salary, email) VALUES(?,?,?,?)";

            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, nameField.getText());
            pst.setString(2, deptField.getText());
            pst.setDouble(3, Double.parseDouble(salaryField.getText()));
            pst.setString(4, emailField.getText());

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Employee Added Successfully");

            loadEmployees();

            clearFields();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, e);
        }
    }

    // UPDATE EMPLOYEE
    public void updateEmployee() {

        try {

            int row = table.getSelectedRow();

            if (row == -1) {

                JOptionPane.showMessageDialog(this, "Select Employee from Table");
                return;
            }

            int id = Integer.parseInt(model.getValueAt(row, 0).toString());

            String query = "UPDATE employees SET name=?, department=?, salary=?, email=? WHERE id=?";

            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, nameField.getText());
            pst.setString(2, deptField.getText());
            pst.setDouble(3, Double.parseDouble(salaryField.getText()));
            pst.setString(4, emailField.getText());
            pst.setInt(5, id);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Employee Updated Successfully");

            loadEmployees();

            clearFields();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, e);
        }
    }

    // DELETE EMPLOYEE
    public void deleteEmployee() {

        try {

            int row = table.getSelectedRow();

            if (row == -1) {

                JOptionPane.showMessageDialog(this, "Select Employee from Table");
                return;
            }

            int id = Integer.parseInt(model.getValueAt(row, 0).toString());

            String query = "DELETE FROM employees WHERE id=?";

            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1, id);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Employee Deleted Successfully");

            loadEmployees();

            clearFields();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, e);
        }
    }

    // SEARCH EMPLOYEE
    public void searchEmployee() {

        try {

            model.setRowCount(0);

            String query = "SELECT * FROM employees WHERE name LIKE ?";

            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, "%" + nameField.getText() + "%");

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{

                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getDouble("salary"),
                        rs.getString("email")
                });
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, e);
        }
    }

    // CLEAR FIELDS
    public void clearFields() {

        nameField.setText("");
        deptField.setText("");
        salaryField.setText("");
        emailField.setText("");
    }

    // BUTTON ACTIONS
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == addBtn) {

            addEmployee();
        }

        if (ae.getSource() == updateBtn) {

            updateEmployee();
        }

        if (ae.getSource() == deleteBtn) {

            deleteEmployee();
        }

        if (ae.getSource() == searchBtn) {

            searchEmployee();
        }

        if (ae.getSource() == viewBtn) {

            loadEmployees();
        }

        if (ae.getSource() == clearBtn) {

            clearFields();
        }

        if (ae.getSource() == exitBtn) {

            System.exit(0);
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {

        new EmployeeAdministrationSystem();
    }
}