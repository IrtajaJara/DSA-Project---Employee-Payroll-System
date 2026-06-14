import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
class Main{


    static HashMap<Integer, Employee> map = new HashMap<>();
    static StackAction stack = new StackAction();
    static SalaryBST bst = new SalaryBST();
    static int nextId = 1001;
    static Scanner sc = new Scanner(System.in);

   public static void main(String[] args) {

        System.out.println("\n==========================================");
        System.out.println("   EMPLOYEE PAYROLL SYSTEM");
        System.out.println("==========================================");

        hire("Musa", "IT", 75000, "Engineer");
        hire("Sara", "HR", 65000, "Manager");
        hire("Fareed", "Finance", 85000, "Accountant");
        hire("Irtaja", "IT", 150000, "Manager");

        while (true) {
            System.out.println("\n========== MENU ==========");
            System.out.println("1. Hire Employee");
            System.out.println("2. Fire Employee");
            System.out.println("3. Search By ID");
            System.out.println("4. Search By Salary");
            System.out.println("5. Undo Last Action");
            System.out.println("6. Sorted Display By Salary");
            System.out.println("7. Show All Employees");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");

            int ch = Integer.parseInt(sc.nextLine());

            switch(ch) {
                case 1:
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Department: ");
                    String dept = sc.nextLine();
                    System.out.print("Salary: ");
                    double sal = Double.parseDouble(sc.nextLine());
                    System.out.print("Position: ");
                    String pos = sc.nextLine();
                    hire(name, dept, sal, pos);
                    break;

                case 2:
                    System.out.print("Employee ID: ");
                    int id = Integer.parseInt(sc.nextLine());
                    fire(id);
                    break;

                case 3:
                    System.out.print("Employee ID: ");
                    int searchId = Integer.parseInt(sc.nextLine());
                    searchById(searchId);
                    break;

                case 4:
                    System.out.print("Salary: ");
                    double searchSal = Double.parseDouble(sc.nextLine());
                    searchBySalary(searchSal);
                    break;

                case 5:
                    undo();
                    break;

                case 6:
                    sortedDisplay();
                    break;

                case 7:
                    showAll();
                    break;

                case 8:
                    System.out.println("\nThank you! Goodbye.");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid Choice! Please enter 1-8.");
            }
        }
    }

    static void hire(String name, String dept, double salary, String pos) {
        int id = nextId++;
        Employee e = new Employee(id, name, dept, salary, pos);
        map.put(id, e);
        bst.insert(e);
        stack.push(new Action("HIRE", e));
        System.out.println("HIRED: " + name + " (ID: " + id + ")");
    }

    static void fire(int id) {
        Employee e = map.get(id);
        if (e == null) {
            System.out.println("Employee Not Found!");
            return;
        }
        map.remove(id);
        bst.delete(e.salary);
        stack.push(new Action("FIRE", e));
        System.out.println("FIRED: " + e.name + " (ID: " + id + ")");
    }

    static void searchById(int id) {
        Employee e = map.get(id);
        if (e != null) {
            System.out.println("FOUND: " + e);
        } else {
            System.out.println("Not Found!");
        }
    }

    static void searchBySalary(double salary) {
        Employee e = bst.search(salary);
        if (e != null) {
            System.out.println("FOUND: " + e);
        } else {
            System.out.println("Not Found!");
        }
    }

    static void undo() {
        Action a = stack.pop();
        if (a == null) {
            System.out.println("Nothing To Undo!");
            return;
        }

        if (a.type.equals("HIRE")) {
            map.remove(a.employee.id);
            bst.delete(a.employee.salary);
            System.out.println("UNDO: Removed " + a.employee.name);
        }
        else if (a.type.equals("FIRE")) {
            map.put(a.employee.id, a.employee);
            bst.insert(a.employee);
            System.out.println("UNDO: Rehired " + a.employee.name);
        }
    }


    static void sortedDisplay() {
        System.out.println("\n===== SORTED BY SALARY (Low to High) =====");
        ArrayList<Employee> list = bst.getSorted();
        for (Employee e : list) {
            System.out.println(e);
        }
    }

    static void showAll() {
        if (map.isEmpty()) {
            System.out.println("No Employees!");
            return;
        }
        System.out.println("\n===== ALL EMPLOYEES =====");
        for (Employee e : map.values()) {
            System.out.println(e);
        }
        System.out.println("Total: " + map.size());
    }}