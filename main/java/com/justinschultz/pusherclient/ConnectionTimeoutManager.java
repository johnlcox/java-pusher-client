package com.justinschultz.pusherclient;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ConnectionTimeoutManager {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionTimeoutManager.class);
	private final PusherClient pusherClient;

	private Timer timer;
	private int retryCounter = 1;

	public ConnectionTimeoutManager(PusherClient pusherClient) {
		this.pusherClient = pusherClient;
	}

	public void startTimeoutTimer() {
		if (timer != null) {
			timer.cancel();
		}

		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				retryConnect();
			}
		}, 1000);
	}

	public void stopTimeoutTimer() {
		if (timer != null) {
			timer.cancel();
		}

		retryCounter = 1;
	}

	public void retryConnect() {
		if (timer != null) {
			timer.cancel();
		}

		final int retryDelayMilliseconds = Math.min(retryCounter * 1000, 10000);
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				logger.info("Retrying connection");
				pusherClient.connect();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						retryConnect();
					}
				}, retryDelayMilliseconds);
			}
		}, retryCounter * 1000);

		retryCounter++;
	}
}
