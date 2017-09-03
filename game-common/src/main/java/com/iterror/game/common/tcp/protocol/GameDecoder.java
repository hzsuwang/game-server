package com.iterror.game.common.tcp.protocol;

import com.alibaba.fastjson.JSONObject;
import com.iterror.game.common.tcp.connection.MyConnection;
import com.iterror.game.common.tcp.connection.MyConnectionListener;
import com.iterror.game.common.tcp.msg.BaseMsg;
import com.iterror.game.common.util.SjsonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteOrder;

/**
 * Created by tony.yan on 2017/9/3.
 */
public class GameDecoder extends LengthFieldBasedFrameDecoder{

    private static final Logger logger        = LoggerFactory.getLogger(GameDecoder.class);

    private DefaultTypeMetainfo defaultTypeMetainfo;

    public GameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,DefaultTypeMetainfo defaultTypeMetainfo) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
        this.defaultTypeMetainfo = defaultTypeMetainfo;
    }

    public GameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip,DefaultTypeMetainfo defaultTypeMetainfo) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
        this.defaultTypeMetainfo = defaultTypeMetainfo;
    }

    public GameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast,DefaultTypeMetainfo defaultTypeMetainfo) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
        this.defaultTypeMetainfo = defaultTypeMetainfo;
    }

    public GameDecoder(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast,DefaultTypeMetainfo defaultTypeMetainfo) {
        super(byteOrder, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
        this.defaultTypeMetainfo = defaultTypeMetainfo;
    }

    @Override
    protected BaseMsg decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf msgIn = (ByteBuf) super.decode(ctx, in);
        byte[] byteArray = new byte[msgIn.capacity()];
        msgIn.readBytes(byteArray);
        String msg = new String(byteArray);
        int msgCode = SjsonUtil.getMsgCodeFromMsg(msg);
        Class<?> classObj = defaultTypeMetainfo.find(msgCode);
        if (classObj == null) {
            logger.error("msgCode not find     msgCode=" + msgCode);
            return null;
        }
        BaseMsg baseMsg= (BaseMsg) JSONObject.parseObject(msg, classObj);
        String cid = ctx.channel().id().asLongText();
        MyConnection myConnection = MyConnectionListener.getMyConnectionBySocketId(cid);
        baseMsg.setChannelId(cid);
        baseMsg.setMsgUid(myConnection.getUid());
        return baseMsg;
    }
}
