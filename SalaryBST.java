import java.util.ArrayList;

public class SalaryBST {
    BSTNode root;

    void insert(Employee emp) {
        root = insertRec(root, emp);
    }


    BSTNode insertRec(BSTNode root, Employee emp) {
        if (root == null) {
            return new BSTNode(emp);}
        if (emp.salary < root.emp.salary){
            root.left = insertRec(root.left, emp);
        }
        else if (emp.salary > root.emp.salary) {
            root.right = insertRec(root.right, emp);
        }
        return root;
    }

    Employee search(double salary) {
        return searchRec(root, salary);
    }

    Employee searchRec(BSTNode root, double salary) {
        if (root == null){
            return null;
        }
        if (salary == root.emp.salary) {
            return root.emp;
        }
        if (salary < root.emp.salary) {
            return searchRec(root.left, salary);
        }
        else return searchRec(root.right, salary);
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
        }
        else if (salary > root.emp.salary){
            root.right = deleteRec(root.right, salary);
        }
        else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            BSTNode temp = root.right;
            while (temp.left != null){
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