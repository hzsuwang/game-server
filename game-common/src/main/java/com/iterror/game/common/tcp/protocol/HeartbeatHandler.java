package com.iterror.game.common.tcp.protocol;

import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by tony.yan on 2017/9/3.
 */
public class HeartbeatHandler extends IdleStateHandler {

    public HeartbeatHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
        super(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
    }

    public HeartbeatHandler(long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit) {
        super(readerIdleTime, writerIdleTime, allIdleTime, unit);
    }

    public HeartbeatHandler(boolean observeOutput, long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit) {
        super(observeOutput, readerIdleTime, writerIdleTime, allIdleTime, unit);
    }
}
