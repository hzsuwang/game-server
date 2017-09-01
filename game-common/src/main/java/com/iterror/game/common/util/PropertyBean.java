package com.iterror.game.common.util;

public class PropertyBean {

    private String imgLocal        = "D:/";                 // 本地文件目录
    private String imgServerDomain = "http://192.168.20.74"; // 文件服务器域名
    private int    port;
    private String ip;
    private String serviceGroup;
    private String service;
    private String zooKeeperConnectString;

    public String getZooKeeperConnectString() {
        return zooKeeperConnectString;
    }

    public void setZooKeeperConnectString(String zooKeeperConnectString) {
        this.zooKeeperConnectString = zooKeeperConnectString;
    }

    public String getServiceGroup() {
        return serviceGroup;
    }

    public void setServiceGroup(String serviceGroup) {
        this.serviceGroup = serviceGroup;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getImgLocal() {
        return imgLocal;
    }

    public void setImgLocal(String imgLocal) {
        this.imgLocal = imgLocal;
    }

    public String getImgServerDomain() {
        return imgServerDomain;
    }

    public void setImgServerDomain(String imgServerDomain) {
        this.imgServerDomain = imgServerDomain;
    }
}
