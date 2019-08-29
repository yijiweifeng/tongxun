package sample.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.client.cache.StrCache;
import sample.client.cache.UserInfoCache;
import sample.client.utils.AddStyleCssUtil;
import sample.client.utils.ApiUrlManager;
import sample.client.utils.HttpUtil;
import sample.fxmlinit.FxmlInitCtroller;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/20
 * Time: 15:27
 * Description: No Description
 */
public class AddGroupController implements Initializable {

    @FXML
    private JFXTextField groupName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void addGroup() throws IOException {
        if(groupName.getText() != null && !groupName.getText().isEmpty()){
            String add_group = ApiUrlManager.add_group();
            String params = "?userId=" + UserInfoCache.getInstance().getUserId()
                    + "&name=" + URLEncoder.encode( groupName.getText(), "UTF-8" ) + "&type=1";
            String post = HttpUtil.sendPost(add_group + params, "");
            JSONObject jsonObject = JSONObject.parseObject(post);
            if (jsonObject.getString("result") != null && jsonObject.getString("result").equals("200")) {
                WindowController.windowController.showMyGroup();
                Stage stage = FxmlInitCtroller.getInstance().getSuccessStage();
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("success.fxml"));
                stage.setTitle("操作成功");
                Scene scene = new Scene(root, 300, 200);
                stage.setScene(scene);
                stage.setScene(AddStyleCssUtil.addSceneStyle(scene));
                stage.getIcons().add(AddStyleCssUtil.addImageIcon());
                stage.show();
                FxmlInitCtroller.getInstance().getAddGroupStage().close();
            }else{
                StrCache.getInstance().setErrorMsg("操作失败!");
                Stage stage = FxmlInitCtroller.getInstance().getErrorStage();
                stage.close();
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("error.fxml"));
                stage.setTitle("错误");
                Scene scene = new Scene(root, 300, 150);
                stage.setScene(scene);
                stage.setScene(AddStyleCssUtil.addSceneStyle(scene));
                stage.getIcons().add(AddStyleCssUtil.addImageIcon());
                stage.show();
            }
        }else{
            StrCache.getInstance().setErrorMsg("群组名称不能为空!");
            Stage stage = FxmlInitCtroller.getInstance().getErrorStage();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("error.fxml"));
            stage.setTitle("错误");
            Scene scene = new Scene(root, 300, 150);
            stage.setScene(scene);
            stage.setScene(AddStyleCssUtil.addSceneStyle(scene));
            stage.getIcons().add(AddStyleCssUtil.addImageIcon());
            stage.show();
        }
    }

}
