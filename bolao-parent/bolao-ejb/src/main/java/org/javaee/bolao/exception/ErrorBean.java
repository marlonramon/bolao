package org.javaee.bolao.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	private List<String> childMessages = new ArrayList<>();
	
	public ErrorBean() {
	}
	
	public ErrorBean(String message) {
		this.message = message;			
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
