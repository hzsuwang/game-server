package com.iterror.game.common.util;

/**
 * MD5工具
 *
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class MD5 {

	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    /**
     * MD5值计算
     * <p>
     * MD5的算法在RFC1321 中定义: 在RFC 1321中，给出了Test suite用来检验你的实现是否正确： MD5 ("") = d41d8cd98f00b204e9800998ecf8427e MD5 ("a") =
     * 0cc175b9c0f1b6a831c399e269772661 MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72 MD5 ("message digest") =
     * f96b697d7cb7938d525a2f31aaf161d0 MD5 ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b
     * 
     * @param res 源字符串
     * @return md5值
     */
    public final static String md5Digest(String res) {
        if (res == null || "".equals(res)) {
            return null;
        }
        byte[] strTemp;
        try {
            strTemp = res.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            return null;
        }
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            String dd = new String(str);
            return dd;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * MD5值计算+Base64
     * <p>
     * MD5的算法在RFC1321 中定义: 在RFC 1321中
     * 
     * @param res 源字符串
     * @return md5值
     */
    public final static byte[] md5SrcDigest(String res) {
        if (res == null || "".equals(res)) {
            return null;
        }
        byte[] strTemp = res.getBytes();
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            return md;
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 进行md5校验
     * @param md5Str
     * @return
     */
    public static boolean checkFileMD5(String md5Str){
    	
    	
    	return false;
    }

	/**
	 * 生成文件的md5校验值
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5String(File file){		
		if(file == null){
			return null;
		}
		InputStream fis = null;
		try{
	        MessageDigest mdTemp = MessageDigest.getInstance("MD5");
		    fis = new FileInputStream(file);
		    byte[] buffer = new byte[1024];
		    int numRead = 0;
		    while ((numRead = fis.read(buffer)) > 0) {
		    	mdTemp.update(buffer, 0, numRead);
		    }
		    fis.close();
		    return bufferToHex(mdTemp.digest());
		} catch (Exception e) {
            return null;
        } 
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同 
		char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换 
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

}
