package org.javaee.bolao.exception;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
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

}
