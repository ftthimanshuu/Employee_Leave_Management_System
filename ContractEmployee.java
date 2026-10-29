// Child class for contract employees
public class ContractEmployee extends Employee {
    public ContractEmployee(String id, String name, String dept, String email, int balance) {
        super(id, name, dept, email, balance);
    }

    @Override
    public String getEmployeeType() { return "Contract"; }
}
