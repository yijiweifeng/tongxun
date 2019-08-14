package sample.client.Handler;

import org.apache.log4j.Logger;
import sample.client.bean.AppMessage;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       ServerReportMessageHandler.java</p>
 * <p>@PackageName:     com.freddy.chat.im.handler</p>
 * <b>
 * <p>@Description:     服务端返回的消息发送状态报告</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/04/22 19:16</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public class ServerReportMessageHandler extends AbstractMessageHandler {

    private static final String TAG = ServerReportMessageHandler.class.getSimpleName();
    private static Logger logger = Logger.getRootLogger();
    @Override
    protected void action(AppMessage message) {
        logger.info(TAG + " " + "收到消息状态报告，message=" + message);
    }
}
