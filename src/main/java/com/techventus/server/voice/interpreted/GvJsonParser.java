/**
 * 
 */
package com.techventus.server.voice.interpreted;

import gvjava.org.json.JSONException;
import gvjava.org.json.JSONObject;
import gvjava.org.json.JSONTokener;

import java.util.List;

/**
 * Parser for JSON responses from the Google Voice web service.
 * @author Alex Anderson <alex@frontlinesms.com>
 */
public class GvJsonParser {
//> STATIC CONSTANTS
	/** data.code value when SMS was sent OK */
	private static int RESPONSE_CODE_OK = 0;

	public GvSmsSendResponse parseSendResponse(String jsonResponse) throws GvSmsSendException {
		JSONTokener tokenizer = new JSONTokener(jsonResponse);
		try {
			JSONObject o = new JSONObject(tokenizer);
			if(!o.getBoolean("ok")) {
				throw new GvSmsSendException("Response not OK.");
			} else {
				o = o.getJSONObject("data");
				int responseCode = o.getInt("code");
				if(responseCode == RESPONSE_CODE_OK) {
					return GvSmsSendResponse.SENT_OK;
				}
			}
		} catch (JSONException ex) {
			throw new GvSmsSendException("Malformed response from server.", ex);
		}
		
		throw new GvSmsSendException("Response not recognized.");
	}
	
	/**
	 * Reads a list of {@link GvSmsMessage} from a JSON response from the Google Voice
	 * web service.
	 */
	List<GvSmsMessage> parseMessages(String jsonResponse) {
		// TODO Auto-generated method stub
		return null;
	}
}
