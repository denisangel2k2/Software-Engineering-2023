package Monitoring;

import Monitoring.Enums.PresenceStatus;
import Monitoring.Enums.TaskStatusCategory;
import Monitoring.Implementations.BossRepository;
import Monitoring.Implementations.EmployeeRepository;
import Monitoring.Implementations.PresenceRepository;
import Monitoring.Implementations.TaskRepository;
import Monitoring.Interfaces.IBossRepository;
import Monitoring.Interfaces.IEmployeeRepository;
import Monitoring.Interfaces.IPresenceRepository;
import Monitoring.Interfaces.ITaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServiceImpl implements IService {
    private IPresenceRepository presenceRepository=null;
    private ITaskRepository taskRepository=null;
    private IEmployeeRepository employeeRepository=null;
    private IBossRepository bossRepository=null;


    //    public ServiceImpl(IPresenceRepository presenceRepository,ITaskRepository taskRepository, IEmployeeRepository employeeRepository, IBossRepository bossRepository) {
//        this.presenceRepository = presenceRepository;
//        this.taskRepository = taskRepository;
//        this.employeeRepository = employeeRepository;
//        this.bossRepository = bossRepository;
//    }
    public ServiceImpl() {

    }


    private Employee loginEmployee(String username, String password) {
        if (this.employeeRepository == null)
            this.employeeRepository = new EmployeeRepository();
        Employee employee1 = employeeRepository.findByUsernameAndPassword(username, password);
        if (employee1 != null)
            return employee1;
        return null;
    }


    private Boss loginBoss(String username, String password) {
        if (this.bossRepository == null)
            this.bossRepository = new BossRepository();
        Boss boss1 = bossRepository.findByUsernameAndPassword(username, password);
        if (boss1 != null)
            return boss1;
        return null;
    }

    @Override
    public Entity<Integer> login(String username, String password) {
        Boss boss = loginBoss(username, password);
        Employee employee = loginEmployee(username, password);

        return (boss != null) ? boss : employee;
    }

    @Override
    public void checkIn(Integer employeeId, String time) {
        if (this.presenceRepository == null)
            this.presenceRepository = new PresenceRepository();
        Employee employee = employeeRepository.findById(employeeId);
        Presence presence = new Presence(time, employee);
        presenceRepository.add(presence);
        //employee.checkIn(presence,time);
    }

    @Override
    public Iterable<Task> getTasks(Integer employeeId) {
        if (this.taskRepository == null)
            this.taskRepository = new TaskRepository();
        var tasks = ((List<Task>) taskRepository.getAll()).stream()
                .filter(task -> task.getEmployee().getId() == employeeId && task.getStatus().equals(TaskStatusCategory.ONGOING))
                .toList();
        return tasks;
    }

    @Override
    public void checkOut(Integer employeeId) {
        if (this.presenceRepository == null)
            this.presenceRepository = new PresenceRepository();
        var presences = ((List<Presence>) presenceRepository.getAll());
        Optional<Presence> presence = presences.stream()
                .filter(p -> p.getEmployee().getId() == employeeId && p.getStatus().equals(PresenceStatus.AT_WORK))
                .findAny();
        if (presence.isEmpty())
            return;
        Presence presence1=presence.get();
        presence1.setStatus(PresenceStatus.CHECKED_OUT);
        presenceRepository.update(presence1);
    }

    @Override
    public void finishTask(Integer taskId) {
        if (this.taskRepository == null)
            this.taskRepository = new TaskRepository();
        Task task = taskRepository.findById(taskId);
        task.setStatus(TaskStatusCategory.FINISHED);
        taskRepository.update(task);
    }

    @Override
    public List<Employee> getEmployeesAtWork() {
        if (this.employeeRepository == null)
            this.employeeRepository = new EmployeeRepository();
        if (this.presenceRepository == null)
            this.presenceRepository = new PresenceRepository();

        var presences= ((List<Presence>) presenceRepository.getAll()).stream().filter(p->p.getStatus().equals(PresenceStatus.AT_WORK)).toList();
        List<Employee> employees=new ArrayList<>();
        for (Presence presence:presences) {
            var employee=employeeRepository.findById(presence.getEmployee().getId());
            employees.add(employee);
        }
        return employees;
    }
    @Override
    public String getCheckinTime(Integer employeeId){
        if (this.presenceRepository == null)
            this.presenceRepository = new PresenceRepository();
        var presences= ((List<Presence>) presenceRepository.getAll()).stream().filter(p->p.getEmployee().getId()==employeeId && p.getStatus().equals(PresenceStatus.AT_WORK)).findFirst();
        if(presences.isEmpty())
            return null;
        return presences.get().getCheck_in_time();
    }

    @Override
    public void addTask(String deadline, String description, Boss boss, Employee employee) {
        if (this.taskRepository == null)
            this.taskRepository = new TaskRepository();
        Task task = new Task(description, deadline);
        task.setBoss(boss);
        task.setEmployee(employee);

        taskRepository.add(task);
    }

    @Override
    public List<Employee> getEmployeesByName(String name) {
        if (this.employeeRepository == null)
            this.employeeRepository = new EmployeeRepository();
        return getEmployeesAtWork().stream()
                .filter(employee -> employee.getName().contains(name.toLowerCase())).collect(Collectors.toList());
    }
}
