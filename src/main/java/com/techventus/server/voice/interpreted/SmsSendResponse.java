/**
 * 
 */
package com.techventus.server.voice.interpreted;

import gvjava.org.json.JSONException;
import gvjava.org.json.JSONObject;
import gvjava.org.json.JSONTokener;

/**
 * Response returned when {@link InterpretedVoice#sendSms(String, String)} is called.
 * @author Alex Anderson <alex@frontlinesms.com>
 */
public enum SmsSendResponse {
	
//> ENUM VALUES
	/** Response: SMS sent OK */
	SENT_OK;
	
//> STATIC CONSTANTS
	/** data.code value when SMS was sent OK */
	private static int RESPONSE_CODE_OK = 0;

	public static SmsSendResponse getFromJsonResponse(String jsonResponse) throws SmsSendException {
		JSONTokener tokenizer = new JSONTokener(jsonResponse);
		try {
			JSONObject o = new JSONObject(tokenizer);
			if(!o.getBoolean("ok")) {
				throw new SmsSendException("Response not OK.");
			} else {
				o = o.getJSONObject("data");
				int responseCode = o.getInt("code");
				if(responseCode == RESPONSE_CODE_OK) {
					return SENT_OK;
				}
			}
		} catch (JSONException ex) {
			throw new SmsSendException("Malformed response from server.", ex);
		}
		
		throw new SmsSendException("Response not recognized.");
	}

}
