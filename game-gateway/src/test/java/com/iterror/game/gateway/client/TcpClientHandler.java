package com.iterror.game.gateway.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tony.yan on 2017/9/1.
 */
public class TcpClientHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger logger = LoggerFactory.getLogger(TcpClientHandler.class);

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        logger.info("client接收到服务器返回的消息:" + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        logger.info("active=" + ctx.channel().isActive());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        logger.info("exceptionCaught=", cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        logger.info("与服务器断开连接服务器");
    }
}
