package com.iterror.game.common.tcp.connection;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tony.yan on 2017/9/1.
 */
public class MyConnectionListener {

    private static final Logger                              logger                 = LoggerFactory.getLogger(MyConnectionListener.class);
    private static final ConcurrentMap<String, MyConnection> connections            = new ConcurrentHashMap<String, MyConnection>();
    private static final ConcurrentMap<String, MyConnection> connectionsWithNameKey = new ConcurrentHashMap<String, MyConnection>();

    public static void connectionCreated(final MyConnection connection) {
        if (connections.putIfAbsent(connection.getID(), connection) != null) {
            throw new IllegalArgumentException("Connection already exists with id " + connection.getID());
        }

    }

    public static void connectionDestroyed(final String connectionID) {
        MyConnection connection = connections.get(connectionID);
        if (connection != null) {
            if (connection.getName() != null) {
                MyConnection namedConnection = connectionsWithNameKey.get(connection.getName());
                if (namedConnection != null && namedConnection.getID() == connection.getID()) {
                    connectionsWithNameKey.remove(connection.getName());
                }
            }
            connection.close();
        }
        connections.remove(connectionID);
    }

    public static MyConnection getMyConnectionBySocketId(String connectionID) {
        return connections.get(connectionID);
    }

    /**
     * 把经过验证的长连接以用户名作为key保存 返回值null说明这个用户没有在其它地方登录，非空表示在其它地方登录着，推下线消息
     **/
    public static MyConnection addNamedConnection(final MyConnection connection) {
        MyConnection oldConnection = connectionsWithNameKey.get(connection.getName());
        connectionsWithNameKey.put(connection.getName(), connection);
        return oldConnection;
    }

    public static MyConnection getMyConnectionByName(String name) {
        return connectionsWithNameKey.get(name);
    }

    public static ConcurrentMap<String, MyConnection> getConnections() {
        return connections;
    }

    public static ConcurrentMap<String, MyConnection> getNamedConnections() {
        return connectionsWithNameKey;
    }
}
