// Thrown when no employee exists for the given employee ID
public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException(String employeeID) {
        super("Employee with ID " + employeeID + " not found.");
    }
}
