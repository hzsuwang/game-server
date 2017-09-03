package com.iterror.game.common.tcp.msg;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.iterror.game.common.tcp.protocol.SignalCode;

/**
 * Created by tony.yan on 2017/9/2.
 */
public abstract class BaseMsg implements java.io.Serializable {
    public static final String msgCodeKey = "msgCode";

    protected long             msgId;                                  // 消息id
    protected String           channelId;                              // 连接通道id
    protected long             msgUid;                                 // 用户id


    public long getMsgUid() {
        return msgUid;
    }

    public void setMsgUid(long msgUid) {
        this.msgUid = msgUid;
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

    public int getMsgCode(){
        SignalCode attr = this.getClass().getAnnotation(SignalCode.class);
        return attr.messageCode();
    }

    @Override
    public String  toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
