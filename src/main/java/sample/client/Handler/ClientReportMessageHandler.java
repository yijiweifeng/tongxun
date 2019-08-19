package sample.client.Handler;

import org.apache.log4j.Logger;
import sample.client.MsgTimeoutTimerManager;
import sample.client.NettyTcpClient;
import sample.client.bean.AppMessage;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/16
 * Time: 17:26
 * Description: No Description
 */
public class ClientReportMessageHandler extends AbstractMessageHandler {

    private static final String TAG = ClientReportMessageHandler.class.getSimpleName();
    private static Logger logger = Logger.getRootLogger();

    @Override
    protected void action(AppMessage message) {
        logger.info(TAG + " " + "客户端提交的消息接收状态报告，message=" + message);
    }

}
