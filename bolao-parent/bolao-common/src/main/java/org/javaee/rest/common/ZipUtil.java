package org.javaee.rest.common;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

	public static List<File> unzip(InputStream inputStream, String destination) throws IOException {
		ZipInputStream zipIn = new ZipInputStream(inputStream);
		ZipEntry entry = null;
		byte[] dados = new byte[524288];

		List<File> listOfFile = new ArrayList<File>();
		
		while ((entry = zipIn.getNextEntry()) != null) {
			String caminho = destination + entry.getName();
			// Buffer com 512KB
			BufferedOutputStream bufferOut = new BufferedOutputStream(new FileOutputStream(caminho), dados.length);

			while (true) {
				int amount = zipIn.read(dados);

				if (amount == -1) {
					break;
				}

				bufferOut.write(dados, 0, amount);
			}
			listOfFile.add(new File(caminho));
			
			bufferOut.flush();
			bufferOut.close();

			zipIn.closeEntry();
		}
		zipIn.close();

		return listOfFile;
	}

	public static byte[] zip(File... files) throws IOException {
		ByteArrayOutputStream zip;
		ZipOutputStream zipOut = null;

		try {
			// Instancia de uma stream de escrita com buffer de 1 MB
			zip = new ByteArrayOutputStream(1048576);
			// FileOutputStream fileOut = new FileOutputStream("");
			// Instancia da classe responsavel pela compres�o
			zipOut = new ZipOutputStream(zip);

			for (File file : files) {
				zipOut.putNextEntry(new ZipEntry(file.getName()));
				zipOut.write(Files.readAllBytes(file.toPath()));
			}
			
			zipOut.flush();
			
		} finally {
			if (zipOut != null) {
				zipOut.close();
			}
		}

		return zip.toByteArray();
	}
}