package Monitoring;


public interface IService {
    Employee loginEmployee(String username, String password);
    Boss loginBoss(String username, String password);
    void checkIn(Integer employeeId, String time);
}
