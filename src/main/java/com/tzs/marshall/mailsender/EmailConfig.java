package com.tzs.marshall.mailsender;

import com.tzs.marshall.bean.DBProperties;
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

import static com.tzs.marshall.constants.Constants.*;

@Configuration
@DependsOn({"initProperties"})
public class EmailConfig {
    private static final Logger log = LoggerFactory.getLogger(EmailConfig.class);
    @Bean
    public JavaMailSender getJavaMailSender() {
        log.info("initializing JavaMailSender Bean...");
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
//        mailSender.setHost("smtp.hostinger.com");
        mailSender.setPort(465);

        mailSender.setUsername(DBProperties.properties.getProperty(ADMIN_EMAIL));
        mailSender.setPassword(DBProperties.properties.getProperty(ADMIN_EMAIL_PASSWORD));

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
                        return new PasswordAuthentication(DBProperties.properties.getProperty(ADMIN_EMAIL), DBProperties.properties.getProperty(ADMIN_EMAIL_PASSWORD));
                    }
                });
        log.info("JavaMailSender initialized");
        log.info(String.format("Properties: %s", props));
        log.info(String.format("MailSenderProps: %s, Username: %s, Password: %s", mailSender.getJavaMailProperties(), mailSender.getUsername(), mailSender.getPassword()));
        return mailSender;
    }
}
