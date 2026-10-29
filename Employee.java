// Parent class holding the details common to every employee
public abstract class Employee {
	private String employeeID, employeeName, department, email;
	private int availableLeaveBalance;

	public Employee(String employeeID, String employeeName, String department, String email,
			int availableLeaveBalance) {
		this.employeeID = employeeID;
		this.employeeName = employeeName;
		this.department = department;
		this.email = email;
		this.availableLeaveBalance = availableLeaveBalance;
	}

	// Getters
	public String getEmployeeID() {
		return employeeID;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public String getDepartment() {
		return department;
	}

	public String getEmail() {
		return email;
	}

	public int getAvailableLeaveBalance() {
		return availableLeaveBalance;
	}

	// Setters
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAvailableLeaveBalance(int balance) {
		this.availableLeaveBalance = balance;
	}

	// Each child class tells its own type
	public abstract String getEmployeeType();

	public void display() {
		System.out.println("Employee ID   : " + employeeID);
		System.out.println("Name          : " + employeeName);
		System.out.println("Department   : " + department);
		System.out.println("Email         : " + email);
		System.out.println("Type          : " + getEmployeeType());
		System.out.println("Leave Balance : " + availableLeaveBalance);
	}
}
