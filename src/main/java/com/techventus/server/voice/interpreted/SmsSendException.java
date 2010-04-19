/**
 * 
 */
package com.techventus.server.voice.interpreted;

/**
 * Exception thrown when trying to send an SMS.
 * @author Alex Anderson <alex@frontlinesms.com>
 */
@SuppressWarnings("serial")
public class SmsSendException extends Exception {
	public SmsSendException(String message) {
		super(message);
	}

	public SmsSendException(String message, Exception cause) {
		super(message, cause);
	}

	public SmsSendException(Exception cause) {
		super(cause);
	}
}
