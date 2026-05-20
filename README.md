# GUI-Based Employee Administration System

## 📌 Project Overview
The GUI-Based Employee Administration System is a desktop application developed using Java Swing and JDBC for managing employee records.

This project performs CRUD Operations:
- Add Employee
- Update Employee
- Delete Employee
- Search Employee
- View Employee Records

The application is connected with a MySQL database and provides a user-friendly graphical interface.

---

## 🚀 Technologies Used
- Java
- Java Swing
- JDBC
- MySQL
- MySQL Workbench
- Eclipse / NetBeans IDE

---

## 📂 Project Features
✔ Add Employee  
✔ Update Employee  
✔ Delete Employee  
✔ Search Employee  
✔ View All Employees  
✔ JTable Display  
✔ MySQL Database Connectivity  
✔ GUI Interface  

---

## 🛠 Database Setup

### Step 1: Create Database
```sql
CREATE DATABASE employee_admin_db;
USE employee_admin_db;

CREATE TABLE employees(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    department VARCHAR(100),
    salary DOUBLE,
    email VARCHAR(100)
);
