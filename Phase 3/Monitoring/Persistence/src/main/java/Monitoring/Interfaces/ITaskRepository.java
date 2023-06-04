package Monitoring.Interfaces;

import Monitoring.Task;

public interface ITaskRepository extends IRepository<Task>{
    void update(Task task);
}
