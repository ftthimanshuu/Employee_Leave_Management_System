// Thrown when requested leave is more than the available balance
public class InsufficientLeaveException extends Exception {
    public InsufficientLeaveException(int available, int requested) {
        super("Insufficient leave. Available: " + available + ", Requested: " + requested);
    }
}
