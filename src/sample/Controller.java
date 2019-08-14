package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.client.MyChatClient;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

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

    private MyChatClient myChatClient = new MyChatClient();

    private static Connection connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = initializeDB();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    myChatClient.testClientRun();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void showDateTime(ActionEvent event) {
        String sendTextStr = sendText.getText();
        if (sendTextStr != null && !sendTextStr.isEmpty()) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sb.append(sdf.format(date) + "\n");
            sb.append(sendTextStr + "\n" + "\n");
            window.setText(sb.toString());
            myChatClient.sendMsg(sdf.format(date) + "\n" + sendTextStr);
        }
    }

    //新增好友
    public void addUser(ActionEvent event){

    }

    //获取好友列表
    public void showMyUser(ActionEvent event){

    }

    private Connection initializeDB() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/tongxun", "root", "root");
            System.out.println("Database connected");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    public void login(){
        loginModel.setVisible(false);
        loginModel.setManaged(false);
        windowModel.setVisible(true);
        windowModel.setManaged(true);
    }
}
