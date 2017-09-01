package com.iterror.game.common.util;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

public class QpStringUtil extends StringUtil {

    /**
     * <p>
     * Checks if the String contains only unicode digits. A decimal point is not a unicode digit and returns false.
     * </p>
     * <p>
     * <code>null</code> will return <code>false</code>. An empty String ("") will return <code>true</code>.
     * </p>
     * 
     * <pre>
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = true
     * StringUtils.isNumeric("  ")   = false
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric("12 3") = false
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = false
     * </pre>
     * 
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains digits, and is non-null
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 中文数字
     */
    private static final String[] CN_UPPER_NUMBER = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };

    public static String getNumChina(int level) {
        if (level < 0) {
            return "不可信";
        }
        return CN_UPPER_NUMBER[level];
    }

    public static String subHost(String url) {
        if (url != null && url.startsWith("http")) {
            url = url.substring(url.indexOf("//") + 2).substring(url.substring(url.indexOf("//") + 2).indexOf("/"));
        }
        return url;
    }

    /**
     * 根据字符串明文获取密码
     * 
     * @param ming
     * @return
     */
    public static String password(String ming) {
        String firstMd5 = DigestUtils.md5Hex(ming);
        return DigestUtils.md5Hex(firstMd5.substring(0, firstMd5.length() - 1) + "#%lb!#");
    }

    /**
     * 统计改成 4位版本号
     * 
     * @param clientVer
     * @return
     */
    public static int versionToInt(String clientVer) {
        return Integer.valueOf(clientVer.trim().replace(".", ""));
    }

    /**
     * @param pwd
     * @return
     */
    public static String chengPwd(String pwd) {
        if (isBlank(pwd)) {
            return pwd;
        }
        char[] chars = pwd.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (i == (2 - 1)) {
                continue;
            }
            if (i == (chars.length - 2)) {
                continue;
            }
            sb.append(chars[i]);
        }
        return sb.toString();
    }

    public static String getListStringToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                sb.append(list.get(i));
            } else {
                sb.append(list.get(i)).append(",");
            }
        }
        return sb.toString();
    }
    
    public static String getListLongToString(List<Long> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                sb.append(list.get(i));
            } else {
                sb.append(list.get(i)).append(",");
            }
        }
        return sb.toString();
    }

}
