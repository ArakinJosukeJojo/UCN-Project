package databaselayer;

public class IllegalCustomerException extends Exception {
    public IllegalCustomerException(String message) {
        super(message);
    }

    public IllegalCustomerException(String message, Throwable cause) {
        super(message, cause);
    }
}

