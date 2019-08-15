package sample;

import sample.client.IMSClientBootstrap;
import sample.client.MessageType;
import sample.client.NettyTcpClient;
import sample.client.bean.SingleMessage;
import sample.client.protobuf.MessageProtobuf;
import sample.client.utils.MessageBuilder;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/14
 * Time: 16:19
 * Description: No Description
 */
public class Demo {

    public static void send(String fromID,String toId,String msg){
        String token = "token_" + fromID;
        String hosts = "[{\"host\":\"192.168.9.64\", \"port\":8855}]";
        IMSClientBootstrap.getInstance().init(fromID, token, hosts, 1);
        SingleMessage message = new SingleMessage();
        message.setMsgId(UUID.randomUUID().toString());
        message.setMsgType(MessageType.SINGLE_CHAT.getMsgType());
        message.setMsgContentType(MessageType.MessageContentType.TEXT.getMsgContentType());
        message.setFromId(fromID);
        message.setToId(toId);
        message.setTimestamp(System.currentTimeMillis());
        message.setContent(msg);
        MessageProtobuf.Msg build = MessageBuilder.getProtoBufMessageBuilderByAppMessage(MessageBuilder.buildAppMessage(message)).build();
        NettyTcpClient.getInstance().sendMsg(build);
    }

}
