package com.iterror.game.common.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.kantop.qp.common.dal.redis.RedisDAO;

public class BaseScreen {

    @Autowired
    protected RedisDAO redisDAO;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * // 验证uid 和sessionId是否是同一个用户
     * 
     * @param uid
     * @param sid
     * @return
     */
    protected boolean checkUserSession(Long uid, String sid) {
        boolean result = false;

        if (uid == null || StringUtils.isBlank(sid)) {
            return result;
        }

        String userInfoJson = redisDAO.getUserInfoBySessionId(sid);
        if (StringUtils.isBlank(userInfoJson)) {
            return result;
        }

        JSONObject userJson = JSONObject.parseObject(userInfoJson);
        long innerUid = userJson.getLong("id");

        if (uid != innerUid) {
            return result;
        }

        return true;
    }
}
