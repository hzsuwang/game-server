package com.iterror.game.common.util;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.kantop.qp.common.engine.bto.BaseMsg;

/**
 * 获取json串中某个属性
 * 
 * @author bob
 */
public class SjsonUtil {

    private SjsonUtil(){
    }

    public static void main(String[] args) {
        String str = "{\"FID\":22,Data:{\"UAID\":\"dd@126.com\",\"PWD\":\"7030ee1b8aa5481643b9ac899638f8d7\",Type:0,AToken:\"111\",CType:2}}";
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            getMsgCodeFromMsg(str);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    /**
     * @param msg
     * @return
     */
    public static int getMsgCodeFromMsg(final String msg) {
        int result = -1;
        JSONObject jsonObj = JSONObject.parseObject(msg);
        String msgCode = jsonObj.getString(BaseMsg.msgCodeKey);
        if (StringUtils.isNotBlank(msgCode)) {
            try {
                result = Integer.parseInt(msgCode);
            } catch (NumberFormatException e) {
                result = -2;
            }
        }
        return result;
    }

    /**
     * @param msg
     * @return 返回msgId
     */
    public static long getMsgIdFromMsg(final String msg) {
        long result = -1;
        JSONObject jsonObj = JSONObject.parseObject(msg);
        JSONObject data = jsonObj.getJSONObject("data");
        if (data != null) {
            String msgId = data.getString("msg_id");
            if (StringUtils.isNotBlank(msgId)) {
                try {
                    result = Long.parseLong(msgId);
                } catch (NumberFormatException e) {
                    result = -2;
                }
            }
        }
        return result;
    }

    /**
     * @param msg
     * @return 返回msgId
     */
    public static long getMsgIdReturnMsg(final String msg) {
        long result = -1;
        JSONObject jsonObj = JSONObject.parseObject(msg);
        String msgId = jsonObj.getString("msg_id");
        if (StringUtils.isNotBlank(msgId)) {
            try {
                result = Long.parseLong(msgId);
            } catch (NumberFormatException e) {
                result = -2;
            }
        }
        return result;
    }

    /**
     * @param msg
     * @return 返回客户端请求id
     */
    public static long getCmIdFromMsg(final String msg) {
        long result = -1;
        JSONObject jsonObj = JSONObject.parseObject(msg);
        JSONObject data = jsonObj.getJSONObject("data");
        if (data != null) {
            String cm_id = data.getString("cm_id");
            if (StringUtils.isNotBlank(cm_id)) {
                try {
                    result = Long.parseLong(cm_id);
                } catch (NumberFormatException e) {
                    result = -2;
                }
            }
        }
        return result;
    }

    public static long getUidFromMsg(final String msg) {
        JSONObject jsonObj = JSONObject.parseObject(msg);
        return jsonObj.getLongValue("uid");
    }

    /**
     * @param msg
     * @return 返回sid
     */
    public static String getSidFromMsg(final String msg) {
        JSONObject jsonObj = JSONObject.parseObject(msg);
        JSONObject data = jsonObj.getJSONObject("data");
        if (data != null) {
            return data.getString("sid");
        }
        return null;
    }

}
