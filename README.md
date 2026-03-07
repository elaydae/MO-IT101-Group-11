# MotorPH Payroll System

## Description

**MotorPH Payroll System** is a Java-based payroll system that reads employee and attendance data from CSV files and calculates employee salaries using cutoff periods.

The program includes:

* Login authentication
* Employee information lookup
* Payroll processing for one or all employees
* Government deduction calculations (SSS, PhilHealth, Pag-IBIG, Withholding Tax)

The system processes employee attendance records and computes payroll from **June to December** following the payroll rules defined in the project requirements. 

---

# Members and Roles

**Ephraim Elayda** — Programmer / Research
**Ashley Mackenzie Ramos** — Research / Project Coordinator
**Ma. Irene Andrea Esguerra** — Research / Project Coordinator

---

# Features

* Login authentication system
* Employee role interface
* Payroll staff interface
* Employee information lookup
* Payroll computation for:

  * One employee
  * All employees
* Attendance CSV reading
* Government deductions computation
* Payroll output per cutoff period
* Payroll coverage from **June to December**

---

# Payroll Calculation Rules

## Hours Worked

* Only hours between **8:00 AM – 5:00 PM** are counted.
* Extra hours are **not included**.
* Example:

  * Login **8:30 AM**, logout **5:30 PM** → **7.5 hours**
  * Login **8:05 AM**, logout **5:00 PM** → **8 hours**
* One-hour **lunch break deduction** is applied.

## Gross Pay

Gross Pay =

```
Hours Worked × Hourly Rate
```

## Cutoff Periods

Payroll is divided into two cutoff periods:

**1st Cutoff**

```
Day 1 – Day 15
```

**2nd Cutoff**

```
Day 16 – End of Month
```

* **1st cutoff:** No deductions applied
* **2nd cutoff:** All deductions applied

## Deductions

Government deductions are computed using the **combined gross salary of both cutoffs**.

Deductions include:

* SSS
* PhilHealth
* Pag-IBIG
* Withholding Tax

## Withholding Tax Table

The withholding tax follows the Philippine **TRAIN tax table** based on taxable income after deductions.

---

# Repository Structure

```
MotorPH-Payroll-System
│
├── MotorPHPayroll.java
├── attendance.csv
└── README.md
```

Rules from the requirement:

* Only **one Java file** must exist in the repository.
* No external libraries are used.

---

# Setup & How to Run

## Prerequisites

* Java JDK 8 or higher
* Terminal / Command Prompt
* Attendance CSV file

---

## Step 1 — Export Attendance CSV

1. Open the spreadsheet
   **Copy_of_MotorPH_Employee_Data.xlsx**

2. Select the sheet
   **Attendance Record**

3. Export it as:

```
CSV (Comma Delimited)
```

4. Save the file as:

```
attendance.csv
```

5. Place the file in the **same folder as the Java file**.

---

## Step 2 — Compile

Open terminal inside the project folder and run:

```
javac MotorPHPayroll.java
```

---

## Step 3 — Run

```
java MotorPHPayroll
```

---

# Sample Output

## Invalid Login Example

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
[1] Enter your employee number
[2] Exit
```

If the employee number exists:

```
Employee #
Employee Name
Birthday
```

---

## Payroll Staff Role Example

```
Username: payroll_staff
Password: 12345

PAYROLL STAFF MENU
1. Process Payroll
2. Exit
```

Sub-menu:

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

## 1. `main()` — Program Entry Point & Login

Handles:

* System startup
* Username and password validation
* Role routing

Valid users:

```
employee
payroll_staff
```

Password:

```
12345
```

---

## 2. `getEmployees()` — Employee Data Storage

Because the requirement states that **no external libraries should be used**, the employee data from:

```
Copy_of_MotorPH_Employee_Data.xlsx
```

is embedded directly inside the program as a **2D String array**.

Each row represents **one employee record**.

---

## 3. `loadAttendanceCsv()` — Reading Attendance from File

This function:

* Loads `attendance.csv`
* Reads attendance records
* Parses CSV lines
* Stores them into a 2D array for payroll computation.

---

## 4. `calculateHoursWorked()` — Core Time Calculation

This method calculates employee working hours by:

* Filtering employee ID
* Filtering date range
* Parsing login and logout times
* Limiting work hours between **8 AM – 5 PM**
* Deducting **1 hour lunch break**

---

## 5. `printFullPayrollForEmployee()` — Full Payroll Across All Months

This method prints payroll results for:

```
June
July
August
September
October
November
December
```

It calculates:

* First cutoff payroll
* Second cutoff payroll
* Monthly deductions

---

## 6. `computeSSS()` — SSS Contribution Lookup

Computes SSS contribution based on the employee's **monthly salary bracket**.

---

## 7. `computePhilhealth()` — PhilHealth Contribution

PhilHealth is computed using the formula:

```
Monthly Salary × 3%
```

Then divided between employer and employee.

---

## 8. `computePagibig()` — Pag-IBIG Contribution

Contribution rates:

```
1% for salary ≤ 1500
2% for salary > 1500
```

Maximum contribution:

```
100 pesos
```

---

## 9. `computeWithholdingTax()` — Withholding Tax

Uses the Philippine **TRAIN tax bracket table** to compute employee withholding tax.

---

## 10. `parseDate()` and `parseTime()` — String Parsing

These helper functions convert:

```
Date strings → Integer arrays
Time strings → Hour & minute values
```

Used during attendance processing.

---

## 11. `findEmployee()` — Employee Lookup

Searches the employee list using:

```
Employee Number
```

Returns the matching employee record.

---

## 12. `parseCsvLine()` — Robust CSV Parsing

Handles CSV parsing safely by:

* Supporting quoted values
* Splitting columns correctly
* Removing unnecessary characters

---

# Employee Coverage

The system includes **34 employees** embedded in the program based on the provided employee dataset.

Each employee record contains:

* Employee ID
* Name
* Birthday
* Address
* Government IDs
* Position
* Supervisor
* Salary information
* Hourly rate

---

# References

MotorPH Official Website
[https://sites.google.com/mmdc.mcl.edu.ph/motorph/home](https://sites.google.com/mmdc.mcl.edu.ph/motorph/home)


