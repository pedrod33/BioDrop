package pt.deliveries.business_initiative.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressSaveForClientPOJO {

    private String city;

    private String completeAddress;

    private double latitude;

    private double longitude;

}
