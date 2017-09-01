package com.iterror.game.common.tcp.msg;

public abstract class BaseRespMsg extends BaseMsg {

    /**
     * 
     */
    private static final long serialVersionUID = -1218496941625235666L;
    private int               rc               = 1;
    private String            errorMsg;                                // 错误消息

    
    public String getErrorMsg() {
        return errorMsg;
    }

    
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }
}
