package pt.deliveries.business_initiative.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ClientEmailOrPasswordIncorrectException extends RuntimeException {
    public ClientEmailOrPasswordIncorrectException(String message)
    {
        super(message);
    }
}
