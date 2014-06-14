package org.javaee.bolao.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.javaee.bolao.exception.BolaoRuntimeException;

/**
 * CUIDADO - o Avast bloqueia o envio de email
 * 
 */

@Stateless
public class NotificadorEmail {

	private static final String ARQUIVO_PROPRIEDADES_EMAIL = "email.properties";

	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void enviar(String subject, String to, String messageText) {
		enviar(subject, to, messageText, null, null);
	}

	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void enviar(String subject, String to, String messageText, File[] anexos) {
		enviar(subject, to, messageText, null, anexos);
	}

	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void enviar(String subject, String to, String messageText, String replyTo, File[] anexos) {

		try {

			Session session = getSession();

			session.setDebug(true);

			Message message = new MimeMessage(session);
			message.setSubject(subject);
			message.setRecipient(RecipientType.TO, new InternetAddress(to));
			message.setFrom(new InternetAddress(session.getProperty("mail.from")));

			if (replyTo != null) {
				message.setReplyTo(new InternetAddress[] { new InternetAddress(replyTo) });
			}

			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setText(messageText);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			if (anexos != null) {
				for (File anexo : anexos) {
					MimeBodyPart attachPart = new MimeBodyPart();
					attachPart.attachFile(anexo);
					multipart.addBodyPart(attachPart);
				}
			}

			message.setContent(multipart);

			System.out.println("Try Sending Mail To: " + to);

			Transport.send(message);

			System.out.println("Mail Sent.");

		} catch (Exception e) {
			throw new BolaoRuntimeException(e, "Erro ao enviar o email. Motivo: {0}", e.getMessage());
		}
	}

	public Session getSession() {

		Properties props = lerArquivoPropriedadesEmail();

		Session mailSession = javax.mail.Session.getInstance(props);

		return mailSession;
	}

	private Properties lerArquivoPropriedadesEmail() {

		Properties properties = new Properties();

		File file = new File("bolao/" + ARQUIVO_PROPRIEDADES_EMAIL);

		try {

			if (file.exists()) {

				FileInputStream fileInputStream = new FileInputStream(file);
				properties.load(fileInputStream);
				fileInputStream.close();

			} else {
				InputStream resourceAsStream = getClass().getResourceAsStream(ARQUIVO_PROPRIEDADES_EMAIL);
				if (resourceAsStream != null) {
					properties.load(resourceAsStream);
				}
			}

		} catch (Exception e) {
			throw new BolaoRuntimeException(e);
		}

		return properties;
	}

}
