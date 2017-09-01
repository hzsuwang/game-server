package com.iterror.game.gateway.tcp.bto.xip;


import com.iterror.game.common.tcp.msg.BaseNotifyMsg;
import com.iterror.game.common.tcp.protocol.SignalCode;
import com.iterror.game.gateway.tcp.message.MessageCode;

@SignalCode(messageCode = MessageCode.MSGCODE_EXIT_NOTIFY)
public class ExitNotify extends BaseNotifyMsg {

    /**
     * 
     */
    private static final long serialVersionUID = -4539414694792130611L;

    private int               type;                                    // 1:其它地方登陆,2:当前用户被封号

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }
    @Override
    public int getMsgCode() {
        SignalCode attr = this.getClass().getAnnotation(SignalCode.class);
        return attr.messageCode();
    }
}
