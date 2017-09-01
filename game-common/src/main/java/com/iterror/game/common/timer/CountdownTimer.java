package com.iterror.game.common.timer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountdownTimer {

	private static final Logger logger		= LoggerFactory.getLogger(CountdownTimer.class);

	private static CountdownTimer countDownTimer;

	private ScheduledExecutorService						exec;

	private int																	tPoolSize	= 25;

	private Map<String, ScheduledFuture<?>>			scheduledFutureMap;

	private Map<String, Long>										timeRemainMap;

	private Map<String, ISimpleTimeoutListener>	listenerMap;

	private int																	mapSize		= 512;

	private void init() {
		exec = Executors.newScheduledThreadPool(tPoolSize);
		scheduledFutureMap = new ConcurrentHashMap<String, ScheduledFuture<?>>(mapSize, 2);
		timeRemainMap = new ConcurrentHashMap<String, Long>(mapSize, 2);
		listenerMap = new ConcurrentHashMap<String, ISimpleTimeoutListener>(mapSize, 2);
	}

	private CountdownTimer(int poolSize) {
		this.tPoolSize = poolSize;
		this.init();
	}

	private CountdownTimer() {
		this.init();
	}

	public static CountdownTimer getInstance() {
		if (countDownTimer == null) {
			countDownTimer = new CountdownTimer();
		}
		return countDownTimer;
	}

	public static CountdownTimer getInstance(int poolSize) {
		if (countDownTimer == null) {
			countDownTimer = new CountdownTimer(poolSize);
		}
		return countDownTimer;
	}

	/**
	 * 单纯的倒计时
	 *
	 * @param timeout
	 * @param listener
	 */
	public void scheduleTimeOut(long timeout, final ISimpleTimeoutListener listener) {
		this.exec.schedule(new Runnable() {

			public void run() {
				listener.onTimeout();

			}
		}, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 添加一个可暂停，继续的 处于暂停状态的倒计时，
	 *
	 * @param key
	 *          倒计时的key
	 * @param timeout
	 *          总的倒计时时间(秒)
	 * @param listener
	 *          在时间到时的监听器
	 */
	public void addCountDownTimer(final String key, long timeout, final ISimpleTimeoutListener listener) {
		stop(key);
		timeRemainMap.put(key, timeout);
		listenerMap.put(key, listener);
	}

	public Long getTimeOut(String key){
		return timeRemainMap.get(key);
	}

	/**
	 * 可以暂停，继续的倒计时
	 *
	 * @param skyId
	 * @param timeout
	 *          超时时间，秒级
	 */
	public void scheduleCountDown(final String key, long timeout, final ISimpleTimeoutListener listener) {
		stop(key);
		ScheduledFuture<?> lunchCheckFuture = this.exec.schedule(new Runnable() {

			public void run() {
				scheduledFutureMap.remove(key);
				timeRemainMap.remove(key);
				listenerMap.remove(key);
				listener.onTimeout();

			}
		}, timeout, TimeUnit.SECONDS);
		this.scheduledFutureMap.put(key, lunchCheckFuture);
		listenerMap.put(key, listener);
	}

	/**
	 * 获取某个Key对象的倒计时对象的剩余时间
	 *
	 * @param key
	 * @return
	 */
	public long getTimeLeft(String key) {
		ScheduledFuture<?> lunchCheckFuture = this.scheduledFutureMap.get(key);

		if (lunchCheckFuture == null) {
			Long remain = timeRemainMap.get(key);
			if (remain == null) {
				logger.error("try to get time left from a null count down timer. key[{}]", key);
				return 0;
			}
			return remain;
		}
		return lunchCheckFuture.getDelay(TimeUnit.SECONDS);
	}

	/**
	 * 暂停某个倒计时
	 *
	 * @param key
	 */
	public void pause(String key) {
		ScheduledFuture<?> lunchCheckFuture = this.scheduledFutureMap.get(key);
		if (lunchCheckFuture == null) {
			logger.error("try to parse a null count down timer. key[{}]", key);
			return;
		}
		long remain = lunchCheckFuture.getDelay(TimeUnit.SECONDS);
		lunchCheckFuture.cancel(true);
		scheduledFutureMap.remove(key);
		lunchCheckFuture = null;
		this.timeRemainMap.put(key, remain);
	}

	/**
	 * 开始计时
	 *
	 * @param key
	 */
	public void start(String key) {
		this.resume(key);
	}

	/**
	 * 继续计时
	 *
	 * @param key
	 */
	public void resume(String key) {
		Long remain = timeRemainMap.get(key);
		if (remain == null) {
			logger.error("try to resume a null count down timer, key[{}]", key);
			return;
		}
		final ISimpleTimeoutListener listener = this.listenerMap.get(key);
		if (listener == null) {
			logger.error("try to resume a count down timer, but no listener found, key[{}]", key);
		}
		this.scheduleCountDown(key, remain, listener);
	}

	/**
	 * 停止计时器，删除相应的Listener
	 * 
	 * @param key
	 */
	public void stop(String key) {
		ScheduledFuture<?> lunchCheckFuture = this.scheduledFutureMap.get(key);
		if (lunchCheckFuture != null) {
			lunchCheckFuture.cancel(true);
		}
		scheduledFutureMap.remove(key);
		timeRemainMap.remove(key);
		listenerMap.remove(key);
	}

	public void settPoolSize(int tPoolSize) {
		this.tPoolSize = tPoolSize;
	}
}
