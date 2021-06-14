package pt.deliveries.deliveries_engine.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class CourierServiceImpl implements CourierService{

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
        courier.setGender(courierPojo.getGender());
        courier.setName(courierPojo.getName());
        courier.setPassword(courierPojo.getPassword());
        courier.setPhoneNumber(courierPojo.getPhoneNumber());
        courier.setVehicle(vehicleRepository.findById(courierPojo.getVehicle_id()).orElse(null));
        courier.setSupervisor(supervisorRepository.findById(courierPojo.getSupervisor_id()).orElse(null));
        courierRepository.save(courier);
        return courierRepository.save(courier);
    }

    @Override
    public boolean exists(RegisterCourierPojo courier) {
        return false;
    }

    public boolean canRegister(RegisterCourierPojo courierPojo){
        if(this.checkIfEmailOrPhoneNumberAlreadyExistInDB(courierPojo) && this.checkIfFKsArePresent(courierPojo.getVehicle_id(), courierPojo.getSupervisor_id())){
            logger.log(Level.INFO, "gets true");
            return true;
        }
        logger.log(Level.INFO, "gets false");

        return false;
    }

    private boolean checkIfFKsArePresent(Long vehicle_id, Long supervisor_id){
        logger.log(Level.INFO, String.valueOf(!vehicleRepository.findById(vehicle_id).isPresent() && !supervisorRepository.findById(supervisor_id).isPresent()));
        return vehicleRepository.findById(vehicle_id).isPresent() && supervisorRepository.findById(supervisor_id).isPresent();
    }

    private boolean checkIfEmailOrPhoneNumberAlreadyExistInDB(RegisterCourierPojo courierPojo){
        logger.log(Level.INFO, String.valueOf(courierRepository.findByEmail(courierPojo.getEmail())!=null && courierRepository.findByPhoneNumber(courierPojo.getPhoneNumber())!=null));
        return (courierRepository.findByEmail(courierPojo.getEmail())!=null && courierRepository.findByPhoneNumber(courierPojo.getPhoneNumber())!=null);

    }
    public boolean emailExists(LoginCourierPojo loginCourierPojo){
        if(courierRepository.findByEmail(loginCourierPojo.getEmail())!=null){
            return true;
        }
        return false;
    }


    public Courier verifyLogin(LoginCourierPojo loginCourierPojo){
        Courier courierInDB = courierRepository.findByEmail(loginCourierPojo.getEmail());
        if(courierInDB!=null && courierInDB.getPassword().equals(loginCourierPojo.getPassword())){
            return courierInDB;
        }
        return null;
    }
}
