package pt.deliveries.business_initiative.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRegistrationPOJO {

    private String name;

    private String email;

    private String password;

    private String gender;

    private String phoneNumber;
}
