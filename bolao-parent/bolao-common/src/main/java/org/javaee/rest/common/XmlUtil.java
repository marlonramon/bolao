package org.javaee.rest.common;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class XmlUtil {

	public static String toString(Object entity, boolean formatOutput) {

		StringWriter stringWriter = new StringWriter();

		try {
			
			Map<String, Object> properties = new HashMap<String, Object>();
			
			properties.put(Marshaller.JAXB_FORMATTED_OUTPUT, formatOutput);
			
			JAXBContext jaxbContext = JAXBContext.newInstance(entity.getClass());

			Marshaller marshaller = jaxbContext.createMarshaller();

			marshaller.marshal(entity, stringWriter);

		} catch (JAXBException e) {
			stringWriter.write(e.getMessage());
		}
		return stringWriter.toString();
	}
	
}
