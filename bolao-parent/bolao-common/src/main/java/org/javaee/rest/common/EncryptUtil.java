package org.javaee.rest.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.jasypt.util.text.StrongTextEncryptor;

public class EncryptUtil {

	public static void main(String[] args) throws ParseException {
		
		
		Long time = new SimpleDateFormat("dd/MM/yyyy").parse("30/10/2013").getTime();
		
		StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
		textEncryptor.setPassword("admin");
		
		//textEncryptor.de
		
	}
	
}
