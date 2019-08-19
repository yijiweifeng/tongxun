package sample.client.listener.impl;

import com.alibaba.fastjson.JSONObject;
import javafx.application.Platform;
import sample.client.bean.SingleMessage;
import sample.client.cache.ChatMessageCache;
import sample.client.cache.UserInfoCache;
import sample.client.event.I_CEventListener;
import sample.client.utils.ApiUrlManager;
import sample.client.utils.HttpUtil;
import sample.controller.WindowController;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/19
 * Time: 10:08
 * Description: No Description
 */
public class IMSChatSingleMessageListener implements I_CEventListener {

    private WindowController windowController = WindowController.windowController;

    public ChatMessageCache chatMessageCache = ChatMessageCache.getInstance();

    @Override
    public void onCEvent(String topic, int msgCode, int resultCode, Object obj) {
        SingleMessage singleMessage = (SingleMessage) obj;
        if (chatMessageCache.getUserNoReceiveSingerMsgMap().get(Long.valueOf(singleMessage.getFromId())) == null) {
            chatMessageCache.getUserNoReceiveSingerMsgMap().put(Long.valueOf(singleMessage.getFromId()), 0);
        }
        chatMessageCache.getUserNoReceiveSingerMsgMap().put(Long.valueOf(singleMessage.getFromId()),
                chatMessageCache.getUserNoReceiveSingerMsgMap().get(Long.valueOf(singleMessage.getFromId())) + 1);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //更新JavaFX的主线程的代码放在此处
                windowController.updateMessageList();
                windowController.updateChatRecord();
            }
        });
    }

}
