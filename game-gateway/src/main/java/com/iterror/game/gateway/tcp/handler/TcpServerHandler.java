package com.iterror.game.gateway.tcp.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.iterror.game.common.tcp.connection.MyConnection;
import com.iterror.game.common.tcp.connection.MyConnectionListener;
import com.iterror.game.common.tcp.msg.BaseMsg;
import com.iterror.game.common.tcp.protocol.DefaultTypeMetainfo;
import com.iterror.game.common.util.SjsonUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * Created by tony.yan on 2017/8/31.
 */

public class TcpServerHandler extends SimpleChannelInboundHandler<BaseMsg> {

    private static final Logger logger = LoggerFactory.getLogger(TcpServerHandler.class);

    @Autowired
    private DefaultTypeMetainfo typeMetaInfo;

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BaseMsg baseMsg) throws Exception {
        logger.info("client req msg=" + baseMsg);
        //channelHandlerContext.channel().writeAndFlush("yes, server is accepted you ,nice !"+msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        logger.info("与服务端断开连接 id="+ctx.channel().id());
        MyConnectionListener.connectionDestroyed(ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("Unexpected exception from downstream.", cause);
        MyConnectionListener.connectionDestroyed(ctx.channel().id().asLongText());

    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        logger.info("channelRegistered...id=" + ctx.channel().id());
        MyConnection myConnection = new MyConnection(ctx.channel());
        MyConnectionListener.connectionCreated(myConnection);
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
                MyConnectionListener.connectionDestroyed(ctx.channel().id().asLongText());

            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                //未进行写操作
                System.out.println("WRITER_IDLE");
                // 超时关闭channel
                MyConnectionListener.connectionDestroyed(ctx.channel().id().asLongText());

            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                //未进行读写
                System.out.println("ALL_IDLE");
                // 发送心跳消息
                MyConnectionListener.connectionDestroyed(ctx.channel().id().asLongText());

            }

        }
    }
}
