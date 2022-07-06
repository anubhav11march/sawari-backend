package com.ether.author.mailsender;

import com.ether.author.bean.AESHProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Configuration
@DependsOn({"initProperties"})
public class EmailConfig {
    private static final Logger log = LoggerFactory.getLogger(EmailConfig.class);
    @Bean
    public JavaMailSender getJavaMailSender() {
        log.info("initializing JavaMailSender Bean...");
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(465);

        mailSender.setUsername(AESHProperties.SUPPORT_EMAIL);
        mailSender.setPassword(AESHProperties.SUPPORT_EMAIL_PASSWORD);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(AESHProperties.SUPPORT_EMAIL, AESHProperties.SUPPORT_EMAIL_PASSWORD);
                    }
                });
        log.info("JavaMailSender initialized");
        log.info(String.format("Properties: %s", props));
        log.info(String.format("MailSenderProps: %s, Username: %s, Password: %s", mailSender.getJavaMailProperties(), mailSender.getUsername(), mailSender.getPassword()));
        return mailSender;
    }
}
