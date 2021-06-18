package pt.deliveries.deliveries_engine.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderIdDoesNotExistException extends RuntimeException{
    public OrderIdDoesNotExistException(String message) {
        super(message);
    }
}
