package Monitoring;

import Monitoring.Enums.TaskStatusCategory;

import javax.persistence.*;
import javax.persistence.Entity;
@Entity
@Table(name = "tasks")
public class Task implements Monitoring.Entity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer id;

    @Column(name="description")
    private String description;

    @Column(name="deadline")
    private String deadline;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private TaskStatusCategory status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boss_id")
    private Boss boss;

    public Task(String description, String deadline) {
        this.id = null;
        this.description = description;
        this.deadline = deadline;
        this.status = TaskStatusCategory.ONGOING;
        this.employee = null;
        this.boss = null;
    }
    public Task(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public TaskStatusCategory getStatus() {
        return status;
    }

    public void setStatus(TaskStatusCategory status) {
        this.status = status;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Boss getBoss() {
        return boss;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }
}
