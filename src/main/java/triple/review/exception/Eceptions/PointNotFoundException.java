package triple.review.exception.Eceptions;

public class PointNotFoundException extends RuntimeException{
    public PointNotFoundException() {
        super();
    }

    public PointNotFoundException(String message) {
        super(message);
    }

    public PointNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PointNotFoundException(Throwable cause) {
        super(cause);
    }
}
