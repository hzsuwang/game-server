package com.iterror.game.common.timer;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kantop.qp.common.util.QpDateUtil;

public class GameTimer {

    private static final Logger logger          = LoggerFactory.getLogger(GameTimer.class);

    private long                            checkInterval   = 10;
    private ScheduledExecutorService        exec            = null;
    private ConcurrentHashMap<Object, Date> timer           = new ConcurrentHashMap<Object, Date>();
    private int                             timeOutInteval  = 60;
    private ITimeoutListener                timeoutListener = null;
    private String                          regionName      = "gameTimer";

    public void start() {

        exec = Executors.newSingleThreadScheduledExecutor(new NamedPoolThreadFactory(regionName));

        exec.scheduleWithFixedDelay(new Runnable() {

            public void run() {
                try {
                    Date now = new Date();
                    for (Map.Entry<Object, Date> action : timer.entrySet()) {
                        Object key = action.getKey();
                        Date startTime = action.getValue();

                        if (QpDateUtil.getTimeIntervalSeconds(startTime, now) > timeOutInteval) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("regionName=[{}],key=[{}] exceed the max inteval=[{}]. ", new Object[] { regionName, key, timeOutInteval });
                            }
                            timer.remove(key);
                            timeoutListener.onTimeout(key);
                        }
                    }
                } catch (Throwable t) {
                    logger.error("", t);
                }
            }
        }, checkInterval, checkInterval, TimeUnit.SECONDS);

        logger.info("regionName=[{}] timer started...", regionName);
    }

    public void add(Object key) {
        timer.put(key, new Date());
    }

    public void remove(Object key) {
        timer.remove(key);
    }

    public void update(Object key, Date time) {
        timer.put(key, time);
    }

    public void stop() {
        exec.shutdownNow();
    }

    public void setCheckInterval(long checkInterval) {
        this.checkInterval = checkInterval;
    }

    public void setTimeOutInteval(int timeOutInteval) {
        this.timeOutInteval = timeOutInteval;
    }

    public void setTimeoutListener(ITimeoutListener timeoutListener) {
        this.timeoutListener = timeoutListener;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

}
