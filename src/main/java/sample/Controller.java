package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.client.IMSClientBootstrap;
import sample.client.MessageProcessor;
import sample.client.MessageType;
import sample.client.NettyTcpClient;
import sample.client.bean.SingleMessage;
import sample.client.event.CEventCenter;
import sample.client.event.Events;
import sample.client.event.I_CEventListener;
import sample.client.listener.impl.IMSConnectStatusCallbackImpl;
import sample.client.listener.impl.OnEventListenerImpl;
import sample.client.protobuf.MessageProtobuf;
import sample.client.utils.CThreadPoolExecutor;
import sample.client.utils.MessageBuilder;
import sample.itf.IMSClientInterface;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.Vector;

public class Controller implements Initializable {

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
            Demo.send("100002","100001",sdf.format(date) + "\n" + sendTextStr);
        }
    }

    //新增好友
    public void addUser(ActionEvent event){

    }

    //获取好友列表
    public void showMyUser(ActionEvent event){

    }
}
