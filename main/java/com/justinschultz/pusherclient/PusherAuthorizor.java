package com.justinschultz.pusherclient;

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
