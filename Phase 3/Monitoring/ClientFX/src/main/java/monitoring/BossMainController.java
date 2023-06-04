package monitoring;

import Monitoring.Boss;
import Monitoring.Employee;
import Monitoring.IService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BossMainController {
    private IService service;
    private Boss loggedBoss;

    @FXML
    private TableColumn<Employee, String> checkInTimeColumn;

    @FXML
    private TableView<Employee> employeesTableView;

    @FXML
    private TableColumn<Employee, String> firstNameColumn;

    @FXML
    private TableColumn<Employee, String> lastNameColumn;

    ObservableList<Employee> employees= FXCollections.observableArrayList();
    public void setService(IService service, Boss boss){
        this.service=service;
        this.loggedBoss=boss;

        executorService.scheduleAtFixedRate(task, 0, 15, TimeUnit.SECONDS);
        initLists();

    }
    public Employee getSelectedEmployee(){
        return employeesTableView.getSelectionModel().getSelectedItem();
    }

    Runnable task=()->{
        System.out.println("Check for updates on employees");
        Platform.runLater(()->{
            initLists();
        });
    };
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private void openNewTaskWindow(Employee selectedEmployee){

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("bossTaskSendView.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            BossTaskSendController controller = fxmlLoader.getController();
            controller.setService(service,selectedEmployee,loggedBoss);

            Stage stage = (Stage) employeesTableView.getScene().getWindow();

            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void initLists(){
        employees.setAll(service.getEmployeesAtWork());
        employeesTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        employeesTableView.getSelectionModel().selectedItemProperty().addListener(observable -> {
            System.out.println("selected");
            var selectedEmployee=getSelectedEmployee();
           openNewTaskWindow(selectedEmployee);
        });


    }
    @FXML
    private void initialize(){

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        checkInTimeColumn.setCellFactory(cellFact->{
            TableCell<Employee, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Employee currentUser= getTableView().getItems().get(getIndex());
                        setText(service.getCheckinTime(currentUser.getId()));
                    }
                }
            };
            return cell;
        });
        employeesTableView.setItems(employees);
//        var stage=(Stage)employeesTableView.getScene().getWindow();
//        stage.setOnCloseRequest(event -> {
//            executorService.shutdown();
//        });

    }




}
