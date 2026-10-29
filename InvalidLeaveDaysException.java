// Thrown when leave days or leave balance is not between 1 and 18
public class InvalidLeaveDaysException extends Exception {
    public InvalidLeaveDaysException(String field, int value) {
        super("Invalid " + field + ": " + value + ". Must be between 1 and 18.");
    }
}