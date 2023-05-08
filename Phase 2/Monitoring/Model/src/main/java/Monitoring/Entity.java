package Monitoring;

import javax.persistence.MappedSuperclass;


public interface Entity<ID> {
    void setId(ID id);
    ID getId();
}
