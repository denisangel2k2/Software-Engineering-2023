package monitoring;

import Monitoring.Employee;
import Monitoring.IService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EmployeeController {
    @FXML
    private Label loggedLabel;

    private IService service;

    private Employee logged_employee;
    public void setService(IService service, Employee employee){
        this.service=service;
        this.logged_employee=employee;
        loggedLabel.setText("Logged in as "+logged_employee.getName()+" "+logged_employee.getPrenume());
    }



}
