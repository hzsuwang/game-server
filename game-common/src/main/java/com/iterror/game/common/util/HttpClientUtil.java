package com.iterror.game.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {

    public static final String SunX509 = "SunX509";
    public static final String JKS = "JKS";
    public static final String PKCS12 = "PKCS12";
    public static final String TLS = "TLS";
    private static MultiThreadedHttpConnectionManager connectionManager;
    private static final Logger log = LoggerFactory.getLogger("HttpClientUtil");

    private static HttpClient getHttpClient() {
        connectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = connectionManager.getParams();
        // TODO
        params.setDefaultMaxConnectionsPerHost(150);
        params.setConnectionTimeout(20000);
        params.setSoTimeout(20000);

        HttpClientParams clientParams = new HttpClientParams();
        clientParams.setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        HttpClient client = new org.apache.commons.httpclient.HttpClient(clientParams, connectionManager);

        return client;
    }
    
    /**
     * http get 请求
     * @param url
     * @return
     */
    public static Map<String, Object> get(String url) {
        GetMethod getmethod = new GetMethod(url);
        return httpRequest(getmethod, null, false);
    }    
    
    /**
     * http get 请求
     * @param url
     * @param params
     * @param headers
     * @return {@link Map}
     */
    public static Map<String, Object> get(String url, PostParameter[] params, Map<String, String> headers) {

        if (null != params && params.length > 0) {
            String encodedParams = encodeParameters(params);
            if (-1 == url.indexOf("?")) {
                url += "?" + encodedParams;
            } else {
                url += "&" + encodedParams;
            }
        }
        GetMethod getmethod = new GetMethod(url);
        return httpRequest(getmethod, headers, true);
    }

    public static Map<String, Object> post(String url, PostParameter[] params, Map<String, String> headers) {

        PostMethod postMethod = new PostMethod(url);
        for (int i = 0; i < params.length; i++) {
            postMethod.addParameter(params[i].getName(), params[i].getValue());
        }
        HttpMethodParams param = postMethod.getParams();
        param.setContentCharset("UTF-8");

        return httpRequest(postMethod, headers, true);
    }

    public static Map<String, String> httpRequest(HttpMethod method) {
        HttpClient client = getHttpClient();
        int responseCode = -1;
        try {
            method.getParams()
                    .setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
            client.executeMethod(method);
            //Header[] resHeader = method.getResponseHeaders();
            responseCode = method.getStatusCode();
            String responseStr = method.getResponseBodyAsString();


            Map<String, String> response = new HashMap<String, String>();
            if (responseCode == 200) {
                response.put("responseCode", Integer.toString(responseCode));
                response.put("responseStr", responseStr);
            }
            return response;
        } catch (IOException ioe) {
            log.error("HTTP request error: ", ioe);
            return null;
        } finally {
            method.releaseConnection();
        }
    }

    public static Map<String, Object> httpRequest(HttpMethod method, Map<String, String> specialHeaders, boolean resp2String) {
        //InetAddress ipaddr;
        HttpClient client = getHttpClient();
        int responseCode = -1;
        try {
            //ipaddr = InetAddress.getLocalHost();
            List<Header> headers = new ArrayList<Header>();
            if (specialHeaders != null) {
                // 特殊Http头，如微博请求时要带的accessToken等
                for (String key : specialHeaders.keySet()) {
                    headers.add(new Header(key, specialHeaders.get(key)));
                }
                client.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
            }

            method.getParams()
                    .setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
            client.executeMethod(method);
            //Header[] resHeader = method.getResponseHeaders();
            responseCode = method.getStatusCode();


            Map<String, Object> response = new HashMap<String, Object>();
            if (responseCode == 200) {
                response.put("responseCode", Integer.toString(responseCode));
                if(resp2String){
                    String responseStr = method.getResponseBodyAsString();
                    response.put("responseStr", responseStr);
                } else {
                    byte[] responseBody = method.getResponseBody();
                    response.put("responseBody", responseBody);
                }
            }
            return response;
        } catch (IOException ioe) {
            log.error("HTTP request error:",ioe);
            return null;
        } finally {
            method.releaseConnection();
        }
    }

    public static String encodeParameters(PostParameter[] postParams) {
        StringBuffer buf = new StringBuffer();
        for (int j = 0; j < postParams.length; j++) {
            if (j != 0) {
                buf.append("&");
            }
            try {
                buf.append(URLEncoder.encode(postParams[j].getName(), "UTF-8")).append("=")
                        .append(URLEncoder.encode(postParams[j].getValue(), "UTF-8"));
            } catch (java.io.UnsupportedEncodingException neverHappen) {}
        }
        return buf.toString();
    }
    
    /**
     * 获取SSLContext
     * @param trustFile 
     * @param trustPasswd
     * @param keyFile
     * @param keyPasswd
     * @return
     * @throws NoSuchAlgorithmException 
     * @throws KeyStoreException 
     * @throws IOException 
     * @throws CertificateException 
     * @throws UnrecoverableKeyException 
     * @throws KeyManagementException 
     */
    public static SSLContext getSSLContext(
            FileInputStream trustFileInputStream, String trustPasswd,
            FileInputStream keyFileInputStream, String keyPasswd)
            throws NoSuchAlgorithmException, KeyStoreException,
            CertificateException, IOException, UnrecoverableKeyException,
            KeyManagementException {

        // ca
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(HttpClientUtil.SunX509);
        KeyStore trustKeyStore = KeyStore.getInstance(HttpClientUtil.JKS);
        trustKeyStore.load(trustFileInputStream, HttpClientUtil
                .str2CharArray(trustPasswd));
        tmf.init(trustKeyStore);

        final char[] kp = HttpClientUtil.str2CharArray(keyPasswd);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(HttpClientUtil.SunX509);
        KeyStore ks = KeyStore.getInstance(HttpClientUtil.PKCS12);
        ks.load(keyFileInputStream, kp);
        kmf.init(ks, kp);

        SecureRandom rand = new SecureRandom();
        SSLContext ctx = SSLContext.getInstance(HttpClientUtil.TLS);
        ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), rand);

        return ctx;
    }
    
    /**
     * 字符串转换成char数组
     * @param str
     * @return char[]
     */
    public static char[] str2CharArray(String str) {
        if(null == str) return null;
        
        return str.toCharArray();
    }
    
    /**
     * 存储ca证书成JKS格式
     * @param cert
     * @param alias
     * @param password
     * @param out
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws IOException
     */
    public static void storeCACert(Certificate cert, String alias,
            String password, OutputStream out) throws KeyStoreException,
            NoSuchAlgorithmException, CertificateException, IOException {
        KeyStore ks = KeyStore.getInstance("JKS");

        ks.load(null, null);

        ks.setCertificateEntry(alias, cert);

        // store keystore
        ks.store(out, HttpClientUtil.str2CharArray(password));

    }

    /**
     * 获取不带查询串的url
     * @param strUrl
     * @return String
     */
    public static String getURL(String strUrl) {

        if(null != strUrl) {
            int indexOf = strUrl.indexOf("?");
            if(-1 != indexOf) {
                return strUrl.substring(0, indexOf);
            } 
            
            return strUrl;
        }
        
        return strUrl;
        
    }
    
    /**
     * 获取查询串
     * @param strUrl
     * @return String
     */
    public static String getQueryString(String strUrl) {
        
        if(null != strUrl) {
            int indexOf = strUrl.indexOf("?");
            if(-1 != indexOf) {
                return strUrl.substring(indexOf+1, strUrl.length());
            } 
            
            return "";
        }
        
        return strUrl;
    }
    
    /**
     * 获取CA证书信息
     * @param cafile CA证书文件
     * @return Certificate
     * @throws CertificateException
     * @throws IOException
     */
    public static Certificate getCertificate(File cafile)
            throws CertificateException, IOException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        FileInputStream in = new FileInputStream(cafile);
        Certificate cert = cf.generateCertificate(in);
        in.close();
        return cert;
    }
    
    
    /**
     * get HttpURLConnection
     * @param strUrl url地址
     * @return HttpURLConnection
     * @throws IOException
     */
    public static HttpURLConnection getHttpURLConnection(String strUrl)
            throws IOException {
        URL url = new URL(strUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url
                .openConnection();
        return httpURLConnection;
    }
    
    /**
     * get HttpsURLConnection
     * @param strUrl url地址
     * @return HttpsURLConnection
     * @throws IOException
     */
    public static HttpsURLConnection getHttpsURLConnection(String strUrl)
            throws IOException {
        URL url = new URL(strUrl);
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url
                .openConnection();
        return httpsURLConnection;
    }
    
    /**
     * BufferedReader转换成String<br/>
     * 注意:流关闭需要自行处理
     * @param reader
     * @return String
     * @throws IOException
     */
    public static String bufferedReader2String(BufferedReader reader) throws IOException {
        StringBuffer buf = new StringBuffer();
        String line = null;
        while( (line = reader.readLine()) != null) {
            buf.append(line);
            buf.append("\r\n");
        }
                
        return buf.toString();
    }
    
    /**
     * 处理输出<br/>
     * 注意:流关闭需要自行处理
     * @param out
     * @param data
     * @param len
     * @throws IOException
     */
    public static void doOutput(OutputStream out, byte[] data, int len) 
            throws IOException {
        int dataLen = data.length;
        int off = 0;
        while(off < dataLen) {
            if(len >= dataLen) {
                out.write(data, off, dataLen);
            } else {
                out.write(data, off, len);
            }
            
            //刷新缓冲区
            out.flush();
            
            off += len;
            
            dataLen -= len;
        }
        
    }
    
    public static void main(String[] args) {
        HttpClientUtil.get("http://192.1", null, null);

    }
}
