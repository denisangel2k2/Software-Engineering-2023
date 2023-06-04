package monitoring;

import Monitoring.Employee;
import Monitoring.IService;
import Monitoring.Task;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EmployeeController {
    @FXML
    private Label loggedLabel;

    private IService service;


    @FXML
    private Button finishTaskButton;
    @FXML
    private Button logoutButton;


    @FXML
    private TableColumn<Task, String> taskDeadlineColumn;

    @FXML
    private TextArea taskDescriptionTextArea;

    @FXML
    private TableColumn<Task, String> taskNumberColumn;

    @FXML
    private TableView<Task> tasksTableView;

    ObservableList<Task> tasksList= FXCollections.observableArrayList();
    private Employee logged_employee;
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private Integer oldTaskNumber=0;
    public void setService(IService service, Employee employee){
        this.service=service;
        this.logged_employee=employee;
        loggedLabel.setText("Logged in as "+logged_employee.getName()+" "+logged_employee.getPrenume());
        initLists();

    }

    @FXML
    public void initialize(){
        taskDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        taskNumberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tasksTableView.setItems(tasksList);
        executorService.scheduleAtFixedRate(task, 0, 15, TimeUnit.SECONDS);
        tasksTableView.getSelectionModel().selectedItemProperty().addListener(e->{
            taskDescriptionTextArea.setText(tasksTableView.getSelectionModel().getSelectedItem().getDescription());
        });
    }
    Runnable task=()->{
        System.out.println("Check for new tasks");
        Platform.runLater(()->{
            initLists();
            if (oldTaskNumber<tasksList.size()){
                oldTaskNumber=tasksList.size();
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("New taskS");
                alert.setContentText("You have new tasks");
                alert.show();

            }
        });
    };
    private void initLists(){
        var tasks=service.getTasks(logged_employee.getId());
        tasksList.setAll((List<Task>)tasks);

    }


    @FXML
    public void onLogoutAction()  {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loginView.fxml"));
        try {
            service.checkOut(logged_employee.getId());
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            LoginController loginController = fxmlLoader.getController();
            loginController.setService(service);

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
            executorService.shutdown();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void onFinishTaskAction(){
        Task task=tasksTableView.getSelectionModel().getSelectedItem();
        if (task==null){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No task selected");
            alert.showAndWait();
            return;
        }
        service.finishTask(task.getId());
        taskDescriptionTextArea.setText("");
        initLists();
    }


}
