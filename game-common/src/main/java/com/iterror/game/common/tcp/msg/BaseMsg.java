package com.iterror.game.common.tcp.msg;

/**
 * Created by tony.yan on 2017/9/2.
 */
public abstract class BaseMsg implements java.io.Serializable {
    public static final String msgCodeKey = "msg_code";

    protected long             msgId;                                  // 消息id
    protected String           channelId;                              // 连接通道id
    protected long             msgUid;                                 // 用户id
    protected int              retryCount;                             // 重试次数


    public long getMsgUid() {
        return msgUid;
    }

    public void setMsgUid(long msgUid) {
        this.msgUid = msgUid;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int incRetryCount() {
        return retryCount++;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }
    public abstract int getMsgCode();
}
