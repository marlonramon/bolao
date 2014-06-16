package org.javaee.bolao.util;


public final class StringUtil {

	public static Long toNumber(String text) throws NumberFormatException{
		if(text == null || text.trim().isEmpty()){
			return null;
		}
		try{
		return Long.parseLong(text.trim());
		}catch (NumberFormatException e) {
			throw new NumberFormatException(text+" não é um número válido.");
		}
				
	}
	
	public static String removeNonDigit(String text){
		if(text == null || text.trim().isEmpty()){
			return null;
		}
		
		StringBuilder sb = new StringBuilder(text.length());
		
		for(char c: text.toCharArray()){
			if(Character.isDigit(c)){
				sb.append(c);
			}
		}
				
		return sb.toString();
	}
	
	
	public static String wrapLineAsHtml(String text){
		if(text == null){
			return text;
		}
		return text.replaceAll("\\n", "<br/>");
	}
	
	public static boolean isNullOrEmpty(String str){
		return str == null || str.trim().length() == 0;
	}
	
	public static String setMaxLength(String str, int length){
		if(str != null && str.length() > length){
			str = str.substring(0,length);
		}
		
		return str;
	}
	
	public static void main(String[] args) {
		
		String str = "123456";
		
		System.out.println(setMaxLength(str, 5));
		
	}
	
}
