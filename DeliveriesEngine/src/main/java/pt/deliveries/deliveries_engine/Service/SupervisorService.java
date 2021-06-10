package pt.deliveries.deliveries_engine.Service;

import pt.deliveries.deliveries_engine.Model.Supervisor;

public interface SupervisorService {

    Supervisor create(Supervisor supervisor);

    boolean exists(Supervisor supervisor);

    boolean credentialValidity(Supervisor supervisor);
}
