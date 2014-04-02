package org.javaee.rest.common;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import javax.faces.context.FacesContext;

public final class FileUtil {

	public static final String USER_DIR = System.getProperty("user.dir");
	public static final String OS_TEMP_DIR = System.getProperty("java.io.tmpdir");	

	public enum EnumResource {
		ROOT("/"), CSS("/resources/css/"), IMAGES("/resources/images/"), RESOURCES("/resources/");

		private String realPath;

		private EnumResource(String realPath) {
			this.realPath = realPath;
		}

		public String getRelativePath() {
			return realPath;
		}

	};

	public static File getRealPath(EnumResource enumResource, String concatPath) {

		File file = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(enumResource.getRelativePath()));

		file.mkdirs();

		if (concatPath != null) {
			file = new File(file, concatPath);
		}

		return file;

	}

	public static void closeQuiet(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException ignore) {
			}
		}
	}
	
	public static InputStream getInputStream(byte[] byteArray) {
		InputStream byteInputStream = null;
		try {
			byteInputStream = new ByteArrayInputStream(byteArray);
			byteInputStream.read(byteArray, 0, byteArray.length);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return byteInputStream;
	}

	public static byte[] getBytes(File file) {
		byte[] bytes = null;
		FileInputStream is = null;
		FileChannel ch = null;
		try {
			is = new FileInputStream(file);
			ch = is.getChannel();
			int size = (int) ch.size();
			MappedByteBuffer buf = ch.map(MapMode.READ_ONLY, 0, size);
			bytes = new byte[size];
			buf.get(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeQuiet(is);
			closeQuiet(ch);
		}
		return bytes;
	}
	
	public static String getOSTempDir(){
		String tempDir = OS_TEMP_DIR;
		
		if(tempDir.charAt(tempDir.length()-1) != File.separatorChar){
			tempDir += File.separatorChar;
		}
		
		return tempDir;
	}

}
