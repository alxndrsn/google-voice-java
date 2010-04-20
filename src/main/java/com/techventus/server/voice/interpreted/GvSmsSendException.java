/**
 * 
 */
package com.techventus.server.voice.interpreted;

/**
 * Exception thrown when trying to send an SMS.
 * @author Alex Anderson <alex@frontlinesms.com>
 */
@SuppressWarnings("serial")
public class GvSmsSendException extends Exception {
	public GvSmsSendException(String message) {
		super(message);
	}

	public GvSmsSendException(String message, Exception cause) {
		super(message, cause);
	}

	public GvSmsSendException(Exception cause) {
		super(cause);
	}
}
