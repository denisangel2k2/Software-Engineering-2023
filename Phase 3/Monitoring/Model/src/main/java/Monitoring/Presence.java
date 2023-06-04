package Monitoring;

import Monitoring.Enums.PresenceStatus;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Table(name = "presences")
@Transactional
public class Presence implements Monitoring.Entity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "presence_id")
    private Integer id;

    @Column(name = "check_in_time")
    private String check_in_time;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private PresenceStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Presence(String check_in_time, Employee employee) {
        this.id = null;
        this.check_in_time = check_in_time;
        this.status = PresenceStatus.AT_WORK;
        this.employee = employee;
    }

    public Presence(){

    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCheck_in_time() {
        return check_in_time;
    }

    public void setCheck_in_time(String check_in_time) {
        this.check_in_time = check_in_time;
    }

    public PresenceStatus getStatus() {
        return status;
    }

    public void setStatus(PresenceStatus status) {
        this.status = status;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}

