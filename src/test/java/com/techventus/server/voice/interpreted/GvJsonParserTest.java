/**
 * 
 */
package com.techventus.server.voice.interpreted;

import junit.framework.TestCase;
import gvjava.org.json.JSONException;

import com.techventus.server.voice.interpreted.GvJsonParser;
import com.techventus.server.voice.interpreted.GvSmsSendException;
import com.techventus.server.voice.interpreted.GvSmsSendResponse;

/**
 * Unit tests for {@link GvJsonParser}.
 * @author Alex Anderson <alex@frontlinesms.com>
 */
public class GvJsonParserTest extends TestCase {
	private static final String JSON_RESPONSE_OK = "{\"ok\":true,\"data\":{\"code\":0}}\n";
	
	/** Instance of {@link GvJsonParser} to test. */
	private GvJsonParser p;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.p = new GvJsonParser();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		this.p = null;
	}
	
	public void testGetFromJsonResponse() throws GvSmsSendException {
		// Test OK response
		assertEquals(GvSmsSendResponse.SENT_OK, p.parseSendResponse(JSON_RESPONSE_OK));

		// Test bad response
		try {
			p.parseSendResponse("{\"ok\":false}");
		} catch(GvSmsSendException ex) { assertNull(ex.getCause()); }
		
		// Test malformed response
		try {
			p.parseSendResponse("{\"ok\":false"); 
		} catch(GvSmsSendException ex) { assert(ex.getCause() instanceof JSONException); }
	}
}
