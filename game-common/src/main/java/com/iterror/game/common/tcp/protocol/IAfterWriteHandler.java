package com.iterror.game.common.tcp.protocol;

public interface IAfterWriteHandler {

    /**
     * 消息成功下发后的后继处理
     */
    public void onWriteSuccess();

    /**
     * 消息发送失败后继处理
     */
    public void onWriteFailed();

    /**
     * 判断等待的写确认消息是否超时
     *
     * @param now the number of milliseconds since January 1, 1970, 00:00:00 GMT
     * @return true 超时, false未超时
     */
    public boolean isWriteTimeOut(long now);

    /**
     * 获取发送的序列
     *
     * @return 获取发送的序列
     */
    public Long getSendSeq();

    /**
     * 判断此消息是否还需要等待超时，防止内存泄漏
     *
     * @return true 还需等待, false 无需等待，可以从等待队列中删除
     */
    public boolean isNeedWait();
}
