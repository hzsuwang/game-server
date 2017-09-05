package com.iterror.game.common.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Created by tony.yan on 2017/9/3.
 */
public class ServerRegistry {

    private static final Logger logger = LoggerFactory.getLogger(ServerRegistry.class);
    private CountDownLatch      latch  = new CountDownLatch(1);

    private ZkConfig            zkConfig;

    public ServerRegistry(ZkConfig zkConfig){
        this.zkConfig = zkConfig;
        register();
    }

    // 注册到zk中，其中data为服务端的 ip:port
    public void register() {
        if (zkConfig != null) {
            ZooKeeper zk = connectServer();
            if (zk != null) {
                createNode(zk);
            }
        }
    }

    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(zkConfig.getZkurl(), zkConfig.getZkSessionTimeout(), new Watcher() {

                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zk;
    }

    private void createNode(ZooKeeper zk) {
        try {
            byte[] bytes = zkConfig.getAddress().getBytes("UTF-8");
            if (!isExists(zk, "/" + zkConfig.getGroupNode())) {
                createZNode(zk, "/" + zkConfig.getGroupNode(), zkConfig.getGroupNode());
            }
            if (!isExists(zk, "/" + zkConfig.getGroupNode() + "/" + zkConfig.getSubNode())) {
                createZNode(zk, "/" + zkConfig.getGroupNode() + "/" + zkConfig.getSubNode(), zkConfig.getSubNode());
            }
            String path = zk.create("/" + zkConfig.getGroupNode() + "/" + zkConfig.getSubNode()+"/"+zkConfig.getMember(), bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isExists(ZooKeeper zk, String path) {
        try {
            Stat stat = zk.exists(path, false);
            return null != stat;
        } catch (KeeperException e) {
            logger.error("读取数据失败,发生KeeperException! path: " + path + ", errMsg:" + e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error("读取数据失败,发生InterruptedException! path: " + path + ", errMsg:" + e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createZNode(ZooKeeper zk, String path, String data) {
        try {
            String zkPath = zk.create(path, data.getBytes("UTF-8"), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            logger.info("ZooKeeper创建节点成功，节点地址：" + zkPath);
            return true;
        } catch (KeeperException e) {
            logger.error("创建节点失败：" + e.getMessage() + "，path:" + path, e);
        } catch (InterruptedException e) {
            logger.error("创建节点失败：" + e.getMessage() + "，path:" + path, e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
