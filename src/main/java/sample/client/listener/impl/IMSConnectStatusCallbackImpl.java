package sample.client.listener.impl;

import sample.client.NettyTcpClient;
import sample.client.listener.IMSConnectStatusCallback;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/14
 * Time: 10:49
 * Description: No Description
 */
public class IMSConnectStatusCallbackImpl implements IMSConnectStatusCallback {

    private NettyTcpClient client = NettyTcpClient.getInstance();

    @Override
    public void onConnecting() {
    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onConnectFailed() {

    }

}
