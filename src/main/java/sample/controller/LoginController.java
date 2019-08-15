package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.fxmlinit.FxmlInitCtroller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/15
 * Time: 9:52
 * Description: No Description
 */
public class LoginController implements Initializable {

    private FxmlInitCtroller fxmlInitCtroller = FxmlInitCtroller.getInstance();

    @FXML
    private TextField account;

    @FXML
    private TextField password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void login() throws IOException {
        if (account.getText() == null || account.getText().isEmpty()
                || password.getText() == null || password.getText().isEmpty()) {
            Stage stage = fxmlInitCtroller.getStage();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("error.fxml"));
            stage.setTitle("error");
            Scene scene = new Scene(root, 300, 150);
            stage.setScene(scene);
            stage.show();
        } else {
            Stage stage = fxmlInitCtroller.getStage();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("window.fxml"));
            stage.setTitle("window");
            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void register() throws IOException {
        if (account.getText() == null || account.getText().isEmpty()
                || password.getText() == null || password.getText().isEmpty()) {
            Stage stage = fxmlInitCtroller.getStage();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("error.fxml"));
            stage.setTitle("error");
            Scene scene = new Scene(root, 300, 150);
            stage.setScene(scene);
            stage.show();
        } else {
            Stage stage = fxmlInitCtroller.getStage();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("window.fxml"));
            stage.setTitle("window");
            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            stage.show();
        }
    }

}
