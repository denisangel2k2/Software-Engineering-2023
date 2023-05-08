package Monitoring.Interfaces;

import Monitoring.Boss;

public interface IBossRepository extends IRepository<Boss>{
    Boss findByUsernameAndPassword(String username, String password);
}
