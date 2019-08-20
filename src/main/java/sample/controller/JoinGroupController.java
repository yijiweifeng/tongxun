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
import sample.client.cache.ChatWindowCache;
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
 * Date: 2019/8/20
 * Time: 18:32
 * Description: No Description
 */
public class JoinGroupController implements Initializable {

    @FXML
    private GridPane userList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getFriendList();
    }

    public void getFriendList(){
        userList.getChildren().clear();
        String friend_list = ApiUrlManager.get_friend_list();
        String post = HttpUtil.sendPost(friend_list + "?id=" + UserInfoCache.getInstance().getUserId(), "");
        JSONObject jsonObject = JSONObject.parseObject(post);
        if (jsonObject.getString("result") != null && jsonObject.getString("result").equals("200")) {
            List<JSONObject> data = (List<JSONObject>) (jsonObject.get("data"));
            int index = 0;
            for (final JSONObject obj : data) {
                if(obj.getLong("id").longValue() == UserInfoCache.getInstance().getUserId().longValue()){
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
                            joinGroup(obj.getLong("id"));
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

    public void joinGroup(Long friendId) throws IOException {
        String joinGroup = ApiUrlManager.joinGroup();
        String post = HttpUtil.sendPost(joinGroup + "?userId=" + friendId + "&groupId=" + ChatWindowCache.getInstance().getToId(), "");
        JSONObject jsonObject = JSONObject.parseObject(post);
        if (jsonObject.getString("result") != null && jsonObject.getString("result").equals("200")) {
            Stage stage = FxmlInitCtroller.getInstance().getSuccessStage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("success.fxml"));
            stage.setTitle("操作成功");
            Scene scene = new Scene(root, 300, 200);
            stage.setScene(scene);
            stage.show();
        }else{
            StrCache.getInstance().setErrorMsg(jsonObject.getString("desc"));
            Stage stage = FxmlInitCtroller.getInstance().getErrorStage();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("error.fxml"));
            stage.setTitle("错误");
            Scene scene = new Scene(root, 300, 150);
            stage.setScene(scene);
            stage.show();
        }
    }

}
