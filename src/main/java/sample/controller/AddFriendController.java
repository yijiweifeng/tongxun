package sample.controller;

import com.alibaba.fastjson.JSONObject;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import sample.client.bean.ChatRecodeBean;
import sample.client.utils.ApiUrlManager;
import sample.client.utils.HttpUtil;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getUserList();
    }

    private void getUserList() {
        userList.getChildren().clear();
        String get_user_list = ApiUrlManager.get_user_list();
        String post = HttpUtil.sendGet(get_user_list, "");
        JSONObject jsonObject = JSONObject.parseObject(post);
        if (jsonObject.getString("result") != null && jsonObject.getString("result").equals("200")) {
            List<JSONObject> data = (List<JSONObject>) (jsonObject.get("data"));
            int index = 0;
            for (JSONObject obj : data) {
                final Label label = new Label();
                label.setAlignment(Pos.CENTER_LEFT);
                label.setText(obj.get("name") != null ? (obj.getString("name") + "\n" + obj.getLong("tel")) : (obj.getLong("tel") + ""));
                label.setPrefWidth(596);
                label.setPrefHeight(50);
                label.setTextFill(Paint.valueOf("WHITE"));
                label.setStyle("-fx-background-color:#00CDCD;-fx-border-color:#87CEEB");
                Button button = new Button();
                button.setPrefSize(25,25);
                button.setTextFill(Paint.valueOf("WHITE"));
                button.setAlignment(Pos.CENTER);
                button.setText("+");
                button.setFont(Font.font(15));
                button.setLayoutX(550);
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println();
                    }
                });
                label.setLabelFor(button);
                userList.add(label, 0, index);
                index++;
            }
        }
    }

}
