/**
 * 
 */
package com.iterror.game.common.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author tony.yan
 * 
 */
public class HtmlUtil {

	/**
	 * @param onPageUrl
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static HttpURLConnection getUrlConnection(String onPageUrl, String whost) throws MalformedURLException, IOException {
		URL uurl = new URL(onPageUrl);
		HttpURLConnection conn = (HttpURLConnection) uurl.openConnection();
		conn.setRequestProperty("Accept-Encoding", "gzip,deflate");
		conn.setRequestProperty("Host", whost);
		conn.setRequestProperty("Connection", "keep-alive");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:16.0) Gecko/20100101 Firefox/16.0");
		conn.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		conn.setRequestProperty("Cache-Control", "max-age=0");
		conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		conn.setRequestProperty(
				"Cookie",
				"QC011=734; QC006=lcr7wzubxxotiljbeu3v13bg; QC008=1351184306.1368804590.1368885107.22; QC005=9c442906655801b6b2b980bb986944d0; P00002=%7B%22uid%22%3A%222010782647%22%2C%22nickname%22%3A%22hzqvod%22%2C%22email%22%3A%222010782647%22%2C%22type%22%3A1%7D; QC102=bid%3A623415923uid%3A2010782647%3D1; QC018=tvid%3D519226%26videoPlayTime%3D274.76%26terminalId%3D11; Hm_lvt_53b7374a63c37483e5dd97d78d9bb36e=1368373267,1368459738,1368805973,1368885108; Hm_lvt_e531f04d8541f3dd66f504f77aa5a94d=1363013468,1363784627; QC021=%5B%7B%22key%22%3A%22%E6%96%AF%E5%B7%B4%E8%BE%BE%E5%85%8B%E6%96%AF%22%7D%5D; P00001=DFpUWhi5V9PloPuNJ70bR5m3DKqTm1ZPVRDCdeIam3Wm31oOTEl7W3eTRZ4Fcm11RbLEF9JZvUB0SDNwBm1Qw9w9M8Bcpm2hdCMu40hQMfRXiZSBA; P00003=2010782647; P01010=1368374400; QC025=2010782647-20130518; QC001=1; QC007=DIRECT; Hm_lpvt_53b7374a63c37483e5dd97d78d9bb36e=1368885154; QC010=266036684; P00010=2010782647");
		conn.connect();
		return conn;
	}

}
