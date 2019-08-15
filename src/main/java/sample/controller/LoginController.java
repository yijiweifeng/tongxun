package sample.controller;

import com.alibaba.fastjson.JSONObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.client.IMSClientBootstrap;
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

    private StrCacheUtil strCacheUtil = StrCacheUtil.getInstance();

    private UserInfoCacheUtil userInfoCacheUtil = UserInfoCacheUtil.getInstance();

    @FXML
    private TextField account;

    @FXML
    private PasswordField password;

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
                Integer id = Integer.valueOf(data.get("id").toString());
                userInfoCacheUtil.setTel((Long)(data.get("tel")));
                userInfoCacheUtil.setUserId(id);
                userInfoCacheUtil.setUserName(data.get("name").toString());
                showWindowModel();
            }else{
                strCacheUtil.setErrorMsg(jsonObject.getString("desc"));
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
                strCacheUtil.setErrorMsg(sb.toString());
                showErrorModel();
                return;
            }
            if(password.getText().length() > 20){
                sb.append("密码长度不能超过20 !");
            }
            if(sb.length() == 0){
                String accountText = account.getText();
                String passwordText = password.getText();
                String params = "tel=" + accountText + "&password" + passwordText;
                String post = HttpUtil.sendPost(ApiUrlManager.regiser() + "?" + params, "");
                JSONObject jsonObject = JSONObject.parseObject(post);
                if(jsonObject.getString("result") != null && jsonObject.getString("result").equals("200")){
                    showWindowModel();
                }
            }else{
                strCacheUtil.setErrorMsg(sb.toString());
                showErrorModel();
            }
        }
    }

    private void showWindowModel() throws IOException {
        Stage stage = fxmlInitCtroller.getStage();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("window.fxml"));
        stage.setTitle("window");
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void showErrorModel() throws IOException {
        Stage stage = fxmlInitCtroller.getStage();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("error.fxml"));
        stage.setTitle("error");
        Scene scene = new Scene(root, 300, 150);
        stage.setScene(scene);
        stage.show();
    }
}
