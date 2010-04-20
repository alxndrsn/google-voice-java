/**
 * 
 */
package com.techventus.server.voice.interpreted;

/**
 * Exception thrown when trying to read the SMS inbox.
 * @author Alex Anderson <alex@frontlinesms.com>
 */
@SuppressWarnings("serial")
public class GvSmsReceiveException extends Exception {
	public GvSmsReceiveException(Exception cause) {
		super(cause);
	}
}
