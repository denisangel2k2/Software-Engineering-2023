package Monitoring;

import Monitoring.Implementations.BossRepository;
import Monitoring.Implementations.EmployeeRepository;
import Monitoring.Implementations.PresenceRepository;
import Monitoring.Interfaces.IBossRepository;
import Monitoring.Interfaces.IEmployeeRepository;
import Monitoring.Interfaces.IPresenceRepository;
import Monitoring.Interfaces.ITaskRepository;

public class ServiceImpl implements IService{
    private IPresenceRepository presenceRepository;
    private ITaskRepository taskRepository;
    private IEmployeeRepository employeeRepository;
    private IBossRepository bossRepository;


//    public ServiceImpl(IPresenceRepository presenceRepository,ITaskRepository taskRepository, IEmployeeRepository employeeRepository, IBossRepository bossRepository) {
//        this.presenceRepository = presenceRepository;
//        this.taskRepository = taskRepository;
//        this.employeeRepository = employeeRepository;
//        this.bossRepository = bossRepository;
//    }
    public ServiceImpl(){

    }


    @Override
    public Employee loginEmployee(String username, String password) {
        this.employeeRepository=new EmployeeRepository();
        Employee employee1 = employeeRepository.findByUsernameAndPassword(username,password);
        if (employee1!=null)
            return employee1;
        return null;
    }

    @Override
    public Boss loginBoss(String username, String password){
        this.bossRepository=new BossRepository();
        Boss boss1 = bossRepository.findByUsernameAndPassword(username,password);
        if (boss1!=null)
            return boss1;
        return null;
    }

    @Override
    public void checkIn(Integer employeeId, String time) {
        this.presenceRepository=new PresenceRepository();
        Employee employee=employeeRepository.findById(employeeId);
        Presence presence = new Presence(time,employee);
        presenceRepository.add(presence);
        //employee.checkIn(presence,time);

    }


}
