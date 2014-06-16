package org.javaee.bolao.config;

import java.io.File;

public class BolaoConfig {

	public static long getExpirationLoginTime(){
		return 10 * 60 * 3000; //30 minutes
	}
	
	public static String getPastaImagensBandeiras(){
		return "resources/images/bandeiras/";
	}
	
	public static File getDiretorioTemporario(){
		File dir = new File("bolao/temp/");
		dir.mkdirs();
		return dir;
	}
	
}
