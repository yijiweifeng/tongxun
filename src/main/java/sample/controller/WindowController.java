package sample.controller;

import com.alibaba.fastjson.JSONObject;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Data;
import org.apache.log4j.Logger;
import sample.client.IMSClientBootstrap;
import sample.client.MessageType;
import sample.client.NettyTcpClient;
import sample.client.bean.ChatRecodeBean;
import sample.client.bean.GoodFriendBean;
import sample.client.bean.SingleMessage;
import sample.client.cache.ChatMessageCache;
import sample.client.cache.ChatWindowCache;
import sample.client.cache.UserInfoCache;
import sample.client.event.CEventCenter;
import sample.client.event.Events;
import sample.client.listener.impl.IMSChatSingleMessageListener;
import sample.client.protobuf.MessageProtobuf;
import sample.client.utils.ApiUrlManager;
import sample.client.utils.HttpUtil;
import sample.client.utils.MessageBuilder;
import sample.client.utils.PropertiesUtil;
import sample.fxmlinit.FxmlInitCtroller;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Data
public class WindowController implements Initializable {

    private static Logger logger = Logger.getLogger(WindowController.class);

    private UserInfoCache userInfoCache = UserInfoCache.getInstance();

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static WindowController windowController;

    private ChatWindowCache chatWindowCache = ChatWindowCache.getInstance();

    private ChatMessageCache chatMessageCache = ChatMessageCache.getInstance();

    @FXML
    private Label userName;

    @FXML
    private GridPane userList;

    @FXML
    private Label chatUserTop;

    @FXML
    private GridPane chatRecord;

    @FXML
    private TextArea inputText;

    @FXML
    private ScrollPane recoreModel;

    @FXML
    private VBox modelBox;

    @FXML
    private AnchorPane inputAndSend;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        windowController = this;
        String token = "token_" + userInfoCache.getUserId();
        String hosts = null;
        try {
            hosts = "[{\"host\":\"" + PropertiesUtil.getPro("connect.properties", "connect.server.ip") + "\", \"port\":" + PropertiesUtil.getPro("connect.properties", "connect.server.port") + "}]";
            IMSClientBootstrap.getInstance().init(userInfoCache.getUserId() + "", token, hosts, 1);
            CEventCenter.registerEventListener(new IMSChatSingleMessageListener(), Events.CHAT_SINGLE_MESSAGE);
            userName.setText(userInfoCache.getTel() + "");
            showMassgeList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //新增好友
    public void addUser() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("addFriend.fxml"));
        stage.setTitle("添加好友");
        Scene scene = new Scene(root, 650, 400);
        stage.setScene(scene);
        stage.show();
    }

    //新增群组
    public void addGroup() {

    }

    //获取好友列表
    public void showMyUser() {
        List<JSONObject> data = queryMyUser();
        List<GoodFriendBean> goodFriendBeans = parseGoodFriendBean(data);
        int i = 0;
        for (final GoodFriendBean goodFriendBean : goodFriendBeans) {
            Label label = new Label();
            label.setText(goodFriendBean.getName() + "\n" + goodFriendBean.getTel());
            label.setPrefSize(148, 60);
            label.setStyle("-fx-background-color:#39E639");
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    inputAndSend.setVisible(true);
                    chatMessageCache.getUserNoReceiveSingerMsgMap().remove(goodFriendBean.getId());
                    chatWindowCache.setFromId(userInfoCache.getUserId());
                    chatWindowCache.setToId(goodFriendBean.getId());
                    chatWindowCache.setName(goodFriendBean.getName());
                    chatWindowCache.setTel(goodFriendBean.getTel());
                    chatUserTop.setText(goodFriendBean.getName() + "\n" + goodFriendBean.getTel());
                    chatUserTop.setPadding(new Insets(5));
                    updateChatRecord();
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
            userList.add(label, 0, i);
            i++;
        }
    }

    //查看信息列表
    public void showMassgeList() {
        showSingleChatList();
        showGroupChatList();
    }

    //查看单聊信息列表
    private void showSingleChatList() {
        userList.getChildren().clear();
        List<JSONObject> data = queryMyUser();
        List<GoodFriendBean> goodFriendBeans = parseGoodFriendBean(data);
        int i = 0;
        for (final GoodFriendBean goodFriendBean : goodFriendBeans) {
            if (chatMessageCache.getUserNoReceiveSingerMsgMap().get(goodFriendBean.getId()) == null) {
                continue;
            }
            Label label = new Label();
            label.setText(goodFriendBean.getName() + "(" + chatMessageCache.getUserNoReceiveSingerMsgMap().get(goodFriendBean.getId())
                    + ")\n" + goodFriendBean.getTel());
            label.setPrefSize(148, 60);
            label.setStyle("-fx-background-color:#39E639");
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    inputAndSend.setVisible(true);
                    chatMessageCache.getUserNoReceiveSingerMsgMap().remove(goodFriendBean.getId());
                    chatWindowCache.setFromId(userInfoCache.getUserId());
                    chatWindowCache.setToId(goodFriendBean.getId());
                    chatWindowCache.setName(goodFriendBean.getName());
                    chatWindowCache.setTel(goodFriendBean.getTel());
                    chatUserTop.setText(goodFriendBean.getName() + "\n" + goodFriendBean.getTel());
                    chatUserTop.setPadding(new Insets(5));
                    updateChatRecord();
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
            userList.add(label, 0, i);
            i++;
        }
    }

    //查看群聊信息列表
    private void showGroupChatList() {

    }

    //发送信息
    public void sendMassge() {
        if (!inputText.getText().isEmpty() && chatWindowCache.getToId() != null) {
            SingleMessage message = new SingleMessage();
            message.setMsgId(UUID.randomUUID().toString());
            message.setMsgType(MessageType.SINGLE_CHAT.getMsgType());
            message.setMsgContentType(MessageType.MessageContentType.TEXT.getMsgContentType());
            message.setFromId(userInfoCache.getUserId() + "");
            message.setToId(chatWindowCache.getToId() + "");
            message.setTimestamp(System.currentTimeMillis());
            message.setContent(inputText.getText());
            MessageProtobuf.Msg build = MessageBuilder.getProtoBufMessageBuilderByAppMessage(MessageBuilder.buildAppMessage(message)).build();
            NettyTcpClient.getInstance().sendMsg(build);
            inputText.setText("");
        }
        updateChatRecord();
    }

    public void showOrHideList(){
        if(modelBox.isVisible()){
            modelBox.setVisible(false);
        }else{
            modelBox.setVisible(true);
        }
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

    private List<GoodFriendBean> parseGoodFriendBean(List<JSONObject> list) {
        List<GoodFriendBean> goodFriendBeans = new ArrayList<>();
        for (JSONObject obj : list) {
            GoodFriendBean goodFriendBean = new GoodFriendBean();
            goodFriendBean.setId(obj.getLong("id"));
            goodFriendBean.setName(obj.getString("name"));
            goodFriendBean.setTel(obj.getLong("tel"));
            goodFriendBean.setOnLine(obj.getBoolean("onLine"));
            goodFriendBeans.add(goodFriendBean);
        }
        return goodFriendBeans;
    }

    private Vector<ChatRecodeBean> getChatRecode() {
        Vector<ChatRecodeBean> list = new Vector<>();
        String params = "userId=" + userInfoCache.getUserId() + "&friendOrGroupId=" + chatWindowCache.getToId() + "&infoType=1";
        String post = HttpUtil.sendPost(ApiUrlManager.get_friend_info_list() + "?" + params, "");
        JSONObject jsonObject = JSONObject.parseObject(post);
        if (jsonObject.getString("result") != null && jsonObject.getString("result").equals("200")) {
            JSONObject data = (JSONObject) (jsonObject.get("data"));
            Vector<ChatRecodeBean> mySendList = getChatRecodeBeans((List<JSONObject>) (data.get("mySendList")));
            Vector<ChatRecodeBean> myReceivedList = getChatRecodeBeans((List<JSONObject>) (data.get("myReceivedList")));
            list.addAll(mySendList);
            list.addAll(myReceivedList);
            list.addAll(getNotSendMsg());
            list = filterList(list);
            Collections.sort(list, Comparator.comparing(ChatRecodeBean::getSendTime));
        }
        return list;
    }

    private Vector<ChatRecodeBean> filterList(Vector<ChatRecodeBean> list) {
        Map<String, ChatRecodeBean> chatRecodeBeanMap = new HashMap<>();
        Vector<ChatRecodeBean> newList = new Vector();
        for (ChatRecodeBean chatRecodeBean : list) {
            if (chatRecodeBean.getSendTime().equals(0L)) {
                chatRecodeBeanMap.put(chatRecodeBean.getInfoId(), chatRecodeBean);
            } else {
                if (chatRecodeBeanMap.get(chatRecodeBean.getInfoId()) == null) {
                    chatRecodeBeanMap.put(chatRecodeBean.getInfoId(), chatRecodeBean);
                }
            }
        }
        for (String key : chatRecodeBeanMap.keySet()) {
            newList.add(chatRecodeBeanMap.get(key));
        }
        return newList;
    }

    private Vector<ChatRecodeBean> getNotSendMsg() {
        Vector<ChatRecodeBean> list = new Vector<>();
        String params = "id=" + chatWindowCache.getToId();
        String post = HttpUtil.sendPost(ApiUrlManager.get_not_received_list() + "?" + params, "");
        JSONObject jsonObject = JSONObject.parseObject(post);
        if (jsonObject.getString("result") != null && jsonObject.getString("result").equals("200")) {
            list.addAll(getChatRecodeBeans((List<JSONObject>) (jsonObject.get("data"))));
        }
        return list;
    }

    private Vector<ChatRecodeBean> getChatRecodeBeans(List<JSONObject> list) {
        Vector<ChatRecodeBean> chatRecodeBeans = new Vector();
        for (JSONObject object : list) {
            ChatRecodeBean chatRecodeBean = new ChatRecodeBean();
            chatRecodeBean.setContent(object.getString("content"));
            chatRecodeBean.setContentType(object.getInteger("contentType"));
            chatRecodeBean.setFromId(object.getLong("fromId"));
            chatRecodeBean.setGroupId(object.getLong("groupId"));
            chatRecodeBean.setId(object.getLong("id"));
            chatRecodeBean.setInfoId(object.getString("infoId"));
            chatRecodeBean.setInfoType(object.getInteger("infoType"));
            if (object.get("receiveTime") != null) {
                chatRecodeBean.setReceiveTime(Long.valueOf(object.getString("receiveTime")));
                chatRecodeBean.setSend(true);
            } else {
                chatRecodeBean.setReceiveTime(0L);
                chatRecodeBean.setSend(false);
            }
            chatRecodeBean.setSendTime(Long.valueOf(object.getString("sendTime")));
            chatRecodeBean.setToId(object.getLong("toId"));
            chatRecodeBean.setUploadUrl(object.getString("uploadUrl"));
            chatRecodeBeans.add(chatRecodeBean);
        }
        return chatRecodeBeans;
    }

    public void updateChatRecord() {
        chatRecord.getChildren().clear();
        Vector<ChatRecodeBean> chatRecode = getChatRecode();
        int index = 0;
        for (ChatRecodeBean chatRecodeBean : chatRecode) {
            String msg;
            final Label label = new Label();
            if (chatRecodeBean.getFromId().equals(userInfoCache.getUserId())) {
                msg = userInfoCache.getTel() + " " + formatDateTime(chatRecodeBean.getSendTime()) + "\n";
                label.setAlignment(Pos.CENTER_RIGHT);
            } else {
                msg = chatWindowCache.getTel() + " " + formatDateTime(chatRecodeBean.getSendTime()) + "\n";
                label.setAlignment(Pos.CENTER_LEFT);
            }
            if (!chatRecodeBean.isSend()) {
                msg += "(待接收)";
            }
            msg += chatRecodeBean.getContent();
            label.setText(msg);

            label.setPrefWidth(375);
            label.setPrefHeight(50);
            chatRecord.add(label, 0, index);
            index++;
        }
        recoreModel.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        recoreModel.prefHeightProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            recoreModel.setVvalue(1);
        });
        recoreModel.setPrefHeight(chatRecord.getHeight());
        recoreModel.setVvalue(1);
    }

    private String formatDateTime(long dateTime) {
        String format = sdf.format(new Date(dateTime));
        return format;
    }

    public void updateMessageList() {

    }

}
