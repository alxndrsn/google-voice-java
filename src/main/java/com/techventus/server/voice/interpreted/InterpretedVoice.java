/**
 * 
 */
package com.techventus.server.voice.interpreted;

import java.io.IOException;

import com.techventus.server.voice.Voice;

/**
 * Wrapper for an instance of {@link Voice} which implements interpretation of the JSON responses in {@link Voice} for
 * a subset of the API features.
 * @author Alex Anderson <alex@frontlinesms.com>
 */
public class InterpretedVoice {
	/** Instance of {@link Voice} that this {@link InterpretedVoice} wraps. */
	private final Voice voice;
	
	/** Create a new {@link InterpretedVoice} wrapping {@link #voice} */
	public InterpretedVoice(Voice voice) {
		this.voice = voice;
	}
	
	/**
	 * Send an SMS
	 * 
	 * @param destinationNumber
	 *            the destination number
	 * @param txt
	 *            the Text of the message. Messages longer than the allowed
	 *            character length will be split into multiple messages.
	 * @return
	 */
	public SmsSendResponse sendSms(String destinationNumber, String txt) throws SmsSendException {
		try {
			String jsonResponse = voice.sendSMS(destinationNumber, txt);
			return SmsSendResponse.getFromJsonResponse(jsonResponse);
		} catch (IOException ex) {
			throw new SmsSendException(ex);
		}
	}
}
