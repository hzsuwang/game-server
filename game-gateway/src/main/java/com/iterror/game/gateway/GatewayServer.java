package com.iterror.game.gateway;

import com.iterror.game.common.tcp.connection.NetConfig;
import com.iterror.game.common.tcp.protocol.DefaultTypeMetainfo;
import com.iterror.game.common.tcp.protocol.GameDecoder;
import com.iterror.game.common.tcp.protocol.GameEncoder;
import com.iterror.game.common.tcp.protocol.HeartbeatHandler;
import com.iterror.game.gateway.tcp.handler.TcpServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by tony.yan on 2017/8/31.
 */
public class GatewayServer {

    private static final Logger         logger        = LoggerFactory.getLogger(GatewayServer.class);

    @Autowired
    private TcpServerHandler            tcpServerHandler;

    @Autowired
    private GameDecoder                 gameDecoder;

    @Autowired
    private GameEncoder gameEncoder;

    @Autowired
    private HeartbeatHandler            heartbeatHandler;

    @Autowired
    private NetConfig netConfig;

    protected void start() {

        EventLoopGroup bossGroup     = new NioEventLoopGroup(netConfig.getBossGroupSize());
        EventLoopGroup workerGroup   = new NioEventLoopGroup(netConfig.getWorkerGroupSize());

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup);
        b.channel(NioServerSocketChannel.class);
        b.childHandler(new ChannelInitializer<SocketChannel>() {

            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("ping", heartbeatHandler);
                pipeline.addLast("frameDecoder", gameDecoder);
                pipeline.addLast("frameEncoder", gameEncoder);
                pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                pipeline.addLast(tcpServerHandler);
            }
        });

        try {
            b.bind(netConfig.getIp(), netConfig.getPort()).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("TCP服务器已启动");
    }
}
