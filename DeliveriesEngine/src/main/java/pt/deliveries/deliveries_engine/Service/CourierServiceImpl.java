package pt.deliveries.deliveries_engine.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.Repository.CourierRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
public class CourierServiceImpl implements CourierService{

    @Autowired
    private CourierRepository courierRepository;

    private Logger logger = Logger.getLogger(CourierServiceImpl.class.getName());

    public Courier save(Courier courier){
        return courierRepository.save(courier);
    }

    public boolean exists(Courier courier){
        if(courierRepository.findByEmail(courier.getEmail())!=null){
            return true;
        }
        else if(courierRepository.findByPhoneNumber(courier.getPhoneNumber())!=null){
            return true;
        }
        return false;
    }

    public Courier verifyLogin(Courier courier){
        Courier courierInDB = courierRepository.findByEmail(courier.getEmail());
        if(courierInDB!=null && courierInDB.getPassword().equals(courier.getPassword())){
            return courier;
        }
        return null;
    }
}
