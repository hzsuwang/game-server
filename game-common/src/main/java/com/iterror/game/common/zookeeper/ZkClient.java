package com.iterror.game.common.zookeeper;

import com.iterror.game.common.util.RandomUtil;
import org.I0Itec.zkclient.ZkServer;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tony.yan on 2017/9/23.
 */
public class ZkClient {

    private Stat                stat   = new Stat();
    private static final Logger logger = LoggerFactory.getLogger(ZkServer.class);
    private ZkConfig            zkConfig;
    ZooKeeper                   zk     = null;

    public ZkClient(ZkConfig zkConfig){
        this.zkConfig = zkConfig;
        register();
    }

    // 注册到zk中，其中data为服务端的 ip:port
    public void register() {
        if (zkConfig != null) {
            connectServer();
        }
    }

    private ZooKeeper connectServer() {
        try {
            zk = new ZooKeeper(zkConfig.getZkurl(), zkConfig.getZkSessionTimeout(), new Watcher() {

                public void process(WatchedEvent event) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zk;
    }

    public String getRandomNode() {
        try {
            List<String> serverList = new ArrayList<String>();
            List<String> nodeList = zk.getChildren("/" + zkConfig.getGroupNode() + "/" + zkConfig.getSubNode(), false);
            for (String str : nodeList) {
                byte[] datas = zk.getData("/" + zkConfig.getGroupNode() + "/" + zkConfig.getSubNode() + "/" + str, false, stat);
                String server = new String(datas, "utf-8");
                serverList.add(server);
            }
            if (serverList.size() > 0) {
                return serverList.get(RandomUtil.getRandomNum(serverList.size()));
            }
        } catch (Exception ex) {
            logger.error("getRandomNode error", ex);
        }
        return null;
    }

    public String getModNode(long userId) {
        try {
            List<String> serverList = new ArrayList<String>();
            List<String> nodeList = zk.getChildren("/" + zkConfig.getGroupNode() + "/" + zkConfig.getSubNode(), false);
            for (String str : nodeList) {
                byte[] datas = zk.getData("/" + zkConfig.getGroupNode() + "/" + zkConfig.getSubNode() + "/" + str, false, stat);
                String server = new String(datas, "utf-8");
                serverList.add(server);
            }
            if (serverList.size() > 0) {
                int index = (int) (userId % serverList.size());
                return serverList.get(index);
            }
        } catch (Exception ex) {
            logger.error("getRandomNode error", ex);
        }
        return null;
    }
}
