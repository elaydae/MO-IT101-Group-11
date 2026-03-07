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
  // ==============================
    // MAIN PROGRAM
    // ==============================
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        // Load employee data from CSV file
        String[][] employees = loadEmployees("Employee Details.csv");
        
        // Load attendance data from CSV file
        String[][] attendance = loadAttendance("Attendance.csv");

        System.out.println("======================================");
        System.out.println("         MOTORPH PAYROLL SYSTEM       ");
        System.out.println("======================================");

        
        // ==============================
        // LOGIN SYSTEM
        // ==============================
        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        // Validate username and password
        if (!(username.equals("employee") || username.equals("payroll_staff")) || !password.equals("12345")) {
            System.out.println("Incorrect username and/or password.");
            return;
        }
        // ==============================
        // EMPLOYEE USER MENU
        // ==============================
        if (username.equals("employee")) {

            System.out.println("1. Enter your employee number");
            System.out.println("2. Exit the program");

            String choice = sc.nextLine();

            if (choice.equals("1")) {

                System.out.print("Enter Employee Number: ");
                String id = sc.nextLine();

                String[] emp = findEmployee(employees, id);

                if (emp == null) {
                    System.out.println("Employee number does not exist.");
                    return;
                }
        // Display employee details
                System.out.println("Employee Number: " + emp[0]);
                System.out.println("Employee Name: " + emp[2] + " " + emp[1]);
                System.out.println("Birthday: " + emp[3]);
            }

            return;
        }
         // ==============================
        // PAYROLL STAFF MENU
        // ==============================
        if (username.equals("payroll_staff")) {

            System.out.println("1. Process Payroll");
            System.out.println("2. Exit");

            String choice = sc.nextLine();

        // Process payroll for one employee
            if (!choice.equals("1")) return;

            System.out.println("1. One employee");
            System.out.println("2. All employees");
            System.out.println("3. Exit");

            String option = sc.nextLine();

            if (option.equals("1")) {

                System.out.print("Enter employee number: ");
                String id = sc.nextLine();

                String[] emp = findEmployee(employees, id);

                if (emp == null) {
                    System.out.println("Employee number does not exist.");
                    return;
                }

                processEmployee(emp, attendance);
            }
            // Process payroll for all employees
            if (option.equals("2")) {

                for (String[] emp : employees) {
                    processEmployee(emp, attendance);
                }
            }
        }
    }
    // ==========================================
    // PAYROLL PROCESSING FOR EACH EMPLOYEE
    // ==========================================
    static void processEmployee(String[] emp, String[][] attendance) {

    // Months required by instruction (June to December)
        int[] months = {6,7,8,9,10,11,12};

        for (int m : months) {

    // Get hourly rate from CSV
            double hourly = Double.parseDouble(emp[18]);

    // FIRST CUTOFF (1 - 15)
            double hours1 = hoursWorked(attendance, emp[0], m, 1, 15);
            double gross1 = hours1 * hourly;

    // SECOND CUTOFF (16 - end of month)
            int lastDay = lastDay(m);
            double hours2 = hoursWorked(attendance, emp[0], m, 16, lastDay);
            double gross2 = hours2 * hourly;
            
    // Combine both cutoffs for deduction calculation
            double totalGross = gross1 + gross2;

    // Government deductions
            double sss = computeSSS(totalGross);
            double phil = computePhil(totalGross);
            double pagibig = computePagibig(totalGross);

    // Taxable income
            double taxable = totalGross - sss - phil - pagibig;

    // Withholding tax
            double tax = computeTax(taxable);

            double totalDeduction = sss + phil + pagibig + tax;

    // Net salary calculations    
            double net1 = gross1;
            double net2 = gross2 - totalDeduction;

    // ==============================
    // DISPLAY PAYROLL RESULT
    // ==============================
            System.out.println("--------------------------------");
            System.out.println("Employee #: " + emp[0]);
            System.out.println("Employee Name: " + emp[2] + " " + emp[1]);
            System.out.println("Birthday: " + emp[3]);

            System.out.println("Cutoff Date: Month " + m + " 1-15");
            System.out.println("Total Hours Worked: " + hours1);
            System.out.println("Gross Salary: " + gross1);
            System.out.println("Net Salary: " + net1);

            System.out.println("Cutoff Date: Month " + m + " 16-" + lastDay);
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

    
    // ==========================================
    // CALCULATE HOURS WORKED
    // Only count time between 8:00 AM and 5:00 PM
    // ==========================================
    
    static double hoursWorked(String[][] attendance, String id, int month, int start, int end) {

        double minutes = 0;

        for (String[] row : attendance) {

            if (!row[0].equals(id)) continue;

            String[] d = row[3].split("/");

            int m = Integer.parseInt(d[0]);
            int day = Integer.parseInt(d[1]);

            if (m != month) continue;
            if (day < start || day > end) continue;

            int[] login = parseTime(row[4]);
            int[] logout = parseTime(row[5]);

            int loginMin = login[0]*60 + login[1];
            int logoutMin = logout[0]*60 + logout[1];

    // Grace period: if employee logs in before 8:10
            if (loginMin <= 8*60+10) loginMin = 8*60;
            
    // Cap working time until 5PM        
            if (logoutMin > 17*60) logoutMin = 17*60;

            int worked = logoutMin - loginMin;

            if (worked > 0) minutes += worked;
        }

        return minutes/60.0;
    }
    // Convert time string to hours and minutes
    static int[] parseTime(String t){

        String[] p = t.split(":");
        int h = Integer.parseInt(p[0]);
        int m = Integer.parseInt(p[1]);

        return new int[]{h,m};
    }
    // Get last day of month
    static int lastDay(int m){

        if(m==2) return 28;
        if(m==4||m==6||m==9||m==11) return 30;
        return 31;
    }

        // ==========================================
    // GOVERNMENT DEDUCTION COMPUTATIONS
    // ==========================================

    // SSS Contribution
    static double computeSSS(double salary){

        if(salary < 3250) return 135;
        if(salary >= 24750) return 1125;

        int bracket = (int)((salary-3250)/500);

        return 157.5 + bracket*22.5;
    }
    // PhilHealth Contribution
    static double computePhil(double salary){

        if(salary <=10000) return 150;

        if(salary >=60000) return 900;

        return (salary*0.03)/2;
    }
    // Pag-IBIG Contribution
    static double computePagibig(double salary){

        double rate = salary<=1500 ? 0.01 : 0.02;

        double contrib = salary*rate;

        if(contrib>100) return 100;

        return contrib;
    }
    // Withholding Tax
    static double computeTax(double income){

        if(income<=20832) return 0;

        if(income<=33332) return (income-20833)*0.20;

        if(income<=66666) return 2500 + (income-33333)*0.25;

        if(income<=166666) return 10833 + (income-66667)*0.30;

        if(income<=666666) return 40833.33 + (income-166667)*0.32;

        return 200833.33 + (income-666667)*0.35;
    }

    // ==========================================
    // FIND EMPLOYEE BY ID
    // ==========================================
    static String[] findEmployee(String[][] employees, String id){

        for(String[] emp: employees){

            if(emp[0].equals(id)) return emp;
        }

        return null;
    }

    // ==========================================
    // LOAD EMPLOYEE CSV FILE
    // ==========================================
   static String[][] loadEmployees(String file) throws Exception {

    BufferedReader br = new BufferedReader(new FileReader(file));
    List<String[]> list = new ArrayList<>();

    String line;
    boolean header = true;

    while ((line = br.readLine()) != null) {

        if (header) {
            header = false;
            continue;
        }

        // Proper CSV split that ignores commas inside quotes
        String[] row = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        for (int i = 0; i < row.length; i++) {
            row[i] = row[i].replace("\"", "").trim();
        }

        // Remove commas from salary fields
        if (row.length > 18) {
            row[18] = row[18].replace(",", "");
        }

        list.add(row);
    }

    br.close();

    return list.toArray(String[][]::new);
}
    // ==========================================
    // LOAD ATTENDANCE CSV FILE
    // ==========================================
    static String[][] loadAttendance(String file) throws Exception{

        BufferedReader br = new BufferedReader(new FileReader(file));

        List<String[]> list = new ArrayList<>();

        String line;

        boolean header=true;

        while((line=br.readLine())!=null){
    
    // Skip header
            if(header){header=false;continue;}

            String[] row = line.split(",");

            list.add(row);
        }

        br.close();

        return list.toArray(String[][]::new);
    }
}
