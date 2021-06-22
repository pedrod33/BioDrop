package pt.deliveries.deliveries_engine.Pojo;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;


public class RegisterCourierPojo implements Serializable {

    private Long vehicle_id;

    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    private String gender;

    private Long phoneNumber;


    public RegisterCourierPojo(){}

    public RegisterCourierPojo(String name, String email, String password, String gender, Long phoneNumber, Long vehicle_id) {
        this.vehicle_id = vehicle_id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(Long vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
