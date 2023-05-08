package monitoring;

import Monitoring.IService;
import Monitoring.Implementations.BossRepository;
import Monitoring.Implementations.EmployeeRepository;
import Monitoring.Implementations.PresenceRepository;
import Monitoring.Implementations.TaskRepository;
import Monitoring.Interfaces.IBossRepository;
import Monitoring.Interfaces.IEmployeeRepository;
import Monitoring.Interfaces.IPresenceRepository;
import Monitoring.Interfaces.ITaskRepository;
import Monitoring.ServiceImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    IEmployeeRepository employeeRepository=new EmployeeRepository();
    ITaskRepository taskRepository=new TaskRepository();
    IBossRepository bossRepository=new BossRepository();
    IPresenceRepository presenceRepository=new PresenceRepository();

    IService service=new ServiceImpl(presenceRepository,taskRepository,employeeRepository,bossRepository);

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        LoginController loginController = fxmlLoader.getController();
        loginController.setService(service);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}