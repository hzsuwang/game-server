package com.iterror.game.common.constant;

public class Constants {

    /**
     * 公共常量
     * 
     * @author tony.yan
     */
    public static class CommonConstants {

        /**
         * 默认返回的pageSize
         */
        public static final int    DEFAULT_PAGE_SIZE          = 20;
        /**
         * 返回值 成功：1
         */
        public static final int    RETURN_RESULT_SUCCESS      = 1;
        /**
         * 返回值 成功：1
         */
        public static final String    RETURN_RESULT_SUCCESS_MSG      = "success";

        /**
         * 公共正确，成功状态
         */
        public static final int    COMMON_STATUS_OK           = 1;

        /**
         * app验证key kantop
         */
        public static final String APP_MD5_KEY                = "a42cd85d936bcdf2cdb093e391a93030";

        /**
         * session过期 -1
         */
        public static final int    CODE_SESSION_OVERDUE       = -1;
        public static final String CODE_SESSION_OVERDUE_MSG   = "会话己过期，请重新登陆";

        /**
         * 返回值 失败：-2
         */
        public static final int    CODE_USER_TITLES           = -2;
        public static final String CODE_USER_TITLES_MSG       = "账号被封";

        /**
         * 返回值 失败：-3
         */
        public static final int    CODE_OTHER_LOGIN           = -3;
        public static final String CODE_OTHER_LOGIN_MSG       = "其他设备登录";

        /**
         * 返回值 失败：-4
         */
        public static final int    CODE_ROOM_CARD_BALANCE     = -4;
        public static final String CODE_ROOM_CARD_BALANCE_MSG = "房卡不足，请购买房卡！";

        /**
         * 返回值 失败：-5
         */
        public static final int    CODE_IMEI_TITLES           = -5;
        public static final String CODE_IMEI_TITLES_MSG       = "当前设备被封";

        /**
         * 返回值 失败：-2
         */
        public static final int    RETURN_RESULT_FAILE        = -999;
        public static final String RETURN_RESULT_FAILE_MSG    = "请求参数错误,请重试！";

    }

    /**
     * 用户常量
     * 
     * @author tony.yan
     */
    public static class UserConstants {

        /**
         * 微信
         */
        public static final int    USER_TYPE_WEIXIN              = 0;

        /**
        *
        */
        public static final String OAUTH_FROM_WX_STR             = "weixin";

        /**
         * 用户被禁用
         */
        public static final int    USER_STATUS_DISABLE           = 0;

        /**
         * 正常用户
         */
        public static final int    USER_STATUS_NORMAL            = 1;

        /**
         * 返回值 失败：-20101
         */
        public static final int    CODE_USER_NOT_EXIST_ERROR     = -20101;
        public static final String CODE_USER_NOT_EXIST_ERROR_MSG = "帐号不存在";

        /**
         * 返回值 失败：-20102
         */
        public static final int    CODE_LONG_CHECK_ERROR         = -20102;
        public static final String CODE_LONG_CHECK_ERROR_MSG     = "帐号登录验证失败";

    }

    /**
     * 房卡常量
     * 
     * @author tony.yan
     */
    public static class RoomCardConstants {

        // 开房消费来源
        public static final int OPEN_ROOM_SRC = 100;
        // 给用户发卡
        public static final int SEND_USER_SRC = 101;
        // 接收到赠送过来的卡
        public static final int RECEIVE_SRC   = 200;
    }

    /**
     * 文件工具类
     * 
     * @author tony.yan
     */
    public static class FileConstants {

        public static String       FileServerRoot             = "/img";
        public static String       FileAudioServerRoot        = "/audio";

        public static String       FileServerSeperator        = "/";
        public static String       FileServerChatFileTypeWav  = "/chatfile/wav"; // 聊天-语音
        public static String       FileServerChatFileTypePic  = "/chatfile/pic"; // 聊天-图片
        public static String       FileServerFileTypeAvatar   = "/avatar";      // 头像

        /**
         * 文件上传失败
         */
        public static final int    CODE_UPLOAD_FILE_ERROR     = -800101;
        public static final String CODE_UPLOAD_FILE_ERROR_MSG = "文件上传失败";
    }
}
