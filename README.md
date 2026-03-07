# MotorPH Payroll System

A Java-based payroll system that processes employee salary information using attendance records stored in CSV files.

This system was developed as part of an **academic programming project** that demonstrates payroll computation, file processing, and menu-driven program design without using Object-Oriented Programming.

---

# Table of Contents

* Description
* Members and Roles
* Features
* Payroll Calculation Rules
* System Workflow
* Repository Structure
* Setup & How to Run
* Sample Output
* How the Code Works
* Employee Coverage
* References

---

# Description

The **MotorPH Payroll System** is a Java program designed to simulate a basic payroll processing system for a company.

The program reads employee and attendance data from spreadsheet files and calculates salaries based on **cutoff payroll periods**.

The system supports two user roles:

* **Employee** – View employee information
* **Payroll Staff** – Process payroll for one or all employees

Payroll calculations include:

* Hours worked
* Gross salary
* Government deductions
* Net salary

All computations follow the payroll rules provided in the project specification. 

---

# Members and Roles

| Name                      | Role                           |
| ------------------------- | ------------------------------ |
| Ephraim Elayda            | Programmer / Research          |
| Ashley Mackenzie Ramos    | Research / Project Coordinator |
| Ma. Irene Andrea Esguerra | Research / Project Coordinator |

---

# Features

### Login Authentication

The program requires users to log in before accessing the system.

Valid usernames:

```
employee
payroll_staff
```

Password:

```
12345
```

---

### Employee Role

Employees can:

* Enter their **Employee Number**
* View their personal information

Displayed details include:

* Employee Number
* Employee Name
* Birthday

---

### Payroll Staff Role

Payroll staff can:

* Process payroll for **one employee**
* Process payroll for **all employees**

Payroll reports include:

* Cutoff period
* Hours worked
* Gross salary
* Government deductions
* Net salary

---

# Payroll Calculation Rules

## Work Hours

Only hours between **8:00 AM and 5:00 PM** are counted.

Examples:

| Login   | Logout  | Hours Counted |
| ------- | ------- | ------------- |
| 8:30 AM | 5:30 PM | 7.5 hours     |
| 8:05 AM | 5:00 PM | 8 hours       |

Rules:

* Extra hours are **not included**
* **1 hour lunch break** is deducted
* No rounding of values is allowed

---

## Payroll Cutoff Periods

Each month is divided into two payroll periods.

**First Cutoff**

```
Day 1 – Day 15
```

**Second Cutoff**

```
Day 16 – End of Month
```

Rules:

* First cutoff → **no deductions**
* Second cutoff → **all deductions applied**

---

## Government Deductions

Deductions are calculated based on the **combined gross salary of both cutoffs**.

The following deductions are included:

* SSS
* PhilHealth
* Pag-IBIG
* Withholding Tax

---

# System Workflow

1. Program starts
2. User enters **username and password**
3. System verifies credentials
4. Menu displayed depending on role
5. Payroll data processed
6. Payroll results printed in the console

---

# Repository Structure

```
MotorPH-Payroll-System
│
├── MotorPHPayroll.java
├── attendance.csv
└── README.md
```

Important project rules:

* Only **one Java file** is allowed
* No external libraries are used
* All employee data is embedded in the program

---

# Setup & How to Run

## Prerequisites

* Java JDK 8 or higher
* Terminal or Command Prompt
* Attendance CSV file

---

## Step 1 — Export Attendance CSV

1. Open:

```
Copy_of_MotorPH_Employee_Data.xlsx
```

2. Select sheet:

```
Attendance Record
```

3. Export the sheet as:

```
CSV (Comma Delimited)
```

4. Save the file as:

```
attendance.csv
```

5. Place the file in the **same folder as the Java file**.

---

## Step 2 — Compile the Program

Open terminal inside the project folder.

Run:

```
javac MotorPHPayroll.java
```

---

## Step 3 — Run the Program

```
java MotorPHPayroll
```

---

# Sample Output

## Invalid Login

```
Username: admin
Password: 12345

Incorrect username and/or password.
```

Program terminates immediately.

---

## Employee Role Example

```
Username: employee
Password: 12345

EMPLOYEE MENU
1. Enter your employee number
2. Exit
```

If the employee exists:

```
Employee Number: 10001
Employee Name: Manuel III Garcia
Birthday: 10/11/1983
```

---

## Payroll Staff Example

```
Username: payroll_staff
Password: 12345

PAYROLL STAFF MENU
1. Process Payroll
2. Exit
```

Submenu:

```
1. One Employee
2. All Employees
3. Exit
```

Example payroll output:

```
PAYROLL — June 2024

Employee #: 10009
Employee Name: Rosie Atienza
Birthday: 09/24/1948

Cutoff Date: June 1 to June 15
Total Hours Worked:
Gross Salary:
Net Salary:

Cutoff Date: June 16 to June 30
Total Hours Worked:
Gross Salary:

SSS:
PhilHealth:
Pag-IBIG:
Tax:

Total Deductions:
Net Salary:
```

---

# How the Code Works

## 1. `main()` — Program Entry Point

Responsible for:

* Loading employee data
* Loading attendance records
* Handling login authentication
* Routing users to the correct menu

---

## 2. `getEmployees()` — Employee Data Storage

Employee records from the spreadsheet are stored inside the program as a **two-dimensional string array**.

Each row represents:

```
One employee record
```

Each column represents fields such as:

* Employee ID
* Name
* Birthday
* Position
* Salary
* Hourly rate

---

## 3. `loadAttendanceCsv()` — Attendance Data Loader

This method:

* Reads `attendance.csv`
* Parses each line
* Stores attendance data in memory

---

## 4. `calculateHoursWorked()` — Time Calculation

This function calculates the number of hours worked by an employee.

The method:

* Filters attendance records by employee ID
* Checks date ranges
* Calculates working minutes
* Applies lunch break deduction
* Converts minutes to hours

---

## 5. `printFullPayrollForEmployee()`

This method prints payroll data for every month from:

```
June
July
August
September
October
November
December
```

For each month it computes:

* First cutoff payroll
* Second cutoff payroll
* Government deductions

---

## 6. Government Deduction Methods

The system includes separate functions to compute each deduction:

### `computeSSS()`

Determines SSS contribution using salary brackets.

### `computePhilhealth()`

Calculates PhilHealth contribution based on monthly salary.

### `computePagibig()`

Computes Pag-IBIG contribution with a maximum limit.

### `computeWithholdingTax()`

Calculates tax using the Philippine **TRAIN tax table**.

---

## 7. Helper Functions

Several helper functions are used for data processing:

| Function         | Purpose                                    |
| ---------------- | ------------------------------------------ |
| `parseDate()`    | Converts date strings to numbers           |
| `parseTime()`    | Converts time strings to hours and minutes |
| `findEmployee()` | Searches employee records                  |
| `parseCsvLine()` | Handles CSV parsing safely                 |

---

# Employee Coverage

The system includes **34 employees** embedded in the program.

Employee information includes:

* Employee ID
* Full Name
* Birthday
* Address
* Government ID numbers
* Position
* Supervisor
* Salary
* Hourly rate

---

# References

MotorPH Company Website

[https://sites.google.com/mmdc.mcl.edu.ph/motorph/home](https://sites.google.com/mmdc.mcl.edu.ph/motorph/home)

If you want, I can also show you **3 small improvements professors usually give extra points for on GitHub projects** (most students don’t include them).
