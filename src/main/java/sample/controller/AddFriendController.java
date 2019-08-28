package sample.controller;

import com.alibaba.fastjson.JSONObject;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.Data;
import sample.client.cache.AllUserInfoCache;
import sample.client.cache.StrCache;
import sample.client.cache.UserInfoCache;
import sample.client.utils.ApiUrlManager;
import sample.client.utils.HttpUtil;
import sample.fxmlinit.FxmlInitCtroller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/19
 * Time: 16:24
 * Description: No Description
 */
public class AddFriendController implements Initializable {

    @FXML
    private GridPane userList;

    @FXML
    private TextField userTelInput;

    public static AddFriendController addFriendController = null;

    private UserInfoCache userInfoCache = UserInfoCache.getInstance();

    private FxmlInitCtroller fxmlInitCtroller = FxmlInitCtroller.getInstance();

    private StrCache strCache = StrCache.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addFriendController = this;
    }

    public void getUserList() {
        userList.getChildren().clear();
        String get_user_list = ApiUrlManager.get_user_list();
        String post = HttpUtil.sendGet(get_user_list,(userTelInput.getText() != null && !userTelInput.getText().isEmpty()) ? "tel=" + userTelInput.getText() : "");
        JSONObject jsonObject = JSONObject.parseObject(post);
        if (jsonObject.getString("result") != null && jsonObject.getString("result").equals("200")) {
            List<JSONObject> data = (List<JSONObject>) (jsonObject.get("data"));
            int index = 0;
            for (final JSONObject obj : data) {
                if(obj.getLong("id").longValue() == userInfoCache.getUserId().longValue()){
                    continue;
                }
                Pane pane = new Pane();
                pane.setPrefSize(596,50);
                pane.setStyle("-fx-background-color:#00CDCD;-fx-border-color:#87CEEB");
                Label label = new Label();
                label.setAlignment(Pos.CENTER_LEFT);
                label.setText(obj.get("name") != null ? (obj.getString("name") + "\n" + obj.getLong("tel")) : (obj.getLong("tel") + ""));
                label.setPrefWidth(550);
                label.setPrefHeight(50);
                label.setTextFill(Paint.valueOf("WHITE"));
                label.setPadding(new Insets(0,10,0,10));
                Button button = new Button();
                button.setPrefSize(36,36);
                button.setTextFill(Paint.valueOf("WHITE"));
                button.setAlignment(Pos.CENTER);
                button.setText("+");
                button.setFont(Font.font(17));
                button.setStyle("-fx-background-color:#00CDCD;-fx-border-color:#FFFFFF");
                button.setLayoutX(550);
                button.setLayoutY(7);
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            addFriend(obj.getLong("id"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                pane.getChildren().add(label);
                pane.getChildren().add(button);
                userList.add(pane, 0, index);
                index++;
            }
        }
    }

    private void addFriend(Long friendId) throws IOException {
        String add_friend = ApiUrlManager.add_friend();
        String post = HttpUtil.sendPost(add_friend + "?id=" + userInfoCache.getUserId() + "&friendId=" + friendId, "");
        JSONObject jsonObject = JSONObject.parseObject(post);
        if (jsonObject.getString("result") != null && jsonObject.getString("result").equals("200")) {
            AllUserInfoCache.getInstance().clearMap();
            WindowController.windowController.showMyUser();
            Stage stage = fxmlInitCtroller.getSuccessStage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("success.fxml"));
            stage.setTitle("操作成功");
            Scene scene = new Scene(root, 300, 200);
            stage.setScene(scene);
            stage.show();
        }else{
            strCache.setErrorMsg(jsonObject.getString("desc"));
            Stage stage = fxmlInitCtroller.getErrorStage();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("error.fxml"));
            stage.setTitle("错误");
            Scene scene = new Scene(root, 300, 150);
            stage.setScene(scene);
            stage.show();
        }
    }

}
