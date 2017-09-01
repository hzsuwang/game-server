package com.iterror.game.gateway.tcp.bto.xip;


import com.iterror.game.common.tcp.msg.BaseReqMsg;
import com.iterror.game.common.tcp.protocol.SignalCode;
import com.iterror.game.gateway.tcp.message.MessageCode;

@SignalCode(messageCode = MessageCode.MSGCODE_AUTH_REQ)
public class AuthReq extends BaseReqMsg {

    /**
     * 
     */
    private static final long serialVersionUID = 4968371172876505908L;
    private String            sid;                                     // session id
    private String            did;                                     // 设备id
   

    /**
     * @return the sid
     */
    public String getSid() {
        return sid;
    }

    /**
     * @param sid the sid to set
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * @return the did
     */
    public String getDid() {
        return did;
    }

    /**
     * @param did the did to set
     */
    public void setDid(String did) {
        this.did = did;
    }
    
    @Override
    public int getMsgCode() {
        SignalCode attr = this.getClass().getAnnotation(SignalCode.class);
        return attr.messageCode();
    }
}
