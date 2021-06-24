package pt.deliveries.business_initiative.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientLoginPOJO {

    private String email;

    private String password;

}
