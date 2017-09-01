package com.iterror.game.gateway.tcp.message;

/**
 * Created by tony.yan on 2017/9/2.
 */
public class MessageCode {

    // 消息回执
    public static final int MSGCODE_RETURN_MSG_REQ             = 100000;
    public static final int MSGCODE_RETURN_MSG_RESP            = 200000;

    // 心跳
    public static final int MSGCODE_HEARTBEAT_REQ              = 100001;
    public static final int MSGCODE_HEARTBEAT_RESP             = 200001;

    // 鉴权
    public static final int MSGCODE_AUTH_REQ                   = 100002;
    public static final int MSGCODE_AUTH_RESP                  = 200002;

    // 退出登录
    public static final int MSGCODE_EXIT_NOTIFY                = 300003;
}
