package pt.deliveries.deliveries_engine.Pojo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class CreateRatingPojo implements Serializable {

    @NotBlank
    private int score;

    private String description;

    @NotBlank
    private long order_id;
    public CreateRatingPojo(int score, String description, long order_id){
        this.score = score;
        this.description = description;
        this.order_id = order_id;
    }

    public int getScore() {
        return score;
    }

    public String getDescription() {
        return description;
    }

    public long getOrder_id() {
        return order_id;
    }
}
