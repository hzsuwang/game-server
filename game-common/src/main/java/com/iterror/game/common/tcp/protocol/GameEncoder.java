package com.iterror.game.common.tcp.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldPrepender;

import java.nio.ByteOrder;
import java.util.List;

/**
 * Created by tony.yan on 2017/9/3.
 */
public class GameEncoder extends LengthFieldPrepender {

    public GameEncoder(int lengthFieldLength) {
        super(lengthFieldLength);
    }

    public GameEncoder(int lengthFieldLength, boolean lengthIncludesLengthFieldLength) {
        super(lengthFieldLength, lengthIncludesLengthFieldLength);
    }

    public GameEncoder(int lengthFieldLength, int lengthAdjustment) {
        super(lengthFieldLength, lengthAdjustment);
    }

    public GameEncoder(int lengthFieldLength, int lengthAdjustment, boolean lengthIncludesLengthFieldLength) {
        super(lengthFieldLength, lengthAdjustment, lengthIncludesLengthFieldLength);
    }

    public GameEncoder(ByteOrder byteOrder, int lengthFieldLength, int lengthAdjustment, boolean lengthIncludesLengthFieldLength) {
        super(byteOrder, lengthFieldLength, lengthAdjustment, lengthIncludesLengthFieldLength);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        super.encode(ctx, msg, out);
    }
}
