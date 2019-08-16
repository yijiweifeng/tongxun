package sample.controller;

import com.alibaba.fastjson.JSONObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.apache.log4j.Logger;
import sample.client.IMSClientBootstrap;
import sample.client.NettyTcpClient;
import sample.client.bean.GoodFriendBean;
import sample.client.cache.ChatWindowCache;
import sample.client.event.Events;
import sample.client.utils.ApiUrlManager;
import sample.client.utils.HttpUtil;
import sample.client.utils.PropertiesUtil;
import sample.client.cache.UserInfoCache;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class WindowController implements Initializable {

    private static Logger logger = Logger.getLogger(WindowController.class);

    private UserInfoCache userInfoCache = UserInfoCache.getInstance();

    @FXML
    private Label userName;

    @FXML
    private GridPane userList;

    @FXML
    private AnchorPane chatUserTop;

    private ChatWindowCache chatWindowCache = ChatWindowCache.getInstance();

    public static final String[] EVENTS = {
            Events.CHAT_SINGLE_MESSAGE
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String token = "token_" + userInfoCache.getUserId();
        String hosts = null;
        try {
            hosts = "[{\"host\":\"" + PropertiesUtil.getPro("connect.properties", "connect.server.ip") + "\", \"port\":" + PropertiesUtil.getPro("connect.properties", "connect.server.port") + "}]";
            IMSClientBootstrap.getInstance().init(userInfoCache.getUserId() + "", token, hosts, 1);
            userName.setText(userInfoCache.getTel() + "");
            showMyUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //新增好友
    public void addUser(ActionEvent event) {

    }

    //新增群组
    public void addGroup(){

    }

    //获取好友列表
    public void showMyUser() {
        List<JSONObject> data = queryMyUser();
        List<GoodFriendBean> goodFriendBeans = parseGoodFriendBean(data);
        int i = 0;
        for (final GoodFriendBean goodFriendBean : goodFriendBeans) {
            final Label label = new Label();
            label.setText(goodFriendBean.getName() + "\n" + goodFriendBean.getTel());
            label.setPrefSize(200,50);
            label.setPadding(new Insets(10));
            label.setStyle("-fx-background-color:#39E639");
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    chatWindowCache.setFromId(userInfoCache.getUserId());
                    System.out.println();
                }
            });
            label.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    label.setStyle("-fx-background-color:#39E639");
                }
            });
            label.setOnMouseMoved(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    label.setStyle("-fx-background-color:#67E667");
                }
            });
            userList.add(label,0,i);
            i++;
        }
    }

    //查看信息列表
    public void showMassgeList(){

    }

    //查看通讯录
    public void showMailList(){

    }

    private List<JSONObject> queryMyUser() {
        String friend_list = ApiUrlManager.get_friend_list();
        String post = HttpUtil.sendPost(friend_list + "?id=" + userInfoCache.getUserId(), "");
        JSONObject jsonObject = JSONObject.parseObject(post);
        if (jsonObject.getString("result") != null && jsonObject.getString("result").equals("200")) {
            List<JSONObject> data = (List<JSONObject>) (jsonObject.get("data"));
            return data;
        }
        return null;
    }

    private List<GoodFriendBean> parseGoodFriendBean(List<JSONObject> list){
        List<GoodFriendBean> goodFriendBeans = new ArrayList<>();
        for(JSONObject obj : list){
            GoodFriendBean goodFriendBean = new GoodFriendBean();
            goodFriendBean.setId(obj.getLong("id"));
            goodFriendBean.setName(obj.getString("name"));
            goodFriendBean.setTel(obj.getLong("tel"));
            goodFriendBean.setOnLine(obj.getBoolean("onLine"));
            goodFriendBeans.add(goodFriendBean);
        }
        return goodFriendBeans;
    }
}
