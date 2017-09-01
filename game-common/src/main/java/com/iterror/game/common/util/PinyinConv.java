/**
 *
 */
package com.iterror.game.common.util;

/**
 * @author tony.yan
 *
 */
public class PinyinConv {
	private static int BEGIN = 45217;
	private static int END = 63486;
	private static int[] table = new int[27];

	// 对应首字母区间表
	private static char[] initialtable = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'h', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 't', 't', 'w', 'x', 'y', 'z', };
	private static char[] chartable = { '啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈', '哈', '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然', '撒', '塌', '塌', '塌', '挖', '昔', '压', '匝', };
	// 初始化
	static {
		for (int i = 0; i < 26; i++) {
			table[i] = gbValue(chartable[i]);// 得到GB2312码的首字母区间端点表，十进制。
		}
		table[26] = END;// 区间表结尾
	}

	private static int gbValue(char ch) {// 将一个汉字（GB2312）转换为十进制表示。
		String str = new String();
		str += ch;
		try {
			byte[] bytes = str.getBytes("GB2312");
			if (bytes.length < 2)
				return 0;
			return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
		} catch (Exception e) {
			return 0;
		}
	}

	public static String cn2py(String sourceStr, int num) {
		String result = "";
		if (sourceStr == null) {
			return result;
		}
		if (sourceStr.length() < num) {
			return result;
		}
		String temp = sourceStr.substring(0, num);
		int StrLength = temp.length();
		int i;
		try {
			for (i = 0; i < StrLength; i++) {
				result += Char2Initial(temp.charAt(i));
			}
		} catch (Exception e) {
			result = "";
		}
		return result.toUpperCase();
	}

	public static String cn2py(String sourceStr) {
		String result = "";
		if (sourceStr == null) {
			return result;
		}
		String temp = sourceStr;
		int StrLength = temp.length();
		int i;
		try {
			for (i = 0; i < StrLength; i++) {
				result += Char2Initial(temp.charAt(i));
			}
		} catch (Exception e) {
			result = "";
		}
		return result.toLowerCase();
	}

	private static char Char2Initial(char ch) {
		// 对英文字母的处理：小写字母转换为大写，大写的直接返回
		if (ch >= 'a' && ch <= 'z')
			return (char) (ch - 'a' + 'A');
		if (ch >= 'A' && ch <= 'Z')
			return ch;

		// 对非英文字母的处理：转化为首字母，然后判断是否在码表范围内，
		// 若不是，则直接返回。
		// 若是，则在码表内的进行判断。
		int gb = gbValue(ch);// 汉字转换首字母

		if ((gb < BEGIN) || (gb > END))// 在码表区间之前，直接返回
			return ch;

		int i;
		for (i = 0; i < 26; i++) {// 判断匹配码表区间，匹配到就break,判断区间形如“[,)”
			if ((gb >= table[i]) && (gb < table[i + 1]))
				break;
		}

		if (gb == END) {// 补上GB2312区间最右端
			i = 25;
		}
		return initialtable[i]; // 在码表区间中，返回首字母
	}

	public static void main(String[] args) {
		System.out.println(cn2py("重庆重视发展IT行业，大多数外企，如，IBM等进驻山城",3));
		System.out.println(cn2py("重庆重视发展IT行业，大多数外企，如，IBM等进驻山城"));
	}
}
