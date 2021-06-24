package pt.deliveries.deliveries_engine.Pojo;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class LoginCourierPojo implements Serializable {

    @NotBlank @Email
    private String email;

    @NotBlank
    private String password;

    public LoginCourierPojo() {};

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
