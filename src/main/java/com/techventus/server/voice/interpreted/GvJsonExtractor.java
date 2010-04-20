/**
 * 
 */
package com.techventus.server.voice.interpreted;

/**
 * Extracts JSON from the body of a Google Voice XML response.
 * @author Alex Anderson <alex@frontlinesms.com>
 */
public class GvJsonExtractor {
	private static final String START_MARKER = "<json><![CDATA[";
	private static final String END_MARKER = "]]></json>";
	
	public String getJsonResponse(String xmlResponse) {
		int start = xmlResponse.indexOf(START_MARKER) + START_MARKER.length();
		int end = xmlResponse.indexOf(END_MARKER);
		return xmlResponse.substring(start, end).trim();
	}
}
