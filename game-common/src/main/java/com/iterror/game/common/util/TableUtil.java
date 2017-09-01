package com.iterror.game.common.util;

public class TableUtil {

    private static String phoneTable = "bg_cell_phone_";

    public static String getPhoneTableName(String phone) {
        String tablePrefix = phone.substring(0, 3);
        return phoneTable + tablePrefix;
    }

    public static void main(String[] args) {
        int [] frefixs = {133,142,144,146,148,149,153,180,181,189,130,131,132,141,143,145,155,156,185,186,134,135,136,137,138,139,140,147,150,151,152,157,158,159,182,183,187,188};
        
        for (int string : frefixs) {
            String str =  "CREATE TABLE bg_cell_phone_"+string+" (\r\n"+
                    "id bigint(20) NOT NULL,\r\n"+
                    "phone varchar(32) DEFAULT NULL COMMENT '手机号',\r\n"+
                    "province varchar(32) DEFAULT NULL COMMENT '省份',\r\n"+
                    "city varchar(32) DEFAULT NULL COMMENT '城市',\r\n"+
                    "area_code varchar(32) DEFAULT NULL COMMENT '电话地区号',\r\n"+
                    "post_code varchar(32) DEFAULT NULL COMMENT ' 邮政编码',\r\n"+
                    "operator varchar(32) DEFAULT NULL COMMENT '运营商',\r\n"+
                    "create_time datetime DEFAULT NULL,\r\n"+
                    "edit_time datetime DEFAULT NULL,\r\n"+
                    "PRIMARY KEY (id),\r\n"+
                    "KEY index_phone (phone)\r\n"+
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='手机归属地信息';\r\n";
           System.out.println(str);
        }
        
    }
    
//         （中国电信+中国卫通）
//  　　手机号码开头数字 133、142、144、146、148、149、153、180、181、189
//  　
//  　　（中国联通+中国网通）
//  　　手机号码开头数字 130、131、132、141、143、145、155、156、185、186
//  　
//  　　（中国移动+中国铁通）
//  　　手机号码开头数字 134、135、136、137、138、139、140、147、150、151、152、157、158、159、182、183、187、188
    
}
