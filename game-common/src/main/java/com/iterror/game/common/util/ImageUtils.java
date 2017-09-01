/*
 * CKFinder
 * ========
 * http://ckfinder.com
 * Copyright (C) 2007-2011, CKSource - Frederico Knabben. All rights reserved.
 *
 * The software, this file and its contents are subject to the CKFinder
 * License. Please read the license.txt file before using, installing, copying,
 * modifying or distribute this file or part of its contents. The contents of
 * this file is part of the Source Code of CKFinder.
 */
package com.iterror.game.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utils to operate on images.
 */
public class ImageUtils {

    private static final Logger logger        = LoggerFactory.getLogger(ImageUtils.class);
    /**
     * allowed image extensions.
     */
    private static final String[] ALLOWED_EXT   = { "gif", "jpeg", "jpg", "png", "psd", "bmp", "tiff", "tif", "swc",
            "jpc", "jp2", "jpx", "jb2", "xbm", "wbmp" };
    private static final int      MAX_BUFF_SIZE = 1024;

    public synchronized static String saveImage(String srcUrl, String localPathPrefix) {
        try {
            String path = getLocalPath(srcUrl, localPathPrefix);
            File outFile = new File(path);
            OutputStream output = new FileOutputStream(outFile);
            URL url = new URL(srcUrl);
            InputStream is = url.openStream();
            IOUtils.copy(is, output);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String getLocalPath(String srcUrl, String localPathPrefix) {
        File extFile = new File(srcUrl);
        String fileTempName = extFile.getName();
        String fileName = "";
        if (fileTempName.indexOf(".") == -1) {
            fileName = System.currentTimeMillis() + ".jpg";
        } else {
            fileName = System.currentTimeMillis()
                       + fileTempName.substring(fileTempName.lastIndexOf("."), fileTempName.length());
        }
        String localPath = localPathPrefix + File.separator + FileUtil.getBaseDirPath("vod");
        String path = localPath + fileName;
        File destFile = new File(localPath);
        if (!destFile.exists()) {
            destFile.mkdir();
        }
        return path;
    }

    public static String downloadUrl(String srcUrl, String localPathPrefix, String refUrl, String host) {
        try {
            String path = getLocalPath(srcUrl, localPathPrefix);
            File outFile = new File(path);
            OutputStream output = new FileOutputStream(outFile);
            URL url = new URL(srcUrl);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            connection.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
            connection.setRequestProperty("Cache-Control", "max-age=0");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Host", host);
            connection.setRequestProperty("Referer", refUrl);
            connection.setRequestProperty("User-Agent",
                                          "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:32.0) Gecko/20100101 Firefox/32.0");
            InputStream is = connection.getInputStream();
            IOUtils.copy(is, output);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("downloadUrl srcUrl=[{}],localPath=[{}] refUrl=[{}] host=[{}]", new Object[] { srcUrl,
                    localPathPrefix, refUrl, host });
            return "";
        }
    }
    
    public static String subHost(String url) {
        String detailUrl = url;
        if (url != null && url.startsWith("http")) {
            detailUrl = url.substring(url.indexOf("//") + 2);
            detailUrl = detailUrl.substring(0, detailUrl.indexOf("/"));
        }
        return detailUrl;
    }

    /**
     * writes unchanged file to disk.
     * 
     * @param sourceFile - file to read from
     * @param destFile - file to write to
     * @throws IOException when error occurs.
     */
    public static void writeUntouchedImage(final File sourceFile, final File destFile) throws IOException {
        FileInputStream fileIS = new FileInputStream(sourceFile);
        writeUntouchedImage(fileIS, destFile);
    }

    /**
     * writes unchanged file to disk.
     * 
     * @param stream - stream to read the file from
     * @param destFile - file to write to
     * @throws IOException when error occurs.
     */
    private static void writeUntouchedImage(final InputStream stream, final File destFile) throws IOException {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        byte[] buffer = new byte[MAX_BUFF_SIZE];
        int readNum = -1;
        while ((readNum = stream.read(buffer)) != -1) {
            byteArrayOS.write(buffer, 0, readNum);
        }
        byte[] bytes = byteArrayOS.toByteArray();
        byteArrayOS.close();
        FileOutputStream fileOS = new FileOutputStream(destFile);
        fileOS.write(bytes);
        fileOS.flush();
        fileOS.close();
    }

    public static void main(String[] args) {
        String url = "http://fanyi.youdao.com/";
        String domain = subHost(url);
        System.out.println(domain);
    }
}
