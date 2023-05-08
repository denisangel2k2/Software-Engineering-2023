package monitoring;

import Monitoring.Boss;
import Monitoring.IService;

public class BossMainController {
    private IService service;
    private Boss loggedBoss;

    public void setService(IService service, Boss boss){
        this.service=service;
        this.loggedBoss=boss;
    }


}
