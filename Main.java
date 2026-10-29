import java.util.*;

public class Main {
    static final int MAX_LEAVE_DAYS = 18;   // maximum leave per employee per year

    static ArrayList<Employee> employees = new ArrayList<>();                    // all employee records
    static HashSet<String> emails = new HashSet<>();                             // keeps emails unique
    static HashMap<String, Employee> employeeMap = new HashMap<>();              // employee ID -> employee
    static LinkedHashMap<String, LeaveRequest> requests = new LinkedHashMap<>(); // request ID -> request
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== Employee Leave Management =====");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Apply Leave");
            System.out.println("5. Approve Leave");
            System.out.println("6. Reject Leave");
            System.out.println("7. Display All Leave Requests");
            System.out.println("8. Display Employee Leave Balance");
            System.out.println("9. Exit");

            try {
                int choice = Integer.parseInt(input("Enter choice: "));
                switch (choice) {
                    case 1: addEmployee(); break;
                    case 2: displayAllEmployees(); break;
                    case 3: findEmployee(input("Enter Employee ID: ")).display(); break;
                    case 4: applyLeave(); break;
                    case 5: approveLeave(); break;
                    case 6: rejectLeave(); break;
                    case 7: displayAllRequests(); break;
                    case 8: showBalance(); break;
                    case 9: System.out.println("Thank you!"); return;
                    default: System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            } catch (EmployeeNotFoundException | InsufficientLeaveException
                     | InvalidLeaveDaysException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Prints a prompt and returns the typed text
    static String input(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    // Looks up an employee using the HashMap
    static Employee findEmployee(String id) throws EmployeeNotFoundException {
        Employee emp = employeeMap.get(id);
        if (emp == null) throw new EmployeeNotFoundException(id);
        return emp;
    }


//    
    
    // Returns a request only if it exists and is still PENDING
    static LeaveRequest findPendingRequest(String id) {
        LeaveRequest request = requests.get(id);
        if (request == null) throw new IllegalArgumentException("Leave request " + id + " not found.");
        if (request.getStatus() != LeaveRequest.Status.PENDING)
            throw new IllegalArgumentException("Request is already " + request.getStatus() + ".");
        return request;
    }

    static void addEmployee() throws InvalidLeaveDaysException {
        String id = input("Enter Employee ID: ");
        String name = input("Enter Employee Name: ");
        String dept = input("Enter Department: ");
        String email = input("Enter Email: ");
        int balance = Integer.parseInt(input("Enter Leave Balance: "));
        String type = input("Employee Type (Permanent/Contract): ");

        // Validations
        if (balance < 1 || balance > MAX_LEAVE_DAYS) throw new InvalidLeaveDaysException("leave balance", balance);
        if (employeeMap.containsKey(id)) throw new IllegalArgumentException("Employee ID already exists.");
        if (emails.contains(email)) throw new IllegalArgumentException("Email already exists.");
        
        

        Employee emp;
        if (type.equalsIgnoreCase("Permanent")) emp = new PermanentEmployee(id, name, dept, email, balance);
        else if (type.equalsIgnoreCase("Contract")) emp = new ContractEmployee(id, name, dept, email, balance);
        else throw new IllegalArgumentException("Employee type must be Permanent or Contract.");

        employees.add(emp);
        emails.add(email);
        employeeMap.put(id, emp);
        System.out.println("Employee added successfully.");
    }

    static void displayAllEmployees() {
        if (employees.isEmpty()) System.out.println("No employees found.");
        Iterator<Employee> it = employees.iterator();   // traverse using an Iterator
        while (it.hasNext()) {
            it.next().display();
            System.out.println("-------------------------");
        }
    }

    static void applyLeave() throws EmployeeNotFoundException, InvalidLeaveDaysException,
                                    InsufficientLeaveException {
        Employee emp = findEmployee(input("Enter Employee ID: "));
        LeaveRequest.LeaveType type =
                LeaveRequest.LeaveType.valueOf(input("Enter Leave Type (CASUAL/SICK/EARNED): ").toUpperCase());
        int days = Integer.parseInt(input("Enter Number of Days: "));

        if (days <= 0 || days > MAX_LEAVE_DAYS) throw new InvalidLeaveDaysException("number of days", days);
        if (days > emp.getAvailableLeaveBalance())
            throw new InsufficientLeaveException(emp.getAvailableLeaveBalance(), days);

        String reason = input("Enter Reason: ");
        LeaveRequest request = new LeaveRequest(emp.getEmployeeID(), type, days, reason);
        requests.put(request.getRequestID(), request);
        System.out.println("Leave request created successfully.");
        System.out.println("Request ID: " + request.getRequestID() + "\nStatus: " + request.getStatus());
    }

    static void approveLeave() throws EmployeeNotFoundException, InsufficientLeaveException {
        LeaveRequest request = findPendingRequest(input("Enter Request ID: "));
        Employee emp = findEmployee(request.getEmployeeID());
        int previous = emp.getAvailableLeaveBalance();
        int used = request.getNumberOfDays();

        // Balance is checked again in case other leaves were approved meanwhile
        if (used > previous) throw new InsufficientLeaveException(previous, used);

        emp.setAvailableLeaveBalance(previous - used);   // deduct approved days
        request.setStatus(LeaveRequest.Status.APPROVED);

        System.out.println("Leave Request Approved.");
        System.out.println("Employee ID      : " + emp.getEmployeeID());
        System.out.println("Previous Balance : " + previous);
        System.out.println("Leave Used       : " + used);
        System.out.println("Remaining Leave  : " + emp.getAvailableLeaveBalance());
    }

    static void rejectLeave() {
        // Balance is not touched when a request is rejected
        findPendingRequest(input("Enter Request ID: ")).setStatus(LeaveRequest.Status.REJECTED);
        System.out.println("Leave Request Rejected. Leave balance unchanged.");
    }

    static void displayAllRequests() {
        if (requests.isEmpty()) System.out.println("No leave requests found.");
        for (LeaveRequest request : requests.values()) request.display();
    }

    static void showBalance() throws EmployeeNotFoundException {
        Employee emp = findEmployee(input("Enter Employee ID: "));
        System.out.println(emp.getEmployeeName() + " has " + emp.getAvailableLeaveBalance() + " leave day(s) left.");
    }
}