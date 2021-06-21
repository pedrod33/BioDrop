package pt.deliveries.deliveries_engine.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.deliveries.deliveries_engine.Exception.CourierEmailAndPasswordDoNotMatchException;
import pt.deliveries.deliveries_engine.Exception.CourierEmailOrPhoneNumberInUseException;
import pt.deliveries.deliveries_engine.Exception.SupervisorOrVehicleTypeAssociationException;
import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.Pojo.LoginCourierPojo;
import pt.deliveries.deliveries_engine.Pojo.RegisterCourierPojo;
import pt.deliveries.deliveries_engine.Repository.CourierRepository;
import pt.deliveries.deliveries_engine.Repository.SupervisorRepository;
import pt.deliveries.deliveries_engine.Repository.VehicleRepository;

import java.util.logging.Level;
import java.util.logging.Logger;


@Service
@Transactional
public class CourierServiceImpl{

    Logger logger = Logger.getLogger(CourierServiceImpl.class.getName());

    @Autowired
    private CourierRepository courierRepository;


    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private SupervisorRepository supervisorRepository;

    public Courier save(RegisterCourierPojo courierPojo){

        Courier courier = new Courier();
        courier.setEmail(courierPojo.getEmail());
        if(courierPojo.getGender()!=null){
            courier.setGender(courierPojo.getGender());
        }
        courier.setName(courierPojo.getName());
        courier.setPassword(courierPojo.getPassword());
        courier.setPhoneNumber(courierPojo.getPhoneNumber());
        courier.setVehicle(vehicleRepository.findById(courierPojo.getVehicle_id()).orElse(null));
        courier.setSupervisor(supervisorRepository.findById(courierPojo.getSupervisor_id()).orElse(null));
        return courierRepository.save(courier);
    }

    public boolean canRegister(RegisterCourierPojo courierPojo){
        this.checkIfEmailOrPhoneNumberAlreadyExistInDB(courierPojo);
        this.checkIfFKsArePresent(courierPojo.getVehicle_id(), courierPojo.getSupervisor_id());
        logger.log(Level.INFO, "gets true");
        return true;

    }

    private boolean checkIfFKsArePresent(Long vehicle_id, Long supervisor_id){
        if(vehicleRepository.findById(vehicle_id).isPresent()
        && supervisorRepository.findById(supervisor_id).isPresent()){
            return true;
        }
        logger.log(Level.INFO, "cant register fks");
        throw new SupervisorOrVehicleTypeAssociationException("Cannot associate courier with vehicle type or with supervisor!");
    }

    private boolean checkIfEmailOrPhoneNumberAlreadyExistInDB(RegisterCourierPojo courierPojo){
        if(courierRepository.findByEmail(courierPojo.getEmail())==null
        && courierRepository.findByPhoneNumber(courierPojo.getPhoneNumber())==null){
            return true;
        }
        logger.log(Level.INFO, "should throw exception");
        throw new CourierEmailOrPhoneNumberInUseException("Email or Phone Number Credentials are used in another account!");
    }


    public Courier verifyLogin(LoginCourierPojo loginCourierPojo){
        Courier courierInDB = courierRepository.findByEmail(loginCourierPojo.getEmail());
        if(courierInDB!=null && courierInDB.getPassword().equals(loginCourierPojo.getPassword())){
            return courierInDB;
        }
        throw new CourierEmailAndPasswordDoNotMatchException("Email and Password credentials do not match!");
    }
}
