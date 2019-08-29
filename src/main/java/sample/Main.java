package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.fxmlinit.FxmlInitCtroller;

public class Main extends Application {

    private FxmlInitCtroller fxmlInitCtroller = FxmlInitCtroller.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        primaryStage.setTitle("登录");
        Scene scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("msg.png")));
        primaryStage.show();
        fxmlInitCtroller.setStage(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
