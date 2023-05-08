package Monitoring;

import Monitoring.Enums.TaskStatusCategory;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee implements Monitoring.Entity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "prenume")
    private String prenume;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private List<Task> tasks;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private List<Presence> presences;


    public List<Presence> getPresences() {
        return presences;
    }

    public Employee(String name, String prenume, String username, String password) {
        this.id = null;
        this.name = name;
        this.prenume = prenume;
        this.username = username;
        this.password = password;
        this.tasks = new ArrayList<>();
    }

    public void addPresence(Presence presence){
        this.presences.add(presence);
    }

    public Employee(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }


    public void checkIn(Presence presence, String time){
        presence.setCheck_in_time(time);
        this.presences.add(presence);
    }
    public void finishTask(Integer taskId){
        for(Task task : this.tasks){
            if(task.getId().equals(taskId)){
                task.setStatus(TaskStatusCategory.FINISHED);
            }
        }
    }

}
