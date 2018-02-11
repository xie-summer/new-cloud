package com.cloud.util;
/**
 * @author summer
 */
public interface HttpResultCallback {
	/**
	 * @param result
	 */
	void processResult(HttpResult result);

	/**
	 * @param t
	 */
	void processError(Throwable t);
}
