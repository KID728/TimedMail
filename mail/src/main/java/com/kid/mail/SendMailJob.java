package com.kid.mail;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.util.MailSSLSocketFactory;

public class SendMailJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(SendMailJob.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("[SendMailJob][execute] SendMailJob execute");
        try {
            sendMail();
        } catch (GeneralSecurityException e) {
            log.error("[SendMailJob][execute] catch a general security exception", e);
        }
    }

    private void sendMail() throws GeneralSecurityException {
        String to = "284031995@qq.com";
        String from = "632835699@qq.com";
        String host = "smtp.qq.com";

        Properties properties = System.getProperties();

        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");

        // properties.setProperty("mail.user", "kid");
        // properties.setProperty("mail.password", "728");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("632835699@qq.com", "ftip********bfag"); // 发件人邮件用户名、授权码
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Hi!");
            message.setText("It’s time to pay vutlr!");

            Transport.send(message);
            log.info("[SendMailJob][sendMail] Sent message successfully");
        } catch (MessagingException e) {
            log.error("[SendMailJob][sendMail] Failed to send message", e);
        }
    }
}
