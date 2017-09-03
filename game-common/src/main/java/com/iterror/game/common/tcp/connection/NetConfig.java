package com.iterror.game.common.tcp.connection;

/**
 * Created by tony.yan on 2017/9/3.
 */
public class NetConfig {

    private String ip              = "127.0.0.1";
    private int    port;
    private int    bossGroupSize   = Runtime.getRuntime().availableProcessors() * 2;// /** 用于分配处理业务线程的线程组个数 */
    /*
     * NioEventLoopGroup实际上就是个线程池, NioEventLoopGroup在后台启动了n个NioEventLoop来处理Channel事件, 每一个NioEventLoop负责处理m个Channel,
     * NioEventLoopGroup从NioEventLoop数组里挨个取出NioEventLoop来处理Channel
     */
    private int    workerGroupSize = 10;


    private String registryAddress=ip+":"+port;


    public String getRegistryAddress() {
        return registryAddress;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }


    public int getBossGroupSize() {
        return bossGroupSize;
    }

    public void setBossGroupSize(int bossGroupSize) {
        this.bossGroupSize = bossGroupSize;
    }

    public int getWorkerGroupSize() {
        return workerGroupSize;
    }

    public void setWorkerGroupSize(int workerGroupSize) {
        this.workerGroupSize = workerGroupSize;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
