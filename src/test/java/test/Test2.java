package test;

import sample.Controller;
import sample.client.IMSClientBootstrap;
import sample.client.MessageProcessor;
import sample.client.MessageType;
import sample.client.NettyTcpClient;
import sample.client.bean.SingleMessage;
import sample.client.event.CEventCenter;
import sample.client.event.Events;
import sample.client.event.I_CEventListener;
import sample.client.protobuf.MessageProtobuf;
import sample.client.utils.CThreadPoolExecutor;
import sample.client.utils.MessageBuilder;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/14
 * Time: 16:02
 * Description: No Description
 */
public class Test2 {

    public static void main(String[] args) {
        String token = "token_100001";
        String hosts = "[{\"host\":\"192.168.9.64\", \"port\":8855}]";
        IMSClientBootstrap.getInstance().init("100001", token, hosts, 1);
        SingleMessage message = new SingleMessage();
        message.setMsgId(UUID.randomUUID().toString());
        message.setMsgType(MessageType.SINGLE_CHAT.getMsgType());
        message.setMsgContentType(MessageType.MessageContentType.TEXT.getMsgContentType());
        message.setFromId("100001");
        message.setToId("100002");
        message.setTimestamp(System.currentTimeMillis());
        message.setContent("2222");
        MessageProtobuf.Msg build = MessageBuilder.getProtoBufMessageBuilderByAppMessage(MessageBuilder.buildAppMessage(message)).build();
        NettyTcpClient.getInstance().sendMsg(build);
    }

}
