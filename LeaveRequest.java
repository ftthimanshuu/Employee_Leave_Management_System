public class LeaveRequest {
    public enum LeaveType { CASUAL, SICK, EARNED }
    public enum Status { PENDING, APPROVED, REJECTED }

    private static int counter = 1001;   // used to generate LR1001, LR1002, ...

    private String requestID, employeeID, reason;
    private LeaveType leaveType;
    private int numberOfDays;
    private Status status;

    public LeaveRequest(String employeeID, LeaveType leaveType, int numberOfDays, String reason) {
        this.requestID = "LR" + counter++;
        this.employeeID = employeeID;
        this.leaveType = leaveType;
        this.numberOfDays = numberOfDays;
        this.reason = reason;
        this.status = Status.PENDING;    // every new request starts as PENDING
    }

    public String getRequestID() { return requestID; }
    public String getEmployeeID() { return employeeID; }
    public int getNumberOfDays() { return numberOfDays; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public void display() {
        System.out.println(requestID + " | " + employeeID + " | " + leaveType
                + " | " + numberOfDays + " day(s) | " + status);
    }
}
