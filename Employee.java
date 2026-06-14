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
        return "ID: " + id + " | Name: " + name + " | Dept: " + department + " | Salary: " + salary + " | Position: " + position;
    }
}