package pt.deliveries.deliveries_engine.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class SupervisorMailAndPasswordDoesNotMatchException extends RuntimeException {
    public SupervisorMailAndPasswordDoesNotMatchException(String message) {
        super(message);
    }
}
