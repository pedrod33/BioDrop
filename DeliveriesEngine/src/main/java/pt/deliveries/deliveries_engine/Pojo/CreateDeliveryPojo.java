package pt.deliveries.deliveries_engine.Pojo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;


public class CreateDeliveryPojo implements Serializable {

    @NotBlank
    @Min(value=-90)
    @Max(value=90)
    private double latStore;

    @NotBlank
    @Min(value=-180)
    @Max(value=180)
    private double longStore;

    @NotBlank
    @Min(value=-90)
    @Max(value=90)
    private double latClient;

    @NotBlank
    @Min(value=-180)
    @Max(value=180)
    private double longClient;

    public CreateDeliveryPojo(){}

    public CreateDeliveryPojo(double latStore, double longStore, double latClient, double longClient) {
        this.latStore = latStore;
        this.longStore = longStore;
        this.latClient = latClient;
        this.longClient = longClient;
    }

    public double getLatStore() {
        return latStore;
    }

    public double getLongStore() {
        return longStore;
    }

    public double getLatClient() {
        return latClient;
    }

    public double getLongClient() {
        return longClient;
    }
}
