package sample.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.client.cache.StrCache;
import sample.client.cache.UserInfoCache;
import sample.client.utils.*;
import sample.fxmlinit.FxmlInitCtroller;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/15
 * Time: 9:52
 * Description: No Description
 */
public class LoginController implements Initializable {

    private FxmlInitCtroller fxmlInitCtroller = FxmlInitCtroller.getInstance();

    private StrCache strCache = StrCache.getInstance();

    private UserInfoCache userInfoCache = UserInfoCache.getInstance();

    @FXML
    private JFXTextField account;

    @FXML
    private JFXPasswordField password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void login() throws IOException {
        if (account.getText() == null || account.getText().isEmpty()
                || password.getText() == null || password.getText().isEmpty()) {
            showErrorModel();
        } else {
            String accountText = account.getText();
            String passwordText = password.getText();
            String params = "tel=" + accountText + "&password=" + passwordText;
            String post = HttpUtil.sendPost(ApiUrlManager.login() + "?" + params, "");
            JSONObject jsonObject = JSONObject.parseObject(post);
            if(jsonObject.getString("result") != null && jsonObject.getString("result").equals("200")){
                Map<String,Object> data = (Map<String,Object>)jsonObject.get("data");
                Long id = Long.valueOf(data.get("id").toString());
                userInfoCache.setTel(Long.valueOf(data.get("tel").toString()));
                userInfoCache.setUserId(id);
                userInfoCache.setUserName(data.get("name") != null ? data.get("name").toString() : "");
                showWindowModel();
            }else{
                strCache.setErrorMsg(jsonObject.getString("desc"));
                showErrorModel();
            }
        }
    }

    public void register() throws IOException {
        if (account.getText() == null || account.getText().isEmpty()
                || password.getText() == null || password.getText().isEmpty()) {
            showErrorModel();
        } else {
            String reg = "^[0-9]{11}$";
            StringBuffer sb = new StringBuffer();
            if(!Pattern.matches(reg,account.getText())){
                sb.append("手机号格式错误,请重新输入!");
            }
            if(sb.length() > 0){
                strCache.setErrorMsg(sb.toString());
                showErrorModel();
                return;
            }
            if(password.getText().length() > 20){
                sb.append("密码长度不能超过20 !");
            }
            if(sb.length() == 0){
                String accountText = account.getText();
                String passwordText = password.getText();
                String params = "tel=" + accountText + "&password=" + passwordText;
                String post = HttpUtil.sendPost(ApiUrlManager.regiser() + "?" + params, "");
                JSONObject jsonObject = JSONObject.parseObject(post);
                if(jsonObject.getString("result") != null && jsonObject.getString("result").equals("200")){
                    Stage stage = FxmlInitCtroller.getInstance().getSuccessStage();
                    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("success.fxml"));
                    stage.setTitle("操作成功");
                    Scene scene = new Scene(root, 300, 200);
                    stage.setScene(scene);
                    stage.show();
                }else if(jsonObject.getString("desc") != null){
                    strCache.setErrorMsg(jsonObject.getString("desc"));
                    showErrorModel();
                }
            }else{
                strCache.setErrorMsg(sb.toString());
                showErrorModel();
            }
        }
    }

    private void showWindowModel() throws IOException {
        Stage stage = fxmlInitCtroller.getStage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("window.fxml"));
        stage.setTitle("聊天");
        Scene scene = new Scene(root, 650, 400);
        stage.setScene(scene);
        stage.setScene(AddStyleCssUtil.addSceneStyle(scene));
        stage.getIcons().add(AddStyleCssUtil.addImageIcon());
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
//                MySystemTray.getInstance().hide(stage);
            }
        });
//        MySystemTray.getInstance().listen(stage);
    }

    private void showErrorModel() throws IOException {
        Stage stage = fxmlInitCtroller.getErrorStage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("error.fxml"));
        stage.setTitle("错误");
        Scene scene = new Scene(root, 300, 150);
        stage.setScene(scene);
        stage.setScene(AddStyleCssUtil.addSceneStyle(scene));
        stage.getIcons().add(AddStyleCssUtil.addImageIcon());
        stage.show();
    }
}
