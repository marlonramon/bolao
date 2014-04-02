package org.javaee.rest.common;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;

public class Encryptor {

	public static String encryptText(String plainText) {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword(Encryptor.class.getSimpleName());
		return textEncryptor.encrypt(plainText);
	}

	public static String decryptText(String encrypted) {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword(Encryptor.class.getSimpleName());
		return textEncryptor.decrypt(encrypted);
	}

	public static String encriptPassword(String userPassword) {
		BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
		String encryptedPassword = passwordEncryptor.encryptPassword(userPassword);
		return encryptedPassword;
	}

	public static boolean checkPassword(String inputPassword, String encryptedPassword) {
		BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
		return passwordEncryptor.checkPassword(inputPassword, encryptedPassword);
	}
}
