package dasturlash.z.spring_security_multi_user.exception;

public class AppBadRequestException extends RuntimeException{
    public AppBadRequestException(String message) {
        super(message);
    }
}
