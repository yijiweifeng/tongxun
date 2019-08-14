package sample.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/10
 * Time: 16:19
 * Description: No Description
 */
public class MyChatClient {
    private static EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    private static Bootstrap bootstrap = new Bootstrap();
    private ChannelFuture sync;
    private Channel channel;
    private static MyChatClient myChatClient = new MyChatClient();
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    public static boolean isConnect = false;

    static {
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.ALLOW_HALF_CLOSURE, true);
        bootstrap.handler(new MyChatClientInitializer());
    }

    public static MyChatClient getMyChatClient(){
        return myChatClient;
    }

    public Channel doConnect() throws Exception {
        testClientRun();
        return channel;
    }

    private Channel connect(Bootstrap bootstrap) throws InterruptedException {
        System.out.println("connect...");
        isConnect = true;
        boolean isFail = false;
        try {
            sync = bootstrap.connect("192.168.9.64", 8899).sync();
        } catch (Exception e) {
            isFail = true;
        }
        while (isFail) {
            Thread.sleep(3000);
            isFail = false;
            System.out.println("now reconnect ");
            try {
                sync = bootstrap.connect("192.168.9.64", 8899).sync();
            } catch (Exception e) {
                isFail = true;
            }
        }

        System.out.println("connect success");
        isConnect = false;
        return sync.channel();
    }

    public void testClientRun() throws Exception {
        System.out.println("resertConnect");
            channel = connect(bootstrap);
//            eventLoopGroup.shutdownGracefully();
    }

    public void sendMsg(String msg){
        sync.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                // 检查操作的状态
                if (channelFuture.isSuccess()) {

                } else {
                    // 如果发生错误，则访问描述原因的Throwable

                }
                System.out.println(channelFuture.isSuccess());
            }
        });
        ChannelFuture channelFuture = channel.writeAndFlush(msg + "\r\n");
    }

}
