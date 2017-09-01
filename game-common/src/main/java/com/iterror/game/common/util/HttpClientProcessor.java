package com.iterror.game.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;

public class HttpClientProcessor {

    private HttpClient     client;
    private HttpMethodBase method;
    private String         encode;

    public HttpClientProcessor(HttpClient client, HttpMethodBase method, String encode){
        this.client = client;
        this.method = method;
        this.encode = encode;
    }

    public HttpClientProcessor(HttpClient client, HttpMethodBase method){
        this(client, method, "");
    }

    public String getResponseBodyAsString() throws HttpException {
        String returnStr = null;
        try {
            client.executeMethod(method);
            returnStr = method.getResponseBodyAsString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return returnStr;
    }

    
    

    protected  String getStringFromStream(InputStream inputStream) throws IOException {
        int max_bytes = 4096;
        byte[] b = new byte[max_bytes];
        StringBuilder builder = new StringBuilder();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int bytesRead = inputStream.read(b, 0, max_bytes);
        while (bytesRead != -1) {
            baos.write(b, 0, bytesRead);
            builder.append(new String(b, 0, bytesRead));
            bytesRead = inputStream.read(b, 0, max_bytes);
        }
        String content = baos.toString(encode);
        baos.close();
        return content;
    }
    
    public HttpClient getClient() {
        return client;
    }

    public void setClient(HttpClient client) {
        this.client = client;
    }

    public HttpMethodBase getMethod() {
        return method;
    }

    public void setMethod(HttpMethodBase method) {
        this.method = method;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

}
