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

import java.io.IOException;

public interface PusherAuthorizor {
	/**
	 * Performs authorization for a pusher channel and returns the authorization token.
	 * 
	 * @param socketId
	 *            The pusher socket Id. (cannot be null)
	 * @param channelName
	 *            The pusher channel. (cannot be null)
	 * @return The authorization token.
	 * @throws IOException
	 *             if I/O related to the authorization process fails.
	 * @throws NullPointerException
	 *             if socketId or channelName is null.
	 */
	public String authorize(String socketId, String channelName) throws IOException;
}
