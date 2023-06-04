package monitoring;

import Monitoring.Boss;
import Monitoring.Employee;
import Monitoring.IService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class BossTaskSendController {

    @FXML
    private DatePicker deadlineDatePicker;

    @FXML
    private Label employeeLabel;

    @FXML
    private Button sendTaskButton;

    @FXML
    private TextArea taskDesccriptionTextArea;

    private IService service;
    private Employee selectedEmployee;
    private Boss loggedBoss;

    public void setService(IService service, Employee employee, Boss boss) {
        this.service = service;
        this.selectedEmployee = employee;
        this.loggedBoss = boss;
        employeeLabel.setText("Send task to" + selectedEmployee.getPrenume() + " " + selectedEmployee.getName());

    }

    @FXML
    public void initialize() {

    }

    @FXML
    public void onSendTaskAction() {
        String description = taskDesccriptionTextArea.getText();
        var deadline = deadlineDatePicker.getValue();

        if (description.length() < 10 || deadline==null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Description too short!");
            alert.showAndWait();

        } else {
            service.addTask(deadline.toString(), description, loggedBoss, selectedEmployee);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("bossMainView.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), 320, 240);
                BossMainController controller = fxmlLoader.getController();
                controller.setService(service, loggedBoss);

                Stage stage = (Stage) employeeLabel.getScene().getWindow();
                stage.setTitle("Hello!");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
