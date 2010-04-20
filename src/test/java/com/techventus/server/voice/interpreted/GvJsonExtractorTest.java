/**
 * 
 */
package com.techventus.server.voice.interpreted;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.TestCase;

/**
 * @author Alex Anderson <alex@frontlinesms.com>
 */
public class GvJsonExtractorTest extends TestCase {
	public void testGetJsonResponse() {
		GvJsonExtractor x = new GvJsonExtractor();
		
		// Load all test files from the classpath and make sure the XML extracts as expected
		int testNumber = 1;
		while(true) {
			String xml = loadTestXml(testNumber);
			String expectedJson = loadJson(testNumber);
			if(xml == null && expectedJson == null) {
				// We've run out of tests
				break;
			}
			expectedJson = expectedJson.trim(); // Trim this as this is how we expect the extractor to behave
			
			assertEquals("Incorrect JSON extracted in test #" + testNumber, expectedJson, x.getJsonResponse(xml));

			// Do the next test
			++testNumber;
		}
	}

	
	private String loadJson(int testNumber) {
		try {
			InputStream resource = getResource(testNumber, "json");
			if(resource == null) {
				return null;
			} else {
				return getStreamAsString(resource);
			}
		} catch (IOException e) {
			return null;
		}
	}

	private String loadTestXml(int testNumber) {
		try {
			InputStream resource = getResource(testNumber, "xml");
			if(resource == null) {
				return null;
			} else {
				return getStreamAsString(resource);
			}
		} catch (IOException e) {
			return null;
		}
	}

	private String getStreamAsString(InputStream resource) throws IOException {
		InputStreamReader isr = null;
		BufferedReader reader = null;
		try {
			isr = new InputStreamReader(resource, "UTF-8");
			reader = new BufferedReader(isr);
			
			StringBuilder bob = new StringBuilder();
			String line;
			while((line = reader.readLine()) != null) {
				bob.append(line);
				bob.append('\n');
			}
			return bob.toString();
		} finally {
			if(reader != null) reader.close();
			if(isr != null) isr.close();
		}
	}

	private InputStream getResource(int resourceNumber, String resourceExtension) {
		return this.getClass().getResourceAsStream(this.getClass().getSimpleName() + '.' + resourceNumber + '.' + resourceExtension);
	}
}
