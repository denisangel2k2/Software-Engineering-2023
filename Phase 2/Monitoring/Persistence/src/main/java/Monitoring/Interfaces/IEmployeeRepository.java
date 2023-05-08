package Monitoring.Interfaces;

import Monitoring.Boss;
import Monitoring.Employee;

public interface IEmployeeRepository extends IRepository<Employee>{
    Employee findByUsernameAndPassword(String username_find, String password_find);
}
