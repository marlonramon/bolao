package org.javaee.bolao.config;

public class Config {

	public static long getExpirationLoginTime(){
		return 10 * 60 * 1000; //10 minutes
	}
	
	public static String getPastaImagensBandeiras(){
		return "resources/images/bandeiras/";
	}
	
}
