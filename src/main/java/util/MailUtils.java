package util;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import com.sun.mail.util.MailSSLSocketFactory;


public class MailUtils {

    public void sentSignUpMail(String mail) throws Exception{
        Message message = initMessage();
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
        message.setSubject(MimeUtility.encodeText("这是一封来自contact的激活邮件",MimeUtility.mimeCharset("utf8"), null));
        String content = "\n" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "\t<meta charset=\"utf-8\">\n" +
                "\t<title>contact</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<p>这是一封来自contact的激活邮件。" + "<br><a href='" + "http://121.42.185.85:8080/contact/ActivateAccount?"+"mail=" + mail + "'>立即激活</a></p>\n" +
                "</body>\n" +
                "</html>";
        
        message.setContent(content, "text/html; charset=UTF-8");
        Transport.send(message);
    }
    
    public void sentIssueMail(String email, String issue) throws Exception{
        Message message = initMessage();
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject(MimeUtility.encodeText("contact用户发现了bug",MimeUtility.mimeCharset("utf8"), null));
        String content = "\n" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "\t<meta charset=\"utf-8\">\n" +
                "\t<title>BITStar</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<p>contact用户发现了bug。" + "<br>\n问题详情：<br>"+issue+"</p>" +
                "</body>\n" +
                "</html>";
        message.setContent(content, "text/html; charset=UTF-8");
        Transport.send(message);
    }
    
    public void sentForgetPasswordMail(String email, String password) throws Exception{
        Message message = initMessage();
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject(MimeUtility.encodeText("contact忘记密码",MimeUtility.mimeCharset("utf8"), null));
        String content = "\n" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "\t<meta charset=\"utf-8\">\n" +
                "\t<title>contact忘记密码</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<p>这是一封来自contact的密码邮件，您的密码为" + password +"</p>\n" +
                "</body>\n" +
                "</html>";
        message.setContent(content, "text/html; charset=UTF-8");
        Transport.send(message);
    }
    
    private Message initMessage() throws Exception {
        String from = "BitStar@lisor.cn";
        String host = "smtp.exmail.qq.com";
        Properties properties = System.getProperties();

        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.auth", "true");

        MailSSLSocketFactory sslSocketFactory = new MailSSLSocketFactory();
        sslSocketFactory.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sslSocketFactory);

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("BitStar@lisor.cn", "Aa1234567");
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        return message;
    }

    public static void main(String[] args) {
		MailUtils ma = new MailUtils();
		try {
			ma.sentForgetPasswordMail("2392737027@qq.com","123");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
}
