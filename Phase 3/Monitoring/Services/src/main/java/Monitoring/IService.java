package Monitoring;


import Monitoring.Enums.TaskStatusCategory;

import java.util.List;

public interface IService {
    Entity<Integer> login(String username, String password);
    void checkIn(Integer employeeId, String time);
    Iterable<Task> getTasks(Integer employeeId);
    void finishTask(Integer taskId);
    void checkOut(Integer employeeId);
    List<Employee> getEmployeesAtWork();
    List<Employee> getEmployeesByName(String name);
    String getCheckinTime(Integer employeeId);
    void addTask(String deadline, String description, Boss boss, Employee employee);
}
