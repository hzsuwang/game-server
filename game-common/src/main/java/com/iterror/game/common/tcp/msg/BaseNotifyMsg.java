package com.iterror.game.common.tcp.msg;

public abstract class BaseNotifyMsg extends BaseMsg {

    protected int              retryCount;                             // 重试次数

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int incRetryCount() {
        return retryCount++;
    }
}
