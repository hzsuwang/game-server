package com.iterror.game.gateway.handler;

import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by tony.yan on 2017/8/31.
 */
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        logger.info("SERVER接收到消息:"+msg);
        channelHandlerContext.channel().writeAndFlush("yes, server is accepted you ,nice !"+msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("Unexpected exception from downstream.", cause);
        ctx.close();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {

            IdleStateEvent event = (IdleStateEvent) evt;

            if (event.state().equals(IdleState.READER_IDLE)) {
                //未进行读操作
                System.out.println("READER_IDLE");
                // 超时关闭channel
                ctx.close();

            } else if (event.state().equals(IdleState.WRITER_IDLE)) {


            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                //未进行读写
                System.out.println("ALL_IDLE");
                // 发送心跳消息


            }

        }
    }
}
