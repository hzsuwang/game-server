package com.iterror.game.common.tcp.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * Created by tony.yan on 2017/9/1.
 */
public class MyConnection {

    private static final Logger logger     = LoggerFactory.getLogger(MyConnection.class);

    private boolean             valid      = false;                                      // 连接无效
    private String              sid;                                                     // sessionid
    private Channel             channel;                                                 // 通道
    private boolean             isReplaced = false;                                      // 是否是其它地址上线，导致该连接被踢下的。是的话，不要向事件服务器发送下线消息


    private long uid;
    private String              name;
    private boolean             closed;

    public MyConnection(final Channel channel){
        this.channel = channel;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public synchronized void close() {
        if (closed) {
            return;
        }
        ChannelFuture closeFuture = channel.close();

        if (!closeFuture.awaitUninterruptibly(10000)) {
            logger.warn("Timed out waiting for channel to close");
        }
        closed = true;
    }

    public String getRemoteAddress() {
        return channel.remoteAddress().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return channel.id().asLongText();
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Channel getChannel() {
        return channel;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        synchronized (this) {
            this.valid = valid;
        }
    }

    /**
     * 是否是其它地址上线，导致该连接被踢下的。是的话，不要向事件服务器发送下线消息
     *
     * @return
     */
    public boolean isReplaced() {
        return isReplaced;
    }

    public void setReplaced(boolean isReplaced) {
        this.isReplaced = isReplaced;
    }
}
