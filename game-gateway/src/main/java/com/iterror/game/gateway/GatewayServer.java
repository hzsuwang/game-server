package com.iterror.game.gateway;

import com.iterror.game.gateway.handler.ServerHandler;
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

import java.util.concurrent.TimeUnit;

/**
 * Created by tony.yan on 2017/8/31.
 */
public class GatewayServer {

    private static final Logger         logger        = LoggerFactory.getLogger(GatewayServer.class);
    private static final String         IP            = "127.0.0.1";
    private static final int            PORT          = 9999;

    /** 用于分配处理业务线程的线程组个数 */
    protected static final int          BIZGROUPSIZE  = Runtime.getRuntime().availableProcessors() * 2; // 默认

    /** 业务出现线程大小 */
    protected static final int          BIZTHREADSIZE = 4;
    /*
     * NioEventLoopGroup实际上就是个线程池, NioEventLoopGroup在后台启动了n个NioEventLoop来处理Channel事件, 每一个NioEventLoop负责处理m个Channel,
     * NioEventLoopGroup从NioEventLoop数组里挨个取出NioEventLoop来处理Channel
     */
    private static final EventLoopGroup bossGroup     = new NioEventLoopGroup(BIZGROUPSIZE);
    private static final EventLoopGroup workerGroup   = new NioEventLoopGroup(BIZTHREADSIZE);

    protected static void shutdown() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    protected static void start(){
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup);
        b.channel(NioServerSocketChannel.class);
        b.childHandler(new ChannelInitializer<SocketChannel>() {

            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("ping", new IdleStateHandler(25, 15, 10, TimeUnit.SECONDS));
                pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                pipeline.addLast(new ServerHandler());
            }
        });

        try {
            b.bind(IP, PORT).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("TCP服务器已启动");
    }
}