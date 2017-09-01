/*
 * Copyright 1999-2009 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with Alibaba.com.
 */

package com.iterror.game.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientHelper {
    private static final Logger log = LoggerFactory
            .getLogger(HttpClientHelper.class);

    public static final String POST_CONTEXT_TYPE_GBK = "application/x-www-form-urlencoded;charset=GBK";

    public static final String POST_CONTENTTYPE_UTF8 = "application/x-www-form-urlencoded;charset=UTF-8";

    public static String getHtml(String url, String charSet, int outTime) {
        GetMethod get = new GetMethod(url);
        try {
            HttpClient client = getHttpClient(outTime);

            BufferedReader reader;

            client.executeMethod(get);
            InputStream inputStream = get.getResponseBodyAsStream();

            reader = new BufferedReader(new InputStreamReader(inputStream,
                    charSet));

            String s = "";
            String s1 = "";

            while ((s1 = reader.readLine()) != null) {
                s += (s1);
            }
            reader.close();
            if ((s.length() > 0) && (s.lastIndexOf("\n") == (s.length() - 1))) {
                s = s.substring(0, s.length() - 1);
            }
            return s;

//            int max_bytes = 4096;
//            byte[] b = new byte[max_bytes];
//            StringBuilder builder = new StringBuilder();
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            int bytesRead = inputStream.read(b, 0, max_bytes);
//            while (bytesRead != -1) {
//                baos.write(b, 0, bytesRead);
//                builder.append(new String(b, 0, bytesRead));
//                bytesRead = inputStream.read(b, 0, max_bytes);
//            }
//            String content = baos.toString(charSet);
//            baos.close();
//            return content;

        } catch (HttpException e) {
            log.warn("", e);
        } catch (IOException e) {
            log.warn("", e);
        } finally {
            try {
                get.releaseConnection();
            } catch (Exception e) {
            }
        }
        return "";
    }

    public static String getResponseBodyAsString(String url,
            Map<String, String> params, int outTime, String contentType)
            throws HttpException {
        HttpClient client = getHttpClient(outTime);
        PostMethod postMethod = new PostMethod(url);
        String result = "";
        HttpClientProcessor processor;
        try {
            postMethod.setRequestHeader("Content-Type", contentType);
            NameValuePair[] data = new NameValuePair[params.size()];
            int idx = 0;
            for (Map.Entry<String, String> ent : params.entrySet()) {
                data[idx++] = new NameValuePair(ent.getKey(), ent.getValue());
            }
            postMethod.setRequestBody(data);
            processor = new HttpClientProcessor(client, postMethod);
            result = processor.getResponseBodyAsString();
        } catch (Exception e) {
            throw new HttpException(e.getMessage());
        } finally {
            try {
                postMethod.releaseConnection();
            } catch (Exception e) {
                throw new HttpException(e.getMessage());
            }
        }
        return result;

    }

    /**
     * 
     * @return
     */
    public static HttpClient getHttpClient(Integer outTime) {
        HttpClient client = new HttpClient();
        int milliTime = (outTime < 0) ? 10000 : (outTime * 1000);

        client.getHttpConnectionManager().getParams()
                .setConnectionTimeout(milliTime);
        // 设置读数据超时时间
        client.getHttpConnectionManager().getParams().setSoTimeout(10000);
        // 获取连接的时间
        client.getParams().setConnectionManagerTimeout(10000);

        return client;
    }

}
