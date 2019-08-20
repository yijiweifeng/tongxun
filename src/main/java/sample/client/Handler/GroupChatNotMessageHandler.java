package sample.client.Handler;

import org.apache.log4j.Logger;
import sample.client.bean.AppMessage;

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
    }

}
