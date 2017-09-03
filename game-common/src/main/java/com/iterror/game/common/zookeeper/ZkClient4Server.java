package com.iterror.game.common.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

/**
 * Created by tony.yan on 2017/9/3.
 */

public class ZkClient4Server {

    private static CuratorFramework createCurator(String connectionURL, String nodepath) {

        return CuratorFrameworkFactory.builder()
                .connectString(connectionURL)
                .connectionTimeoutMs(60000)
                .retryPolicy(new RetryNTimes(Integer.MAX_VALUE,1000000))
                .sessionTimeoutMs(60000).namespace(nodepath).build();

    }

    public static void provideServer(String groupName, String nodePath, String zkUrl, String connectionURL) {

        try {
            CuratorFramework framework = createCurator(zkUrl, nodePath);
            framework.start();
            framework.create().withMode(CreateMode.EPHEMERAL).forPath(groupName + connectionURL, connectionURL.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
