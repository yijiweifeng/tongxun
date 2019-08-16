package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.client.cache.StrCache;
import sample.fxmlinit.FxmlInitCtroller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/15
 * Time: 10:21
 * Description: No Description
 */
public class ErrorController implements Initializable {

    private FxmlInitCtroller fxmlInitCtroller = FxmlInitCtroller.getInstance();

    private StrCache strCache = StrCache.getInstance();

    @FXML
    private Text errorText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(strCache.getErrorMsg() == null || strCache.getErrorMsg().isEmpty()){
            errorText.setText("账号或密码不能为空!");
        }else{
            errorText.setText(strCache.getErrorMsg());
        }
    }

    public void close() throws IOException {
        Stage stage = fxmlInitCtroller.getStage();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        stage.setTitle("login");
        Scene scene = new Scene(root, 300, 300);
        stage.setScene(scene);
        stage.show();
    }

}
