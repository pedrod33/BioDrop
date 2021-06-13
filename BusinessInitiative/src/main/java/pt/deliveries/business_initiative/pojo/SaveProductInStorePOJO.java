package pt.deliveries.business_initiative.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveProductInStorePOJO {

    private String name;

    private String origin;

    private double price;

    private String imgPath;

    private double weight;
}
