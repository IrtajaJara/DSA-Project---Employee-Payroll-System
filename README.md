==============================================
    DSA-PROJECT - EMPLOYEE PAYROLL SYSTEM
==============================================

An Employee Management System using Stack for undoable HR actions, BST for 
searching employees by salary, and HashMap for O(1) employee lookup by ID.

==============================================
               PROJECT DESCRIPTION
==============================================

The Employee Payroll System streamlines HR operations by enabling employee 
hiring, firing, instant ID-based lookup, salary-based search, undoable actions, 
and sorted salary display. This system demonstrates core Data Structure concepts 
in a real-world workforce management application.

==============================================
              DATA STRUCTURES USED
==============================================

| Data Structure | Role                          | Time Complexity |
|----------------|-------------------------------|-----------------|
| HashMap        | Employee lookup by ID         | O(1)            |
| Binary Search Tree (BST) | Salary search & sorted display | O(log n)    |
| Stack          | Undo last action              | O(1)            |

==============================================
                FEATURES (7)
==============================================

| # | Feature                   | Data Structure Used      | Time Complexity |
|---|---------------------------|--------------------------|-----------------|
| 1 | Hire Employee             | HashMap + BST + Stack    | O(1) + O(log n) |
| 2 | Fire Employee             | HashMap + BST + Stack    | O(1) + O(log n) |
| 3 | Search By ID              | HashMap                  | O(1)            |
| 4 | Search By Salary          | BST                      | O(log n)        |
| 5 | Undo Last Action          | Stack                    | O(1)            |
| 6 | Sorted Display By Salary  | BST (In-order traversal) | O(n)            |
| 7 | Show All Employees        | HashMap traversal        | O(n)            |

==============================================
               MENU OPTIONS
==============================================

==========================================
        EMPLOYEE PAYROLL SYSTEM
==========================================

========== MENU ==========
1. Hire Employee
2. Fire Employee
3. Search By ID
4. Search By Salary
5. Undo Last Action
6. Sorted Display By Salary
7. Show All Employees
8. Exit
Enter choice: 

==============================================
              PROJECT STRUCTURE
==============================================

EmployeePayrollSystem/
├── Employee.java          # Employee model class
├── Action.java            # Action model class (HIRE/FIRE)
├── StackAction.java       # Stack implementation for undo
├── BSTNode.java           # BST Node class
├── SalaryBST.java         # BST implementation for salary operations
└── Main.java              # Main driver class with menu

==============================================
            COMPLETE SOURCE CODE
==============================================

========================== Employee.java ==========================

public class Employee {
    int id;
    String name;
    String department;
    double salary;
    String position;

    public Employee(int id, String name, String department, double salary, String position) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.position = position;
    }

    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Dept: " + department + 
               " | Salary: " + salary + " | Position: " + position;
    }
}

========================== Action.java ==========================

public class Action {
    String type;
    Employee employee;

    public Action(String type, Employee employee) {
        this.type = type;
        this.employee = employee;
    }
}

========================== StackAction.java ==========================

import java.util.ArrayList;

public class StackAction {
    ArrayList<Action> list = new ArrayList<>();

    void push(Action a) {
        list.add(a);
    }

    Action pop() {
        if (list.isEmpty()) return null;
        return list.remove(list.size() - 1);
    }

    boolean isEmpty() {
        return list.isEmpty();
    }
}

========================== BSTNode.java ==========================

public class BSTNode {
    Employee emp;
    BSTNode left;
    BSTNode right;

    BSTNode(Employee emp) {
        this.emp = emp;
        left = null;
        right = null;
    }
}

========================== SalaryBST.java ==========================

import java.util.ArrayList;

public class SalaryBST {
    BSTNode root;

    void insert(Employee emp) {
        root = insertRec(root, emp);
    }

    BSTNode insertRec(BSTNode root, Employee emp) {
        if (root == null) {
            return new BSTNode(emp);
        }
        if (emp.salary < root.emp.salary) {
            root.left = insertRec(root.left, emp);
        } else if (emp.salary > root.emp.salary) {
            root.right = insertRec(root.right, emp);
        }
        return root;
    }

    Employee search(double salary) {
        return searchRec(root, salary);
    }

    Employee searchRec(BSTNode root, double salary) {
        if (root == null) {
            return null;
        }
        if (salary == root.emp.salary) {
            return root.emp;
        }
        if (salary < root.emp.salary) {
            return searchRec(root.left, salary);
        } else {
            return searchRec(root.right, salary);
        }
    }

    void delete(double salary) {
        root = deleteRec(root, salary);
    }

    BSTNode deleteRec(BSTNode root, double salary) {
        if (root == null) {
            return null;
        }
        if (salary < root.emp.salary) {
            root.left = deleteRec(root.left, salary);
        } else if (salary > root.emp.salary) {
            root.right = deleteRec(root.right, salary);
        } else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            BSTNode temp = root.right;
            while (temp.left != null) {
                temp = temp.left;
            }
            root.emp = temp.emp;
            root.right = deleteRec(root.right, temp.emp.salary);
        }
        return root;
    }

    ArrayList<Employee> getSorted() {
        ArrayList<Employee> list = new ArrayList<>();
        inorder(root, list);
        return list;
    }

    void inorder(BSTNode root, ArrayList<Employee> list) {
        if (root != null) {
            inorder(root.left, list);
            list.add(root.emp);
            inorder(root.right, list);
        }
    }
}

========================== Main.java ==========================

import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

class Main {

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
        } else if (a.type.equals("FIRE")) {
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
    }
}

==============================================
              SAMPLE OUTPUT
==============================================

==========================================
   EMPLOYEE PAYROLL SYSTEM
==========================================

HIRED: Musa (ID: 1001)
HIRED: Sara (ID: 1002)
HIRED: Fareed (ID: 1003)
HIRED: Irtaja (ID: 1004)

========== MENU ==========
1. Hire Employee
2. Fire Employee
3. Search By ID
4. Search By Salary
5. Undo Last Action
6. Sorted Display By Salary
7. Show All Employees
8. Exit
Enter choice: 7

===== ALL EMPLOYEES =====

ID: 1001 | Name: Musa | Dept: IT | Salary: 75000.0 | Position: Engineer

ID: 1002 | Name: Sara | Dept: HR | Salary: 65000.0 | Position: Manager

ID: 1003 | Name: Fareed | Dept: Finance | Salary: 85000.0 | Position: Accountant

ID: 1004 | Name: Irtaja | Dept: IT | Salary: 150000.0 | Position: Manager

Total: 4

========== MENU ==========

Enter choice: 6

===== SORTED BY SALARY (Low to High) =====

ID: 1002 | Name: Sara | Dept: HR | Salary: 65000.0 | Position: Manager

ID: 1001 | Name: Musa | Dept: IT | Salary: 75000.0 | Position: Engineer

ID: 1003 | Name: Fareed | Dept: Finance | Salary: 85000.0 | Position: Accountant

ID: 1004 | Name: Irtaja | Dept: IT | Salary: 150000.0 | Position: Manager

========== MENU ==========

Enter choice: 3
Employee ID: 1001
FOUND: ID: 1001 | Name: Musa | Dept: IT | Salary: 75000.0 | Position: Engineer

========== MENU ==========

Enter choice: 4
Salary: 85000
FOUND: ID: 1003 | Name: Fareed | Dept: Finance | Salary: 85000.0 | Position: Accountant

========== MENU ==========

Enter choice: 5
UNDO: Removed Irtaja

========== MENU ==========

Enter choice: 8

Thank you! Goodbye.

==============================================
               HOW TO RUN
==============================================

In IntelliJ IDEA:
-----------------
1. Create a new Java project
2. Create all 6 files in the src folder
3. Copy the respective code into each file
4. Run Main.java

In Command Line:
----------------
javac *.java
java Main

==============================================
          TIME COMPLEXITY ANALYSIS
==============================================

| Operation              | Data Structure | Time Complexity |
|------------------------|----------------|-----------------|
| Hire Employee          | HashMap + BST  | O(1) + O(log n) |
| Fire Employee          | HashMap + BST  | O(1) + O(log n) |
| Search By ID           | HashMap        | O(1)            |
| Search By Salary       | BST            | O(log n)        |
| Undo Last Action       | Stack          | O(1)            |
| Sorted Display         | BST In-order   | O(n)            |
| Show All Employees     | HashMap        | O(n)            |

==============================================
           SPACE COMPLEXITY ANALYSIS
==============================================

| Data Structure | Space Complexity |
|----------------|------------------|
| HashMap        | O(n)             |
| BST            | O(n)             |
| Stack          | O(n)             |

Total Space Complexity: O(n) where n = number of employees

==============================================
                    AUTHOR
==============================================
Irtaja Jara
DSA Course Project
Data Structures & Algorithms

================================================================================
