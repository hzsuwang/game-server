package com.iterror.game.gateway.tcp.bto.xip;

import com.iterror.game.common.tcp.msg.BaseRespMsg;
import com.iterror.game.common.tcp.protocol.SignalCode;
import com.iterror.game.gateway.tcp.message.MessageCode;

@SignalCode(messageCode = MessageCode.MSGCODE_AUTH_RESP)
public class AuthResp extends BaseRespMsg {

    /**
     * 
     */
    private static final long serialVersionUID = 8492672670792437176L;

    @Override
    public int getMsgCode() {
        SignalCode attr = this.getClass().getAnnotation(SignalCode.class);
        return attr.messageCode();
    }
}
