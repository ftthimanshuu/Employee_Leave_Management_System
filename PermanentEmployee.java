// Child class for permanent employees
public class PermanentEmployee extends Employee {
    public PermanentEmployee(String id, String name, String dept, String email, int balance) {
        super(id, name, dept, email, balance);
    }

    @Override
    public String getEmployeeType() { return "Permanent"; }
}
