package sample.client.Handler;

import org.apache.log4j.Logger;
import sample.client.bean.AppMessage;
import sample.client.bean.GroupByMessage;
import sample.client.event.CEventCenter;
import sample.client.event.Events;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       GroupChatMessageHandler.java</p>
 * <p>@PackageName:     com.freddy.chat.im.handler</p>
 * <b>
 * <p>@Description:     类描述</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/04/10 03:43</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public class GroupChatMessageHandler extends AbstractMessageHandler {

    private static final String TAG = GroupChatMessageHandler.class.getSimpleName();

    private static Logger logger = Logger.getRootLogger();

    @Override
    protected void action(AppMessage message) {
        logger.info(TAG + " " +  "收到群聊消息，message=" + message);
        GroupByMessage msg = new GroupByMessage();
        msg.setMsgId(message.getHead().getMsgId());
        msg.setMsgType(message.getHead().getMsgType());
        msg.setMsgContentType(message.getHead().getMsgContentType());
        msg.setFromId(message.getHead().getFromId().split("-")[0]);
        msg.setToId(message.getHead().getToId());
        msg.setTimestamp(message.getHead().getTimestamp());
        msg.setExtend(message.getHead().getExtend());
        msg.setContent(message.getBody());
        msg.setGroupId(message.getHead().getFromId().split("-")[1]);
        CEventCenter.dispatchEvent(Events.CHAT_GROUP_MESSAGE, 0, 0, msg);
    }
}
