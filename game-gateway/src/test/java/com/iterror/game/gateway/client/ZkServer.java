package com.iterror.game.gateway.client;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

import java.util.List;
import java.util.Random;

/**
 * Created by tony.yan on 2017/9/3.
 */
public class ZkServer {

    private static final String SERVER_PATH = "game";

    private static CuratorFramework createCurator(String connectionURL) {

        return CuratorFrameworkFactory.builder().connectString(connectionURL).namespace(SERVER_PATH).connectionTimeoutMs(50000).retryPolicy(new RetryNTimes(Integer.MAX_VALUE,
                                                                                                                                                            20000)).sessionTimeoutMs(50000).build();

    }

    public static String getServerPath() {
        Random random = new Random();
        List<String> list = null;
        CuratorFramework framework = createCurator("127.0.0.1:2181");
        try {

            framework.start();

            list = framework.getChildren().forPath("/game-gateway");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            framework.close();
        }

        System.out.println("chect list " + list.get(0));
        if (list != null && !list.isEmpty()) {
            int number = random.nextInt(100000000);

            int n = number % list.size();

            return list.get(n);
        } else {
            return "";
        }
    }
}
