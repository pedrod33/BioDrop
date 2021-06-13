package pt.deliveries.business_initiative.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.IM_USED)
public class ClientEmailOrPhoneNumberInUseException extends RuntimeException {
    public ClientEmailOrPhoneNumberInUseException(String message)
    {
        super(message);
    }
}
