package app.helper;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class MailHelper {
    
	public static void sendMail(String subject, String recipent, String content) throws MessagingException{
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
 
                
		Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
                                @Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("hawarcadestation","Hawarcade22!");
				}
			});

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("HAWArcadeStation@googlemail.com"));
                message.setRecipients(Message.RecipientType.TO,
                                InternetAddress.parse(recipent));
                message.setSubject(subject);
                message.setContent(content, "text/html");

                Transport.send(message);
	}
        
        public static Boolean checkFormat(String mail){
            //String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@haw-hamburg.de";
            String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@web.de";
            Boolean valid = mail.matches(EMAIL_REGEX);
            return valid;
        }
        
}