
package org.javaee.bolao.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ExceptionUtil {
	
	public static String toString(Throwable exception) {
		if(exception == null){
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		
		StringBuilder messages = new StringBuilder();
		messages.append("\n====================================\n");
		while(exception != null){
			messages.append("\n"+exception.getMessage()+"\n");
			exception.printStackTrace(ps);
			exception = exception.getCause();
		}
		messages.append("\n====================================\n");
		return messages.append("\n").append(baos.toString()).toString();
	}
	
}
