# MotorPH Payroll System

A Java-based payroll system that processes employee salary information using attendance records stored in CSV files.
This system was developed as part of an **academic programming project** that demonstrates payroll computation, file processing, and menu-driven program design without using Object-Oriented Programming.

---

# Table of Contents

* Description
* Members and Roles
* Project Plan & Tracking
* Features
* Payroll Calculation Rules
* System Workflow
* Repository Structure
* Setup & How to Run
* Sample Output
* How the Code Works
* Employee Coverage
* Quality Assurance Test
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
# Project Plan File (Required Submission)

To comply with project documentation requirements, a project plan file (.xlsx) is included in the repository.

This file contains:
Effort estimation
Task breakdown
Timeline (11-week development plan)
Status tracking (Completed / Ongoing / Pending)

⚠️ Note:
This file is submitted locally in the repository to ensure verifiable tracking, as required by the project guidelines.

# Effort Estimation & Timeline

The project follows an 11-week development cycle based on the approved proposal.
| Week     | Task                  | Description                                  |
| -------- | --------------------- | -------------------------------------------- |
| Week 1–2 | Requirements Analysis | Review payroll rules and system requirements |
| Week 3   | System Design         | Plan program flow and structure              |
| Week 4–6 | Development           | Implement payroll computation logic          |
| Week 7   | File Handling         | Integrate CSV reading and parsing            |
| Week 8   | Testing               | Validate payroll calculations                |
| Week 9   | Debugging             | Fix errors and edge cases                    |
| Week 10  | Finalization          | Clean code and improve output                |
| Week 11  | Documentation         | Prepare README, reports, and submission      |

# Task Breakdown & Status Tracking

The table below shows how tasks were tracked throughout the project:
| Task                | Description                              | Assigned To | Status      |
| ------------------- | ---------------------------------------- | ----------- | ----------- |
| Requirements Review | Analyze payroll rules                    | Team        | ✅ Completed |
| Data Preparation    | Prepare employee dataset                 | Ephraim     | ✅ Completed |
| Attendance Parsing  | CSV reading implementation               | Ephraim     | ✅ Completed |
| Hours Calculation   | Compute working hours                    | Ephraim     | ✅ Completed |
| Payroll Logic       | Gross and net salary computation         | Ephraim     | ✅ Completed |
| Deductions          | Implement SSS, PhilHealth, Pag-IBIG, Tax | Ephraim     | ✅ Completed |
| Testing             | Validate outputs                         | Team        | ✅ Completed |
| Documentation       | README and reports                       | Team        | ✅ Completed |

# Monitoring and Updates
Project progress was monitored using:

📊 Google Sheets (Tracking)
💬 Gspace (Communication)
📅 Weekly progress meetings

* Reference 
* 📁 Effort Estimation and Project Plan Location: https://docs.google.com/spreadsheets/d/1bg2FKkk0l8Fj1jjAYKmEFeB9M2rPCL9t4WCPFp-kOy0/edit?usp=sharing
* 📁 Project proposal: https://docs.google.com/document/d/1b19nDt9lFxUkoMmdZsrNQftRKb9ow6P2Nwrh_Kzp-Mo/edit?usp=sharing
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
Attendance.csv
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
Total Hours Worked: 75.63333333333334
Gross Salary: 10129.572333333335
Net Salary: 10129.572333333335

Cutoff Date: June 16 to June 30
Total Hours Worked: 71.61666666666666
Gross Salary: 9591.620166666666

SSS: 877.5
PhilHealth: 295.8178875
Pag-IBIG: 100.0
Tax:  0.0

Total Deductions: 1273.3178874999999
Net Salary: 8318.302279166666
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

* Reads `Attendance.csv`
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
---

# Quality Assurance Testing

## Purpose

Quality Assurance (QA) Testing was conducted to ensure that the MotorPH Payroll System produces **accurate, consistent, and reliable payroll results** based on the given requirements.

This section validates that:

- Payroll computations are correct  
- Attendance data is properly processed  
- Edge cases and invalid inputs are handled  

## Sample Test Cases

The system was tested using multiple scenarios, including:

| SCENARIO                   | TEST CASE                   | EXPECTED RESULT                        | Status    |
|----------------------------|-----------------------------|----------------------------------------|-----------|
| Valid Login                | Correct username & password | Access granted                         | ✅ Passed |
| Invalid Login              | Incorrect credentials       | Program terminates                     | ✅ Passed |
| Employee Lookup            | Existing employee ID        | Displays employee info                 | ✅ Passed |
| Invalid Employee ID        | Non-existent ID             | Error / no result                      | ✅ Passed |
| Hours Calculation          | Valid time logs             | Correct hours computed                 | ✅ Passed |
| Late Login                 | After 8:00 AM               | Reduced working hours                  | ✅ Passed |
| Payroll Computation        | Full attendance data        | Correct gross & net salary             | ✅ Passed |
| Deductions                 | Combined cutoff salary      | Correct SSS, PhilHealth, Pag-IBIG, Tax | ✅ Passed |
| CSV Reading                | Valid CSV file              | Data loaded successfully               | ✅ Passed |
| Missing CSV                | No file found               | Error handled properly                 | ✅ Passed |

---

## Testing Approach

Testing was performed using:

- Manual test cases  
- Sample employee records  
- Actual attendance CSV data  

The outputs were compared against expected payroll results based on the defined rules.

---

## Result Summary

All major functionalities of the system were successfully tested:

- ✔ Accurate payroll computation  
- ✔ Correct deduction calculations  
- ✔ Reliable CSV file processing  
- ✔ Proper handling of invalid inputs  

This confirms that the system meets the **functional and accuracy requirements** specified in the project.



* Supporting File https://docs.google.com/spreadsheets/d/1dPl2252ppA8mLpPNYg4y1R6OM_oI9AJW-wQqP20fbwM/edit?usp=sharing

Detailed QA test cases are included in:


---

# References

MotorPH Company Website

[https://sites.google.com/mmdc.mcl.edu.ph/motorph/home](https://sites.google.com/mmdc.mcl.edu.ph/motorph/home)
