package pt.deliveries.deliveries_engine.Pojo;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class LoginCourierPojo implements Serializable {

    @NotBlank @Email
    private final String email;

    @NotBlank
    private final String password;

    public LoginCourierPojo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
