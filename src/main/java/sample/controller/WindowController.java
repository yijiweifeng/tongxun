package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;
import sample.Demo;
import sample.client.IMSClientBootstrap;
import sample.client.NettyTcpClient;
import sample.client.event.Events;
import sample.client.utils.PropertiesUtil;
import sample.client.utils.UserInfoCacheUtil;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class WindowController implements Initializable {

    private static Logger logger = Logger.getLogger(WindowController.class);

    private UserInfoCacheUtil userInfoCacheUtil = UserInfoCacheUtil.getInstance();

    @FXML
    private Button send;

    @FXML
    private TextArea window;

    @FXML
    private TextArea sendText;

    @FXML
    private Button addUser;

    @FXML
    private MenuButton myUser;

    @FXML
    private Button login;

    @FXML
    private Pane loginModel;

    @FXML
    private AnchorPane windowModel;

    private static StringBuffer sb = new StringBuffer();

    private NettyTcpClient myChatClient = NettyTcpClient.getInstance();

    public static final String[] EVENTS = {
            Events.CHAT_SINGLE_MESSAGE
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String token = "token_" + userInfoCacheUtil.getUserId();
        String hosts = null;
        try {
            hosts = "[{\"host\":\"" + PropertiesUtil.getPro("connect.properties", "connect.server.ip") + "\", \"port\":" + PropertiesUtil.getPro("connect.properties", "connect.server.port") + "}]";
            IMSClientBootstrap.getInstance().init(userInfoCacheUtil.getUserId() + "", token, hosts, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDateTime(ActionEvent event) {
        String sendTextStr = sendText.getText();
        if (sendTextStr != null && !sendTextStr.isEmpty()) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sb.append(sdf.format(date) + "\n");
            sb.append(sendTextStr + "\n" + "\n");
            window.setText(sb.toString());
//            myChatClient.sendMsg(sdf.format(date) + "\n" + sendTextStr);
            Demo.send("100002", "100001", sdf.format(date) + "\n" + sendTextStr);
        }
    }

    //新增好友
    public void addUser(ActionEvent event) {

    }

    //获取好友列表
    public void showMyUser(ActionEvent event) {

    }
}
