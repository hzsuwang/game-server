package com.iterror.game.common.zookeeper;

/**
 * Created by tony.yan on 2017/9/3.
 */
public class ZkConfig {

    private String member           = "member";
    private String zkurl            = "127.0.0.1:2181";
    private String groupNode        = "";
    private String subNode          = "";
    private String address          = "";
    private int    zkSessionTimeout = 2000;

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public int getZkSessionTimeout() {
        return zkSessionTimeout;
    }

    public void setZkSessionTimeout(int zkSessionTimeout) {
        this.zkSessionTimeout = zkSessionTimeout;
    }

    public String getZkurl() {
        return zkurl;
    }

    public void setZkurl(String zkurl) {
        this.zkurl = zkurl;
    }

    public String getGroupNode() {
        return groupNode;
    }

    public void setGroupNode(String groupNode) {
        this.groupNode = groupNode;
    }

    public String getSubNode() {
        return subNode;
    }

    public void setSubNode(String subNode) {
        this.subNode = subNode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
