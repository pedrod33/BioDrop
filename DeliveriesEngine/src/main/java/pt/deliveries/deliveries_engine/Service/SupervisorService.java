package pt.deliveries.deliveries_engine.Service;

import pt.deliveries.deliveries_engine.Model.Supervisor;
import pt.deliveries.deliveries_engine.Pojo.RegisterSupervisorPojo;

public interface SupervisorService {

    Supervisor create(RegisterSupervisorPojo supervisor);

    boolean existsRegister(RegisterSupervisorPojo supervisor);

    boolean credentialValidity(RegisterSupervisorPojo supervisor);
}
