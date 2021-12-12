package src.sample;
import src.sample.classes.GUI.ThreadStart;
import src.sample.classes.GUI.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    public static Thread emuThread;
    public static Controller controller;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxmodule.fxml"));
        Parent root = loader.load();
        controller =  loader.getController();
        emuThread = new Thread(new ThreadStart());

        stage.setTitle("CourseWork");
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if(emuThread.isAlive())
            emuThread.stop();
    }
}
