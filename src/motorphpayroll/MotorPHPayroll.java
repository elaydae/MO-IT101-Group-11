/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package motorphpayroll;

/**
 *
 * @author Ephraim
 */
import java.io.*;
import java.util.*;

public class MotorPHPayroll {

    // ==========================================================
    // MAIN PROGRAM CONTROLLER
    // Responsible for: login, menu navigation, program start
    // ==========================================================
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        // Load data from CSV files
        String[][] employeeData = loadEmployees("Employee Details.csv");
        String[][] attendanceData = loadAttendance("Attendance.csv");

        printSystemHeader();

        // --- LOGIN ---
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (!isValidLogin(username, password)) {
            System.out.println("Incorrect username and/or password.");
            return;
        }

        if (username.equals("employee")) {
            runEmployeeMenu(scanner, employeeData);
        } else {
            runPayrollStaffMenu(scanner, employeeData, attendanceData);
        }
    }

    // ==========================================================
    // SYSTEM DISPLAY METHODS
    // Responsible for: printing menus and system messages
    // ==========================================================

    static void printSystemHeader() {
        System.out.println("======================================");
        System.out.println("         MOTORPH PAYROLL SYSTEM       ");
        System.out.println("======================================");
    }

    static boolean isValidLogin(String username, String password) {
        boolean validUser = username.equals("employee") || username.equals("payroll_staff");
        boolean validPass = password.equals("12345");
        return validUser && validPass;
    }

    // ==========================================================
    // EMPLOYEE MENU
    // Allows employee to view personal details
    // ==========================================================

    static void runEmployeeMenu(Scanner scanner, String[][] employeeData) {

        System.out.println("1. Enter your employee number");
        System.out.println("2. Exit the program");

        String choice = scanner.nextLine();

        if (!choice.equals("1")) return;

        System.out.print("Enter Employee Number: ");
        String employeeId = scanner.nextLine();

        String[] employee = findEmployee(employeeData, employeeId);

        if (employee == null) {
            System.out.println("Employee number does not exist.");
            return;
        }

        System.out.println("Employee Number: " + employee[0]);
        System.out.println("Employee Name: " + employee[2] + " " + employee[1]);
        System.out.println("Birthday: " + employee[3]);
    }

    // ==========================================================
    // PAYROLL STAFF MENU
    // Allows payroll staff to compute salaries
    // ==========================================================

    static void runPayrollStaffMenu(
            Scanner scanner,
            String[][] employeeData,
            String[][] attendanceData) {

        System.out.println("1. Process Payroll");
        System.out.println("2. Exit");

        if (!scanner.nextLine().equals("1")) return;

        System.out.println("1. One employee");
        System.out.println("2. All employees");
        System.out.println("3. Exit");

        String option = scanner.nextLine();

        if (option.equals("1")) {

            System.out.print("Enter employee number: ");
            String employeeId = scanner.nextLine();

            String[] employee = findEmployee(employeeData, employeeId);

            if (employee == null) {
                System.out.println("Employee number does not exist.");
                return;
            }

            processEmployeePayroll(employee, attendanceData);
        }

        if (option.equals("2")) {

            for (String[] employee : employeeData) {
                processEmployeePayroll(employee, attendanceData);
            }
        }
    }

    // ==========================================================
    // PAYROLL PROCESSING
    // Responsible for computing employee salary June–December
    // ==========================================================

    static void processEmployeePayroll(String[] employee, String[][] attendanceData) {

        int startMonth = 6;
        int endMonth = 12;

        double hourlyRate = Double.parseDouble(employee[18]);

        for (int month = startMonth; month <= endMonth; month++) {

            int lastDayOfMonth = getLastDayOfMonth(month);

            double firstCutoffHours =
                    calculateWorkedHours(attendanceData, employee[0], month, 1, 15);

            double secondCutoffHours =
                    calculateWorkedHours(attendanceData, employee[0], month, 16, lastDayOfMonth);

            double firstCutoffGross = firstCutoffHours * hourlyRate;
            double secondCutoffGross = secondCutoffHours * hourlyRate;

            double monthlyGross = firstCutoffGross + secondCutoffGross;

            // --- Government deductions calculated on monthly total ---
            double sss = computeSSS(monthlyGross);
            double philHealth = computePhilHealth(monthlyGross);
            double pagibig = computePagibig(monthlyGross);

            double taxableIncome = monthlyGross - sss - philHealth - pagibig;
            double withholdingTax = computeTax(taxableIncome);

            double totalDeductions = sss + philHealth + pagibig + withholdingTax;

            /*
            MotorPH payout rule:

            First cutoff (1–15):
            - Employee receives full gross salary
            - No deductions applied yet

            Second cutoff (16–end):
            - All government deductions for the month are applied
            */

            double firstCutoffNet = firstCutoffGross;
            double secondCutoffNet = secondCutoffGross - totalDeductions;

            printPayroll(
                    employee,
                    month,
                    lastDayOfMonth,
                    firstCutoffHours,
                    secondCutoffHours,
                    firstCutoffGross,
                    secondCutoffGross,
                    firstCutoffNet,
                    secondCutoffNet,
                    sss,
                    philHealth,
                    pagibig,
                    withholdingTax,
                    totalDeductions
            );
        }
    }

    // ==========================================================
    // HOURS WORKED CALCULATION
    // Only counts time between 8:00 AM and 5:00 PM
    // ==========================================================

    static double calculateWorkedHours(
            String[][] attendanceData,
            String employeeId,
            int month,
            int startDay,
            int endDay) {

        double totalMinutesWorked = 0;

        for (String[] attendanceRow : attendanceData) {

            if (!attendanceRow[0].equals(employeeId)) continue;

            String[] dateParts = attendanceRow[3].split("/");

            int recordMonth = Integer.parseInt(dateParts[0]);
            int recordDay = Integer.parseInt(dateParts[1]);

            if (recordMonth != month) continue;
            if (recordDay < startDay || recordDay > endDay) continue;

            int[] loginTime = parseTime(attendanceRow[4]);
            int[] logoutTime = parseTime(attendanceRow[5]);

            int loginMinutes = loginTime[0] * 60 + loginTime[1];
            int logoutMinutes = logoutTime[0] * 60 + logoutTime[1];

            if (loginMinutes <= 8 * 60 + 10) loginMinutes = 8 * 60;
            if (logoutMinutes > 17 * 60) logoutMinutes = 17 * 60;

            int minutesWorked = logoutMinutes - loginMinutes;

            if (minutesWorked > 0) {
                totalMinutesWorked += minutesWorked;
            }
        }

        return totalMinutesWorked / 60.0;
    }

    // ==========================================================
    // UTILITY METHODS
    // ==========================================================

    static int[] parseTime(String time) {
        String[] parts = time.split(":");
        return new int[]{
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1])
        };
    }

    static int getLastDayOfMonth(int month) {

        if (month == 2) return 28;
        if (month == 4 || month == 6 || month == 9 || month == 11) return 30;

        return 31;
    }

    // ==========================================================
    // GOVERNMENT DEDUCTIONS
    // ==========================================================

    static double computeSSS(double salary) {

        if (salary < 3250) return 135;
        if (salary >= 24750) return 1125;

        int bracket = (int) ((salary - 3250) / 500);
        return 157.5 + bracket * 22.5;
    }

    static double computePhilHealth(double salary) {

        if (salary <= 10000) return 150;
        if (salary >= 60000) return 900;

        return (salary * 0.03) / 2;
    }

    static double computePagibig(double salary) {

        double rate = salary <= 1500 ? 0.01 : 0.02;
        double contribution = salary * rate;

        return contribution > 100 ? 100 : contribution;
    }

    static double computeTax(double income) {

        if (income <= 20832) return 0;
        if (income <= 33332) return (income - 20833) * 0.20;
        if (income <= 66666) return 2500 + (income - 33333) * 0.25;
        if (income <= 166666) return 10833 + (income - 66667) * 0.30;
        if (income <= 666666) return 40833.33 + (income - 166667) * 0.32;

        return 200833.33 + (income - 666667) * 0.35;
    }

    // ==========================================================
    // DATA ACCESS METHODS (CSV)
    // ==========================================================

    static String[] findEmployee(String[][] employees, String employeeId) {

        for (String[] employee : employees) {
            if (employee[0].equals(employeeId)) {
                return employee;
            }
        }

        return null;
    }

    static String[][] loadEmployees(String file) throws Exception {

        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String[]> employees = new ArrayList<>();

        String line;
        boolean header = true;

        while ((line = reader.readLine()) != null) {

            if (header) {
                header = false;
                continue;
            }

            String[] row =
                    line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

            for (int i = 0; i < row.length; i++) {
                row[i] = row[i].replace("\"", "").trim();
            }

            if (row.length > 18) {
                row[18] = row[18].replace(",", "");
            }

            employees.add(row);
        }

        reader.close();

        return employees.toArray(String[][]::new);
    }

    static String[][] loadAttendance(String file) throws Exception {

        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String[]> attendance = new ArrayList<>();

        String line;
        boolean header = true;

        while ((line = reader.readLine()) != null) {

            if (header) {
                header = false;
                continue;
            }

            String[] row = line.split(",");
            attendance.add(row);
        }

        reader.close();

        return attendance.toArray(String[][]::new);
    }

    // ==========================================================
    // PAYROLL OUTPUT
    // ==========================================================

    static void printPayroll(
            String[] employee,
            int month,
            int lastDay,
            double hours1,
            double hours2,
            double gross1,
            double gross2,
            double net1,
            double net2,
            double sss,
            double phil,
            double pagibig,
            double tax,
            double totalDeduction) {

        System.out.println("--------------------------------");
        System.out.println("Employee #: " + employee[0]);
        System.out.println("Employee Name: " + employee[2] + " " + employee[1]);
        System.out.println("Birthday: " + employee[3]);

        System.out.println("Cutoff Date: Month " + month + " 1-15");
        System.out.println("Total Hours Worked: " + hours1);
        System.out.println("Gross Salary: " + gross1);
        System.out.println("Net Salary: " + net1);

        System.out.println("Cutoff Date: Month " + month + " 16-" + lastDay);
        System.out.println("Total Hours Worked: " + hours2);
        System.out.println("Gross Salary: " + gross2);

        System.out.println("SSS: " + sss);
        System.out.println("PhilHealth: " + phil);
        System.out.println("Pag-IBIG: " + pagibig);
        System.out.println("Tax: " + tax);

        System.out.println("Total Deductions: " + totalDeduction);
        System.out.println("Net Salary: " + net2);
        System.out.println("--------------------------------");
    }
}
