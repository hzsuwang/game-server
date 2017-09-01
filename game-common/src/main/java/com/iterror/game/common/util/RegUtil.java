package com.iterror.game.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegUtil {
    static Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
    static Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
    /**
     * 判断手机号是否符合规则
     * @param phoneNumber
     * @return
     */
    public static boolean validPhoneNumber(String phoneNumber) {

        if (StringUtil.isBlank(phoneNumber)) {
            return false;
        }

       
        Matcher m = p.matcher(phoneNumber);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证邮箱是否符合规则
     * @param email
     * @return
     */
    public static boolean validEmail(String email){
        Matcher matcher = pattern.matcher(email.trim());
        if(!matcher.matches()){
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        System.out.println(validPhoneNumber("13148332887"));

        System.out.println(validEmail("liuhouxiang@123.com"));
    }

}
