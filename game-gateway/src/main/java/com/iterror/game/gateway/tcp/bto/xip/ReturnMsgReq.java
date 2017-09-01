package com.iterror.game.gateway.tcp.bto.xip;


import com.iterror.game.common.tcp.msg.BaseReqMsg;
import com.iterror.game.common.tcp.protocol.SignalCode;
import com.iterror.game.gateway.tcp.message.MessageCode;

@SignalCode(messageCode = MessageCode.MSGCODE_RETURN_MSG_REQ)
public class ReturnMsgReq extends BaseReqMsg {

    /**
     * 
     */
    private static final long serialVersionUID = 1015732735538013454L;

    private long              returnMsgId;

    public long getReturnMsgId() {
        return returnMsgId;
    }

    public void setReturnMsgId(long returnMsgId) {
        this.returnMsgId = returnMsgId;
    }
    @Override
    public int getMsgCode() {
        SignalCode attr = this.getClass().getAnnotation(SignalCode.class);
        return attr.messageCode();
    }
}
