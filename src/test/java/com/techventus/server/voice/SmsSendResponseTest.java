/**
 * 
 */
package com.techventus.server.voice;

import gvjava.org.json.JSONException;
import junit.framework.TestCase;

import com.techventus.server.voice.interpreted.SmsSendException;
import com.techventus.server.voice.interpreted.SmsSendResponse;

/**
 * Unit tests for {@link SmsSendResponseTest}.
 * @author aga
 */
public class SmsSendResponseTest extends TestCase {
	private static final String JSON_RESPONSE_OK = "{\"ok\":true,\"data\":{\"code\":0}}\n";
	
	public void testGetFromJsonResponse() throws SmsSendException {
		// Test OK response
		assertEquals(SmsSendResponse.SENT_OK, SmsSendResponse.getFromJsonResponse(JSON_RESPONSE_OK));

		// Test bad response
		try {
			SmsSendResponse.getFromJsonResponse("{\"ok\":false}");
		} catch(SmsSendException ex) { assertNull(ex.getCause()); }
		
		// Test malformed response
		try {
			SmsSendResponse.getFromJsonResponse("{\"ok\":false"); 
		} catch(SmsSendException ex) { assert(ex.getCause() instanceof JSONException); }
	}
}
