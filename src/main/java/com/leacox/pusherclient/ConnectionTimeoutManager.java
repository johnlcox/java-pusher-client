package com.leacox.pusherclient;

/*	
 *  Copyright (C) 2012 John Leacox
 *  java-pusher-client, a Pusher (http://pusherapp.com) client for Java
 *  
 *  http://john.leacox.com
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License. 
 */

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ConnectionTimeoutManager {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionTimeoutManager.class);
	private static final int INITIAL_CONNECTION_TIMEOUT = 4000;
	private static final int MAX_CONNECTION_TIMEOUT = 10000;
	private static final int CONNECTION_TIMEOUT_INCREMENT = 1000;

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
		}, INITIAL_CONNECTION_TIMEOUT);
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

		final int retryDelayMilliseconds = Math.min(INITIAL_CONNECTION_TIMEOUT + retryCounter
				* CONNECTION_TIMEOUT_INCREMENT, MAX_CONNECTION_TIMEOUT);
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
		}, retryCounter * INITIAL_CONNECTION_TIMEOUT);

		retryCounter++;
	}
}
