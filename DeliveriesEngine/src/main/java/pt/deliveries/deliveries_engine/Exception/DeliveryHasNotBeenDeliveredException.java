package pt.deliveries.deliveries_engine.Exception;


public class DeliveryHasNotBeenDeliveredException extends RuntimeException {
    public DeliveryHasNotBeenDeliveredException(String message) {
        super(message);
    }
}
