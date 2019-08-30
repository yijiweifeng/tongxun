package sample.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.Data;
import org.apache.log4j.Logger;
import sample.client.IMSClientBootstrap;
import sample.client.MessageType;
import sample.client.NettyTcpClient;
import sample.client.bean.ChatRecodeBean;
import sample.client.bean.GoodFriendBean;
import sample.client.bean.GroupBean;
import sample.client.bean.SingleMessage;
import sample.client.cache.AllUserInfoCache;
import sample.client.cache.ChatMessageCache;
import sample.client.cache.ChatWindowCache;
import sample.client.cache.UserInfoCache;
import sample.client.event.CEventCenter;
import sample.client.event.Events;
import sample.client.listener.impl.IMSChatGroupMessageListener;
import sample.client.listener.impl.IMSChatGroupNotMessageListener;
import sample.client.listener.impl.IMSChatSingleMessageListener;
import sample.client.listener.impl.IMSChatSingleNotMessageListener;
import sample.client.protobuf.MessageProtobuf;
import sample.client.utils.*;
import sample.fxmlinit.FxmlInitCtroller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

    private FxmlInitCtroller fxmlInitCtroller = FxmlInitCtroller.getInstance();

    public boolean isShowGroup = false;

    public boolean isShowSinger = false;

    @FXML
    private Label userName;

    @FXML
    private GridPane userList;

    @FXML
    private Pane chatUserTop;

    @FXML
    private ListView chatRecord;

    @FXML
    private TextArea inputText;

    @FXML
    private VBox modelBox;

    @FXML
    private AnchorPane inputAndSend;

    @FXML
    private AnchorPane chatModel;

    @FXML
    private Label myFriend;

    @FXML
    private Label myGroup;

    @FXML
    private Label addFriend;

    @FXML
    private Label addGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        windowController = this;
        String token = "token_" + userInfoCache.getUserId();
        String hosts = null;
        try {
            hosts = "[{\"host\":\"" + PropertiesUtil.getPro("connect.properties", "connect.server.ip") + "\", \"port\":" + PropertiesUtil.getPro("connect.properties", "connect.server.port") + "}]";
            IMSClientBootstrap.getInstance().init(userInfoCache.getUserId() + "", token, hosts, 1);
            CEventCenter.registerEventListener(new IMSChatSingleMessageListener(), Events.CHAT_SINGLE_MESSAGE);
            CEventCenter.registerEventListener(new IMSChatSingleNotMessageListener(), Events.CHAT_SINGLE_MESSAGE_NOT);
            CEventCenter.registerEventListener(new IMSChatGroupMessageListener(), Events.CHAT_GROUP_MESSAGE);
            CEventCenter.registerEventListener(new IMSChatGroupNotMessageListener(), Events.CHAT_GROUP_MESSAGE_NOT);
            userName.setText(userInfoCache.getTel() + "");
            inputText.setWrapText(true);
            inputText.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if(event.getCode() == KeyCode.ENTER){
                        sendMassge();
                    }
                }
            });
            myFriend.setStyle("-fx-background-color:#79A5E7");
            myFriend.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    myFriend.setStyle("-fx-background-color:#79A5E7");
                }
            });
            myFriend.setOnMouseMoved(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    myFriend.setStyle("-fx-background-color:#9BC3FF");
                }
            });
            myGroup.setStyle("-fx-background-color:#79A5E7");
            myGroup.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    myGroup.setStyle("-fx-background-color:#79A5E7");
                }
            });
            myGroup.setOnMouseMoved(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    myGroup.setStyle("-fx-background-color:#9BC3FF");
                }
            });
            addFriend.setStyle("-fx-background-color:#79A5E7");
            addFriend.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    addFriend.setStyle("-fx-background-color:#79A5E7");
                }
            });
            addFriend.setOnMouseMoved(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    addFriend.setStyle("-fx-background-color:#9BC3FF");
                }
            });
            addGroup.setStyle("-fx-background-color:#79A5E7");
            addGroup.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    addGroup.setStyle("-fx-background-color:#79A5E7");
                }
            });
            addGroup.setOnMouseMoved(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    addGroup.setStyle("-fx-background-color:#9BC3FF");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //新增好友
    public void addUser() throws IOException {
        Stage stage = fxmlInitCtroller.getAddUserStage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("addFriend.fxml"));
        stage.setTitle("添加好友");
        Scene scene = new Scene(root, 650, 400);
        stage.setScene(scene);
        stage.setScene(AddStyleCssUtil.addSceneStyle(scene));
        stage.getIcons().add(AddStyleCssUtil.addImageIcon());
        stage.setResizable(false);
        stage.show();
    }

    //获取好友列表
    public void showMyUser() {
        windowController.setShowGroup(false);
        windowController.setShowSinger(true);
        userList.getChildren().clear();
        List<JSONObject> data = queryMyUser();
        List<GoodFriendBean> goodFriendBeans = parseGoodFriendBean(data);
        int i = 0;
        for (final GoodFriendBean goodFriendBean : goodFriendBeans) {
            Label label = new Label();
            Integer count = chatMessageCache.getUserNoReceiveSingerMsgMap().get(goodFriendBean.getId());
            if(count != null && count > 0){
                label.setText((goodFriendBean.getName() != null ? (goodFriendBean.getName() + "\n") : "") + goodFriendBean.getTel() + "(" + count + "条未读)");
                label.setTextFill(Paint.valueOf("RED"));
            }else{
                label.setText((goodFriendBean.getName() != null ? (goodFriendBean.getName() + "\n") : "") + goodFriendBean.getTel());
                label.setTextFill(Paint.valueOf("#7EACE6"));
            }
            label.setPrefSize(148, 60);
            label.setStyle("-fx-background-color:#ffffff");
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    chatUserTop.getChildren().clear();
                    inputAndSend.setVisible(true);
                    chatMessageCache.getUserNoReceiveSingerMsgMap().put(goodFriendBean.getId(), 0);
                    chatWindowCache.setFromId(userInfoCache.getUserId());
                    chatWindowCache.setToId(goodFriendBean.getId());
                    chatWindowCache.setName(goodFriendBean.getName());
                    chatWindowCache.setTel(goodFriendBean.getTel());
                    Label lab = new Label();
                    lab.setText((goodFriendBean.getName() != null ? (goodFriendBean.getName() + "\n") : "") + goodFriendBean.getTel());
                    lab.setPrefWidth(350);
                    lab.setPrefHeight(50);
                    lab.setPadding(new Insets(5));
                    chatUserTop.getChildren().add(lab);
                    label.setText((goodFriendBean.getName() != null ? (goodFriendBean.getName() + "\n") : "") + goodFriendBean.getTel());
                    label.setTextFill(Paint.valueOf("WHITE"));
                    updateChatRecord();
                }
            });
            label.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    label.setStyle("-fx-background-color:#ffffff");
                    Integer count = chatMessageCache.getUserNoReceiveSingerMsgMap().get(goodFriendBean.getId());
                    if(count != null && count > 0){
                        label.setTextFill(Paint.valueOf("RED"));
                    }else{
                        label.setTextFill(Paint.valueOf("#7EACE6"));
                    }
                }
            });
            label.setOnMouseMoved(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    label.setStyle("-fx-background-color:#7EACE6");
                    label.setTextFill(Paint.valueOf("WHITE"));
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
            if (chatMessageCache.getUserNoReceiveSingerMsgMap().get(goodFriendBean.getId()) > 0) {
                if (goodFriendBean.getName() != null) {
                    label.setText(goodFriendBean.getName() + "(" + chatMessageCache.getUserNoReceiveSingerMsgMap().get(goodFriendBean.getId())
                            + ")\n" + goodFriendBean.getTel());
                } else {
                    label.setText(goodFriendBean.getTel() + "(" + chatMessageCache.getUserNoReceiveSingerMsgMap().get(goodFriendBean.getId())
                            + ")");
                }
            } else {
                label.setText((goodFriendBean.getName() != null ? (goodFriendBean.getName() + "\n") : "") + goodFriendBean.getTel());
            }
            label.setPrefSize(148, 60);
            label.setStyle("-fx-background-color:#ffffff");
            label.setTextFill(Paint.valueOf("#7EACE6"));
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    chatUserTop.getChildren().clear();
                    showSingleChatList();
                    inputAndSend.setVisible(true);
                    chatMessageCache.getUserNoReceiveSingerMsgMap().put(goodFriendBean.getId(), 0);
                    chatWindowCache.setFromId(userInfoCache.getUserId());
                    chatWindowCache.setToId(goodFriendBean.getId());
                    chatWindowCache.setName(goodFriendBean.getName());
                    chatWindowCache.setTel(goodFriendBean.getTel());
                    Label lab = new Label();
                    lab.setText((goodFriendBean.getName() != null ? (goodFriendBean.getName() + "\n") : "") + goodFriendBean.getTel());
                    lab.setPrefWidth(350);
                    lab.setPrefHeight(50);
                    lab.setPadding(new Insets(5));
                    chatUserTop.getChildren().add(lab);
                    updateChatRecord();
                }
            });
            label.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    label.setStyle("-fx-background-color:#ffffff");
                    label.setTextFill(Paint.valueOf("#7EACE6"));
                }
            });
            label.setOnMouseMoved(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    label.setStyle("-fx-background-color:#7EACE6");
                    label.setTextFill(Paint.valueOf("WHITE"));
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
        if (!inputText.getText().trim().isEmpty() && chatWindowCache.getToId() != null) {
            SingleMessage message = new SingleMessage();
            message.setMsgId(UUID.randomUUID().toString());
            if (chatWindowCache.getTel().longValue() == 0) {
                message.setMsgType(MessageType.GROUP_CHAT.getMsgType());
            } else {
                message.setMsgType(MessageType.SINGLE_CHAT.getMsgType());
            }
            message.setMsgContentType(MessageType.MessageContentType.TEXT.getMsgContentType());
            message.setFromId(userInfoCache.getUserId() + "");
            message.setToId(chatWindowCache.getToId() + "");
            message.setTimestamp(System.currentTimeMillis());
            message.setContent(inputText.getText());
            MessageProtobuf.Msg build = MessageBuilder.getProtoBufMessageBuilderByAppMessage(MessageBuilder.buildAppMessage(message)).build();
            NettyTcpClient.getInstance().sendMsg(build);
            inputText.setText("");
        }
        if (chatWindowCache.getTel().longValue() == 0) {
            updateGroupChatRecord();
        } else {
            updateChatRecord();
        }
    }

    public void showOrHideList() {
        if (modelBox.isVisible()) {
            modelBox.setVisible(false);
        } else {
            modelBox.setVisible(true);
        }
    }

    //我的群组
    public void showMyGroup() {
        windowController.setShowGroup(true);
        windowController.setShowSinger(false);
        userList.getChildren().clear();
        List<JSONObject> data = queryMyGroup();
        List<GroupBean> groupBeans = parseGroupBean(data);
        int i = 0;
        for (final GroupBean groupBean : groupBeans) {
            Label label = new Label();
            Integer count = chatMessageCache.getUserNoReceiveGroupMsgMap().get(groupBean.getId());
            if(count != null && count > 0){
                label.setText(groupBean.getGroupName() + "(" + count + "条未读)");
                label.setTextFill(Paint.valueOf("RED"));
            }else{
                label.setText(groupBean.getGroupName());
                label.setTextFill(Paint.valueOf("#7EACE6"));
            }
            label.setPrefSize(148, 60);
            label.setStyle("-fx-background-color:#ffffff");
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    chatUserTop.getChildren().clear();
                    inputAndSend.setVisible(true);
                    chatMessageCache.getUserNoReceiveGroupMsgMap().put(groupBean.getId(), 0);
                    chatWindowCache.setFromId(userInfoCache.getUserId());
                    chatWindowCache.setToId(groupBean.getId());
                    chatWindowCache.setName(groupBean.getGroupName());
                    chatWindowCache.setTel(0L);
                    chatUserTop.setPrefSize(400, 50);
                    Label lab = new Label();
                    lab.setText(groupBean.getGroupName());
                    lab.setPrefWidth(350);
                    lab.setPrefHeight(50);
                    lab.setPadding(new Insets(5));
                    JFXButton button = new JFXButton();
                    button.setPrefSize(36, 36);
                    button.setAlignment(Pos.CENTER);
                    button.setText("+");
                    button.setButtonType(JFXButton.ButtonType.valueOf("RAISED"));
                    button.getStyleClass().add("normal_button");
                    button.setFont(Font.font(17));
                    button.setStyle("-fx-border-color:#FFFFFF");
                    button.setLayoutX(350);
                    button.setLayoutY(7);
                    button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            try {
                                joinGroup();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    chatUserTop.getChildren().add(lab);
                    chatUserTop.getChildren().add(button);
                    label.setText(groupBean.getGroupName());
                    label.setTextFill(Paint.valueOf("WHITE"));
                    updateGroupChatRecord();
                }
            });
            label.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    label.setStyle("-fx-background-color:#ffffff");
                    Integer count = chatMessageCache.getUserNoReceiveGroupMsgMap().get(groupBean.getId());
                    if(count != null && count > 0){
                        label.setTextFill(Paint.valueOf("RED"));
                    }else{
                        label.setTextFill(Paint.valueOf("#7EACE6"));
                    }
                }
            });
            label.setOnMouseMoved(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    label.setStyle("-fx-background-color:#7EACE6");
                    label.setTextFill(Paint.valueOf("WHITE"));
                }
            });
            userList.add(label, 0, i);
            i++;
        }
    }

    //新增群组
    public void addGroup() throws IOException {
        Stage stage = fxmlInitCtroller.getAddGroupStage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("addGroup.fxml"));
        stage.setTitle("添加群组");
        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.setScene(AddStyleCssUtil.addSceneStyle(scene));
        stage.getIcons().add(AddStyleCssUtil.addImageIcon());
        stage.setResizable(false);
        stage.show();
    }

    //添加好友到群组
    public void joinGroup() throws IOException {
        Stage stage = fxmlInitCtroller.getJoinGroupStage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("joinGroup.fxml"));
        stage.setTitle("添加好友到群组");
        Scene scene = new Scene(root, 650, 400);
        stage.setScene(scene);
        stage.setScene(AddStyleCssUtil.addSceneStyle(scene));
        stage.getIcons().add(AddStyleCssUtil.addImageIcon());
        stage.setResizable(false);
        stage.show();
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

    private List<JSONObject> queryMyGroup() {
        List<JSONObject> list = new ArrayList<>();
        List<JSONObject> data = new ArrayList<>();
        String get_my_cteate_group_list = ApiUrlManager.get_my_cteate_group_list();
        String post = HttpUtil.sendPost(get_my_cteate_group_list + "?id=" + userInfoCache.getUserId(), "");
        JSONObject jsonObject = JSONObject.parseObject(post);
        if (jsonObject.getString("result") != null && jsonObject.getString("result").equals("200")) {
            data.addAll((List<JSONObject>) (jsonObject.get("data")));
        }
        String get_my_join_group_list = ApiUrlManager.get_my_join_group_list();
        post = HttpUtil.sendPost(get_my_join_group_list + "?id=" + userInfoCache.getUserId(), "");
        jsonObject = JSONObject.parseObject(post);
        if (jsonObject.getString("result") != null && jsonObject.getString("result").equals("200")) {
            data.addAll((List<JSONObject>) (jsonObject.get("data")));
        }
        Map<Long, JSONObject> map = new HashMap<>();
        for (JSONObject object : data) {
            Long id = object.getLong("id");
            if (!map.containsKey(id)) {
                map.put(id, object);
            }
        }
        for (Long id : map.keySet()) {
            list.add(map.get(id));
        }
        return list;
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

    private List<GroupBean> parseGroupBean(List<JSONObject> list) {
        List<GroupBean> groupBeans = new ArrayList<>();
        for (JSONObject obj : list) {
            GroupBean groupBean = new GroupBean();
            groupBean.setId(obj.getLong("id"));
            groupBean.setCreateUserId(obj.getLong("createUserId"));
            groupBean.setGroupType(obj.getInteger("groupType"));
            groupBean.setGroupName(obj.getString("groupName"));
            groupBeans.add(groupBean);
        }
        return groupBeans;
    }

    private Vector<ChatRecodeBean> getChatRecode(int infoType) {
        Vector<ChatRecodeBean> list = new Vector<>();
        if (chatWindowCache == null || chatWindowCache.getToId() == null) {
            return list;
        }
        String params = "userId=" + userInfoCache.getUserId() + "&friendOrGroupId=" + chatWindowCache.getToId() + "&infoType=" + infoType;
        String post = HttpUtil.sendPost(ApiUrlManager.get_friend_info_list() + "?" + params, "");
        JSONObject jsonObject = JSONObject.parseObject(post);
        if (jsonObject.getString("result") != null && jsonObject.getString("result").equals("200")) {
            JSONObject data = (JSONObject) (jsonObject.get("data"));
            if (infoType == 1) {
                Vector<ChatRecodeBean> mySendList = getChatRecodeBeans((List<JSONObject>) (data.get("mySendList")));
                Vector<ChatRecodeBean> myReceivedList = getChatRecodeBeans((List<JSONObject>) (data.get("myReceivedList")));
                list.addAll(mySendList);
                list.addAll(myReceivedList);
            } else {
                Vector<ChatRecodeBean> groupInfo = getChatRecodeBeans((List<JSONObject>) (data.get("groupInfo")));
                list.addAll(groupInfo);
            }
            try {
                list = filterList(list);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Collections.sort(list, Comparator.comparing(ChatRecodeBean::getSendTime));
        }
        return list;
    }

    private Vector<ChatRecodeBean> filterList(Vector<ChatRecodeBean> list) throws UnsupportedEncodingException {
        Map<String, ChatRecodeBean> chatRecodeBeanMap = new HashMap<>();
        Vector<ChatRecodeBean> newList = new Vector();
        for (ChatRecodeBean chatRecodeBean : list) {
            if (chatRecodeBeanMap.get(chatRecodeBean.getInfoId()) == null) {
                chatRecodeBean.setContent(new String(chatRecodeBean.getContent().getBytes("UTF-8"), "UTF-8"));
                chatRecodeBeanMap.put(chatRecodeBean.getInfoId(), chatRecodeBean);
            }
        }
        for (String key : chatRecodeBeanMap.keySet()) {
            newList.add(chatRecodeBeanMap.get(key));
        }
        return newList;
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
        int lineSize = 25;
        chatModel.getChildren().clear();
        chatRecord.getItems().clear();
        chatRecord.setPrefSize(400, 200);
        Vector<ChatRecodeBean> chatRecode = getChatRecode(1);
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
            String content = chatRecodeBean.getContent();
            int line = 0;
            if (content.length() % lineSize > 0) {
                line = (content.length() - content.length() % lineSize) / lineSize;
                line++;
            } else {
                line = content.length() / lineSize;
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < line; i++) {
                if (i != line - 1) {
                    sb.append(content.substring(i * lineSize, (i + 1) * lineSize) + "\n");
                } else {
                    sb.append(content.substring(i * lineSize, content.length()));
                }
            }
            msg += sb.toString();
            label.setText(msg);

            label.setPrefWidth(365);
            if (content.length() > lineSize) {
                label.setPrefHeight(50 + (line - 1) * 20);
            } else {
                label.setPrefHeight(50);
            }
            chatRecord.getItems().add(label);
        }
        chatRecord.scrollTo(chatRecord.getItems().size() - 1);
        chatModel.getChildren().add(chatRecord);
    }

    public void updateGroupChatRecord() {
        int lineSize = 15;
        chatModel.getChildren().clear();
        chatRecord.getItems().clear();
        chatRecord.setPrefSize(250, 200);
        Vector<ChatRecodeBean> chatRecode = getChatRecode(2);
        for (ChatRecodeBean chatRecodeBean : chatRecode) {
            String msg;
            final Label label = new Label();
            if (chatRecodeBean.getFromId().equals(userInfoCache.getUserId())) {
                msg = userInfoCache.getTel() + " " + formatDateTime(chatRecodeBean.getSendTime()) + "\n";
                label.setAlignment(Pos.CENTER_RIGHT);
            } else {
                msg = AllUserInfoCache.getInstance().getUserMap().get(chatRecodeBean.getFromId()).getLong("tel") + " " + formatDateTime(chatRecodeBean.getSendTime()) + "\n";
                label.setAlignment(Pos.CENTER_LEFT);
            }
            String content = chatRecodeBean.getContent();
            int line = 0;
            if (content.length() % lineSize > 0) {
                line = (content.length() - content.length() % lineSize) / lineSize;
                line++;
            } else {
                line = content.length() / lineSize;
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < line; i++) {
                if (i != line - 1) {
                    sb.append(content.substring(i * lineSize, (i + 1) * lineSize) + "\n");
                } else {
                    sb.append(content.substring(i * lineSize, content.length()));
                }
            }
            msg += sb.toString();
            label.setText(msg);

            label.setPrefWidth(215);
            if (content.length() > lineSize) {
                label.setPrefHeight(50 + (line - 1) * 20);
            } else {
                label.setPrefHeight(50);
            }
            chatRecord.getItems().add(label);
        }
        chatRecord.scrollTo(chatRecord.getItems().size() - 1);
        ListView listView = new ListView();
        listView.setPrefSize(150,200);
        listView.setLayoutX(250);
        listView.setPadding(new Insets(0));
        String params = "id=" + chatWindowCache.getToId();
        String post = HttpUtil.sendPost(ApiUrlManager.get_group_friend_list() + "?" + params, "");
        JSONObject jsonObject = JSONObject.parseObject(post);
        if (jsonObject.getString("result") != null && jsonObject.getString("result").equals("200")) {
            List<GoodFriendBean> goodFriendBeans = parseGoodFriendBean((List<JSONObject>)(jsonObject.get("data")));
            for (final GoodFriendBean goodFriendBean : goodFriendBeans) {
                Label label = new Label();
                label.setText((goodFriendBean.getName() != null ? (goodFriendBean.getName() + "\n") : "") + goodFriendBean.getTel());
                label.setPrefSize(120, 50);
                label.setStyle("-fx-background-color:#7EACE6");
                label.setTextFill(Paint.valueOf("WHITE"));
                label.setPadding(new Insets(0));
                if(goodFriendBean.getId().longValue() != userInfoCache.getUserId()){
                    label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            chatUserTop.getChildren().clear();
                            inputAndSend.setVisible(true);
                            chatMessageCache.getUserNoReceiveSingerMsgMap().put(goodFriendBean.getId(), 0);
                            chatWindowCache.setFromId(userInfoCache.getUserId());
                            chatWindowCache.setToId(goodFriendBean.getId());
                            chatWindowCache.setName(goodFriendBean.getName());
                            chatWindowCache.setTel(goodFriendBean.getTel());
                            Label lab = new Label();
                            lab.setText((goodFriendBean.getName() != null ? (goodFriendBean.getName() + "\n") : "") + goodFriendBean.getTel());
                            lab.setPrefWidth(350);
                            lab.setPrefHeight(50);
                            lab.setPadding(new Insets(5));
                            chatUserTop.getChildren().add(lab);
                            showMyUser();
                            updateChatRecord();
                        }
                    });
                }
                label.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        label.setStyle("-fx-background-color:#7EACE6");
                        label.setTextFill(Paint.valueOf("WHITE"));
                    }
                });
                label.setOnMouseMoved(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        label.setStyle("-fx-background-color:#ffffff");
                        label.setTextFill(Paint.valueOf("#7EACE6"));
                    }
                });
                listView.getItems().add(label);
            }
        }
        chatModel.getChildren().add(chatRecord);
        chatModel.getChildren().add(listView);
    }

    private String formatDateTime(long dateTime) {
        String format = sdf.format(new Date(dateTime));
        return format;
    }

    public void updateMessageList() {

    }

}
