package sample.client.listener.impl;

import sample.client.listener.OnEventListener;
import sample.client.protobuf.MessageProtobuf;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/14
 * Time: 10:49
 * Description: No Description
 */
public class OnEventListenerImpl implements OnEventListener {

    @Override
    public void dispatchMsg(MessageProtobuf.Msg msg) {

    }

    @Override
    public boolean isNetworkAvailable() {
        return false;
    }

    @Override
    public int getReconnectInterval() {
        return 0;
    }

    @Override
    public int getConnectTimeout() {
        return 0;
    }

    @Override
    public int getForegroundHeartbeatInterval() {
        return 0;
    }

    @Override
    public int getBackgroundHeartbeatInterval() {
        return 0;
    }

    @Override
    public MessageProtobuf.Msg getHandshakeMsg() {
        return null;
    }

    @Override
    public MessageProtobuf.Msg getHeartbeatMsg() {
        return null;
    }

    @Override
    public int getServerSentReportMsgType() {
        return 0;
    }

    @Override
    public int getClientReceivedReportMsgType() {
        return 0;
    }

    @Override
    public int getResendCount() {
        return 0;
    }

    @Override
    public int getResendInterval() {
        return 0;
    }

}
