package com.iterror.game.gateway.tcp.bto.xip;


import com.iterror.game.common.tcp.msg.BaseRespMsg;
import com.iterror.game.common.tcp.protocol.SignalCode;
import com.iterror.game.gateway.tcp.message.MessageCode;

@SignalCode(messageCode = MessageCode.MSGCODE_HEARTBEAT_RESP)
public class HeartbeatResp extends BaseRespMsg {

    /**
     * 
     */
    private static final long serialVersionUID = 6436942922114018444L;
    @Override
    public int getMsgCode() {
        SignalCode attr = this.getClass().getAnnotation(SignalCode.class);
        return attr.messageCode();
    }
}
