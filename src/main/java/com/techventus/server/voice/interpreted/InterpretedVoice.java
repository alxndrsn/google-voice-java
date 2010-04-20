/**
 * 
 */
package com.techventus.server.voice.interpreted;

import java.io.IOException;
import java.util.List;

import com.techventus.server.voice.Voice;

/**
 * Wrapper for an instance of {@link Voice} which implements interpretation of the JSON responses in {@link Voice} for
 * a subset of the API features.
 * @author Alex Anderson <alex@frontlinesms.com>
 */
public class InterpretedVoice {
	/** {@link GvJsonParser} for this class */
	private final GvJsonParser jsonParser = new GvJsonParser();
	/** {@link GvJsonExtractor} for this class */
	private final GvJsonExtractor jsonExtractor = new GvJsonExtractor();
	/** Instance of {@link Voice} that this {@link InterpretedVoice} wraps. */
	private final Voice voice;
	
//> CONSTRUCTORS
	/** Create a new {@link InterpretedVoice} wrapping {@link #voice} */
	public InterpretedVoice(Voice voice) {
		this.voice = voice;
	}
	
//> PUBLIC METHODS
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
	public GvSmsSendResponse sendSms(String destinationNumber, String txt) throws GvSmsSendException {
		try {
			String jsonResponse = voice.sendSMS(destinationNumber, txt);
			return jsonParser.parseSendResponse(jsonResponse);
		} catch (IOException ex) {
			throw new GvSmsSendException(ex);
		}
	}
	
	public List<GvSmsMessage> receiveSms() throws GvSmsReceiveException {
		try {
			String xmlResponse = this.voice.getSMS();
			String jsonResponse = this.jsonExtractor.getJsonResponse(xmlResponse);
			return jsonParser.parseMessages(jsonResponse);
		} catch (IOException ex) {
			throw new GvSmsReceiveException(ex);
		}
	}
}
