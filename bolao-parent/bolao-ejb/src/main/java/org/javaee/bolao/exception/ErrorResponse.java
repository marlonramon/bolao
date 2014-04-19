package org.javaee.bolao.exception;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	private List<String> childMessages = new ArrayList<>();
	
	public ErrorResponse() {
	}
	
	public ErrorResponse(String message) {
		this.message = message;			
	}
	
	public ErrorResponse(String message, Object ... arguments) {
		this.message = MessageFormat.format(message, arguments);			
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public boolean addChildMessage(String e) {
		return childMessages.add(e);
	}
	
	public List<String> getChildMessages() {
		return childMessages;
	}
	
	
}
