package sample.client.Handler;

import org.apache.log4j.Logger;
import sample.client.bean.AppMessage;
import sample.client.bean.SingleMessage;
import sample.client.event.CEventCenter;
import sample.client.event.Events;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/20
 * Time: 9:20
 * Description: No Description
 */
public class SingleChatNotMessageHandler extends AbstractMessageHandler {

    private static final String TAG = SingleChatNotMessageHandler.class.getSimpleName();

    private static Logger log = Logger.getRootLogger();

    @Override
    protected void action(AppMessage message) {
        log.info(TAG+ " " + "收到单聊离线消息，message=" + message);
        SingleMessage msg = new SingleMessage();
        msg.setMsgId(message.getHead().getMsgId());
        msg.setMsgType(message.getHead().getMsgType());
        msg.setMsgContentType(message.getHead().getMsgContentType());
        msg.setFromId(message.getHead().getFromId());
        msg.setToId(message.getHead().getToId());
        msg.setTimestamp(message.getHead().getTimestamp());
        msg.setExtend(message.getHead().getExtend());
        msg.setContent(message.getBody());
        CEventCenter.dispatchEvent(Events.CHAT_SINGLE_MESSAGE_NOT, 0, 0, msg);
    }

}
