/**
 * 
 */
package com.techventus.server.voice.interpreted;

/**
 * Representation of an SMS message in the Google Voice system.
 * @author Alex Anderson <alex@frontlinesms.com>
 */
public class GvSmsMessage {
	private final String sourceNumber;
	private final String destinationNumber;
	private final String messageContent;
	
	public GvSmsMessage(String sourceNumber, String destinationNumber,
			String messageContent) {
		this.sourceNumber = sourceNumber;
		this.destinationNumber = destinationNumber;
		this.messageContent = messageContent;
	}

	public String getDestinationNumber() {
		return destinationNumber;
	}
	
	public String getSourceNumber() {
		return sourceNumber;
	}
	
	public String getMessageContent() {
		return messageContent;
	}
}
