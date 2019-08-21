package sample.client.Handler;

import org.apache.log4j.Logger;
import sample.client.bean.AppMessage;
import sample.client.bean.GroupByMessage;
import sample.client.event.CEventCenter;
import sample.client.event.Events;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/20
 * Time: 9:22
 * Description: No Description
 */
public class GroupChatNotMessageHandler extends AbstractMessageHandler {

    private static final String TAG = GroupChatNotMessageHandler.class.getSimpleName();

    private static Logger logger = Logger.getRootLogger();

    @Override
    protected void action(AppMessage message) {
        logger.info(TAG + " " +  "收到群聊离线消息，message=" + message);
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
        CEventCenter.dispatchEvent(Events.CHAT_GROUP_MESSAGE_NOT, 0, 0, msg);
    }

}
