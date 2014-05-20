package com.course.mvp.demo.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void checkAccount(String username, String password,
			AsyncCallback<String> callback);

	void setLevelCompleted(int levelCompleted, AsyncCallback<Integer> callback);

	void getLevelCompleted(AsyncCallback<Integer> callback);
}
