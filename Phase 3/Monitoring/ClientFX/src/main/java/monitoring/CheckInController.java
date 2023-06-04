package monitoring;

import Monitoring.Employee;
import Monitoring.IService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public class CheckInController {
    @FXML
    private Button checkinButton;

    @FXML
    private Spinner<Integer> hourSpinner;

    @FXML
    private Spinner<Integer> minuteSpinner;


    private IService service;
    private Employee logged_employee;

    public void setService(IService service,Employee employee){
        this.service=service;
        this.logged_employee=employee;
        hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23));
        minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59));
    }

    public void onCheckInAction(){
        Integer hour=hourSpinner.getValue();
        Integer minute=minuteSpinner.getValue();
        //get current date with pattern dd/MM/yyyy
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime date=LocalDateTime.now();
        String date_string=dtf.format(date);
        String time=date_string+","+hour+":"+minute;
        service.checkIn(logged_employee.getId(),time);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("employeeMainView.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            EmployeeController controller = fxmlLoader.getController();
            controller.setService(service,logged_employee);
            Stage currentStage = (Stage) minuteSpinner.getScene().getWindow();
            currentStage.setScene(scene);
        }
        catch (Exception e){
            System.out.println("Error: "+e);
        }

    }

}
