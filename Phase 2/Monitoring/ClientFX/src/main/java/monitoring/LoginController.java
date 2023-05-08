package monitoring;

import Monitoring.Boss;
import Monitoring.Employee;
import Monitoring.IService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class LoginController {
    @FXML
    private Button loginButton;

    @FXML
    private TextField passwordTextField;

    @FXML
    private ComboBox<String> typeCombobox;

    @FXML
    private TextField usernameTextField;

    private IService service;
    public void setService(IService service){
        this.service=service;
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("EMPLOYEE");
        list.add("BOSS");
        typeCombobox.setItems(list);
    }
    @FXML
    private void initialize() {



    }

    @FXML
    public void onLoginAction(){
        String userName=usernameTextField.getText();
        String password=passwordTextField.getText();
        String type=typeCombobox.getValue();

        if (type.equals("EMPLOYEE")){
            Employee employee=service.loginEmployee(userName,password);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("checkInView.fxml"));

            try{
                Scene scene = new Scene(fxmlLoader.load(), 320, 240);
                CheckInController controller = fxmlLoader.getController();
                controller.setService(service,employee);
                Stage currentStage = (Stage) loginButton.getScene().getWindow();
                currentStage.setScene(scene);
            }
            catch (Exception e){
                System.out.println("Error: "+e);
            }
        }
        else{
            Boss boss=service.loginBoss(userName,password);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("bossMainView.fxml"));
            try{
                Scene scene = new Scene(fxmlLoader.load(), 320, 240);
                BossMainController controller = fxmlLoader.getController();
                controller.setService(service,boss);
                Stage currentStage = (Stage) loginButton.getScene().getWindow();
                currentStage.setScene(scene);
            }
            catch (Exception e){
                System.out.println("Error: "+e);
            }
        }

    }


}
