package com.iterror.game.gateway.tcp.message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.iterror.game.common.tcp.closure.MessageClosureRegistry;
import com.iterror.game.common.tcp.closure.MessageCourse;
import com.iterror.game.gateway.tcp.bto.xip.AuthReq;
import com.iterror.game.gateway.tcp.bto.xip.HeartbeatReq;
import com.iterror.game.gateway.tcp.bto.xip.ReturnMsgReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iterror.game.common.tcp.msg.BaseMsg;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 消息管理 Created by tony.yan on 2017/9/1.
 */
public class MessageManager {

    private static final Logger                 logger    = LoggerFactory.getLogger(MessageManager.class);

    public final static int                     queueSize = 10240;

    // 连接服务器要处理的消息
    private static LinkedBlockingQueue<BaseMsg> linkQueue = new LinkedBlockingQueue<BaseMsg>(queueSize);

    private static ExecutorService              pool;

    @Autowired
    private static MessageClosureRegistry       messageClosureRegistry;

    public static void start() {
        ThreadFactory factory = new ThreadFactory() {

            public Thread newThread(Runnable r) {
                Thread s = Executors.defaultThreadFactory().newThread(r);
                s.setName("LinkMesageManagerWorker");
                s.setDaemon(true);
                return s;
            }
        };
        pool = Executors.newCachedThreadPool(factory);
        for (int i = 0; i <= 5; i++) {
            pool.submit(new GatewayThread());
        }
    }

    public static void addReceivedMessage(BaseMsg message) {
        if (message != null) {
            try {
                boolean success = linkQueue.offer(message, 5, TimeUnit.SECONDS);
                if (false == success) {
                    logger.error("insert into receivedQueen failed,msg=" + message.getMsgCode());
                }
            } catch (InterruptedException e) {
                logger.error("error call receivedQueen offer,ERROR:", e);
            }
        }
        return;
    }

    public static BaseMsg waitForProcessMessage() {
        BaseMsg message = null;
        while (message == null) {
            try {
                message = linkQueue.poll(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                logger.error("error call receivedQueen poll,ERROR:", e);
            }
        }
        return message;
    }

    private static class GatewayThread implements Runnable {

        @SuppressWarnings({ "unchecked", "rawtypes" })
        public void run() {
            try {
                while (true) {
                    try {
                        BaseMsg message = waitForProcessMessage();
                        MessageCourse handlerh = messageClosureRegistry.getHandlerFor(message);
                        handlerh.execute(message);
                    } catch (Exception e) {
                        logger.error("LinkThread message onHandler unknown error==>", e);
                    }
                }
            } catch (Exception e) {
                logger.error("LinkThread  error==>", e);
            }
        }
    }
}
