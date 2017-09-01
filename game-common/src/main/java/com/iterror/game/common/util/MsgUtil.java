package com.iterror.game.common.util;

import com.kantop.qp.common.util.constant.Constants.CommonConstants;

public class MsgUtil {

    /**
     * 通过code 读取内容
     * 
     * @param rc
     * @return
     */
    public static String getMsg(int rc) {
        String msg = null;
        // 公共模块
        if (rc == CommonConstants.RETURN_RESULT_FAILE) {
            msg = CommonConstants.RETURN_RESULT_FAILE_MSG;
        } else if (rc == CommonConstants.CODE_SESSION_OVERDUE) {
            msg = CommonConstants.CODE_SESSION_OVERDUE_MSG;
        } else if (rc == CommonConstants.CODE_OTHER_LOGIN) {
            msg = CommonConstants.CODE_OTHER_LOGIN_MSG;
        }

        return msg;
    }
}
