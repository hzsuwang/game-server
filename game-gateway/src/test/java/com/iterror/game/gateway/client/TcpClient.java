package com.iterror.game.gateway.client;

import com.alibaba.fastjson.JSONObject;
import com.iterror.game.common.zookeeper.ZkConfig;
import com.iterror.game.gateway.tcp.bto.xip.AuthReq;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tony.yan on 2017/9/1.
 */
public class TcpClient {

    private static final Logger logger    = LoggerFactory.getLogger(TcpClient.class);
    static {
        System.setProperty("io.netty.noUnsafe","true");
    }
    public static String        HOST      = "127.0.0.1";
    public static int           PORT      = 9000;


    public static Bootstrap     bootstrap = getBootstrap();
    public static Channel       channel   = getChannel(HOST, PORT);

    /**
     * 初始化Bootstrap
     * 
     * @return
     */
    public static final Bootstrap getBootstrap() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class);
        b.handler(new ChannelInitializer<Channel>() {

            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                pipeline.addLast("handler", new TcpClientHandler());
            }
        });
        b.option(ChannelOption.SO_KEEPALIVE, true);
        return b;
    }

    public static final Channel getChannel(String host, int port) {
        Channel channel = null;
        try {
            channel = bootstrap.connect(host, port).sync().channel();
        } catch (Exception e) {
            logger.error(String.format("连接Server(IP[%s],PORT[%s])失败", host, port), e);
            return null;
        }
        return channel;
    }

    public static void sendMsg(String msg) throws Exception {
        if (channel != null) {
            channel.writeAndFlush(msg).sync();
        } else {
            logger.warn("消息发送失败,连接尚未建立!");
        }
    }

    public static void main(String[] args) throws Exception {
        try {

            long t0 = System.nanoTime();
            //for (int i = 0; i < 100000; i++) {
             //   TcpClient.sendMsg(i + "你好1");
            //}
            AuthReq req = new AuthReq();
            req.setDid("aaaa");
            req.setSid("bbbb");
            req.setMsgId(123);
            JSONObject messageJson = (JSONObject) JSONObject.toJSON(req);
            String zkUrl = "127.0.0.1:2181";
            ZkConfig zkConfig = new ZkConfig();
            zkConfig.setAddress(HOST+":"+PORT);
            zkConfig.setGroupNode("game");
            zkConfig.setSubNode("game-gateway");
            zkConfig.setZkSessionTimeout(20000);
            zkConfig.setZkurl(zkUrl);
            ZkServer zkServer = new ZkServer(zkConfig);
            String url = zkServer.getRandomNode();
            logger.info("node url="+url);
            TcpClient.sendMsg(messageJson.toJSONString());
            long t1 = System.nanoTime();
            System.out.println((t1 - t0) / 1000000.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
