package sample.client.Handler;

import javafx.application.Platform;
import org.apache.log4j.Logger;
import sample.client.bean.AppMessage;
import sample.client.bean.GroupByMessage;
import sample.client.bean.SingleMessage;
import sample.client.cache.ChatWindowCache;
import sample.client.event.CEventCenter;
import sample.client.event.Events;
import sample.controller.WindowController;

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
        if(message.getHead().getStatusReport() == 1){
            if(ChatWindowCache.getInstance().getTel().longValue() == 0){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        //更新JavaFX的主线程的代码放在此处
                        WindowController.windowController.updateGroupChatRecord();
                    }
                });
            }else{
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        //更新JavaFX的主线程的代码放在此处
                        WindowController.windowController.updateChatRecord();
                    }
                });
            }
        }

    }
}
