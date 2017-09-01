package com.iterror.game.gateway.tcp.bto.xip;

import com.iterror.game.common.tcp.msg.BaseRespMsg;
import com.iterror.game.common.tcp.protocol.SignalCode;
import com.iterror.game.gateway.tcp.message.MessageCode;

@SignalCode(messageCode = MessageCode.MSGCODE_RETURN_MSG_RESP)
public class ReturnMsgResp extends BaseRespMsg {

    /**
     * 
     */
    private static final long serialVersionUID = 6816209320636038711L;

    @Override
    public int getMsgCode() {
        SignalCode attr = this.getClass().getAnnotation(SignalCode.class);
        return attr.messageCode();
    }
}
