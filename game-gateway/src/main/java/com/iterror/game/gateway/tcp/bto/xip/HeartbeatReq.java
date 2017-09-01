package com.iterror.game.gateway.tcp.bto.xip;

import com.iterror.game.common.tcp.msg.BaseReqMsg;
import com.iterror.game.common.tcp.protocol.SignalCode;
import com.iterror.game.gateway.tcp.message.MessageCode;

@SignalCode(messageCode = MessageCode.MSGCODE_HEARTBEAT_REQ)
public class HeartbeatReq extends BaseReqMsg {

    /**
     * 
     */
    private static final long serialVersionUID = 5958944770884933632L;

    @Override
    public int getMsgCode() {
        SignalCode attr = this.getClass().getAnnotation(SignalCode.class);
        return attr.messageCode();
    }
}
