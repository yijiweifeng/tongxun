package sample.client.listener.impl;

import javafx.application.Platform;
import sample.client.bean.GroupByMessage;
import sample.client.bean.SingleMessage;
import sample.client.cache.ChatMessageCache;
import sample.client.cache.ChatWindowCache;
import sample.client.event.I_CEventListener;
import sample.controller.WindowController;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/20
 * Time: 10:02
 * Description: No Description
 */
public class IMSChatGroupMessageListener implements I_CEventListener {

    private WindowController windowController = WindowController.windowController;

    public ChatMessageCache chatMessageCache = ChatMessageCache.getInstance();

    private ChatWindowCache chatWindowCache = ChatWindowCache.getInstance();


    @Override
    public void onCEvent(String topic, int msgCode, int resultCode, Object obj) {
        GroupByMessage singleMessage = (GroupByMessage) obj;
        if (chatMessageCache.getUserNoReceiveGroupMsgMap().get(Long.valueOf(singleMessage.getGroupId())) == null) {
            chatMessageCache.getUserNoReceiveGroupMsgMap().put(Long.valueOf(singleMessage.getGroupId()), 0);
        }
        chatMessageCache.getUserNoReceiveGroupMsgMap().put(Long.valueOf(singleMessage.getGroupId()),
                chatMessageCache.getUserNoReceiveGroupMsgMap().get(Long.valueOf(singleMessage.getGroupId())) + 1);
        if((chatWindowCache != null && chatWindowCache.getToId() != null)
                && !chatWindowCache.getToId().equals(Long.valueOf(singleMessage.getGroupId())) && chatWindowCache.isGroup()){
            chatMessageCache.getUserNoReceiveGroupMsgMap().put(Long.valueOf(singleMessage.getGroupId()), 0);
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //更新JavaFX的主线程的代码放在此处
                if(windowController.isShowGroup()){
                    windowController.showMyGroup();
                }
                if(chatWindowCache.isGroup()){
                    windowController.updateGroupChatRecord();
                }
            }
        });
    }

}