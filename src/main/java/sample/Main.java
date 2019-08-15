package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.fxmlinit.FxmlInitCtroller;

public class Main extends Application {

    private FxmlInitCtroller fxmlInitCtroller = FxmlInitCtroller.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        primaryStage.setTitle("login");
        Scene scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
        fxmlInitCtroller.setStage(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
