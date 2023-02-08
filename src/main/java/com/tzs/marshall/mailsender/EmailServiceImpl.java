package com.tzs.marshall.mailsender;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.tzs.marshall.bean.DBProperties;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.constants.RequestTypeDictionary;
import com.tzs.marshall.error.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Objects;

import static com.tzs.marshall.constants.Constants.*;

@Service
public class EmailServiceImpl implements EmailService {

    private final static Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;
    private static final String SUPPORT_NUMBER = DBProperties.properties.getProperty(SUPPORT_MOBILE_NUMBER);
    private static final String SID = DBProperties.properties.getProperty(TWILIO_ACCOUNT_SID);
    private static final String AUTH_TOKEN = DBProperties.properties.getProperty(TWILIO_AUTH_TOKEN);
    private static final String SUP_EMAIL = DBProperties.properties.getProperty(SUPPORT_EMAIL);

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Override
    @Async
    public void send(EmailBean emailBean) {
        int retries = 0;
        boolean sent = Boolean.FALSE;
        while (retries < 3) {
            try {
                log.info("Sending email...");
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setTo(emailBean.getTo());
                helper.setFrom(emailBean.getFrom());
                helper.setSubject(emailBean.getSubject());
                helper.setText(emailBean.getMessage(), true);
                if (emailBean.getAttachment() != null)
                    helper.addAttachment(Objects.requireNonNull(emailBean.getAttachment().getOriginalFilename()), emailBean.getAttachment());
                mailSender.send(mimeMessage);
                log.info("Email Sent successfully!");
                sent = Boolean.TRUE;
                break;
            } catch (Exception e) {
                log.warn(String.format("Failed to send email: %s, Attempt: %s", e.getMessage(), retries));
                retries++;
            }
        }
        if (sent == Boolean.FALSE) {
            log.error(String.format("Unable to send email after %s attempts.", retries));
            throw new ApiException(MessageConstants.EMAIL_FAILED);
        }
    }

    public void sendConfirmationEmail(String email, String token, String url, RequestTypeDictionary requestTypeDictionary) {
        log.info("Preparing email to send...");
        log.info(String.format("URL: %s, Dictionary: %s", url, requestTypeDictionary));
        String msgBody = buildEmail(requestTypeDictionary, token, url);
        EmailBean emailBean = new EmailBean(email, requestTypeDictionary.getMailSubject(), msgBody);
        emailBean.setFrom(SUP_EMAIL);
        send(emailBean);
    }

    @Override
    public void sendOTPToMobile(String mobileNumber, String otp) {
            Twilio.init(SID, AUTH_TOKEN);
            Message message = Message.creator(
//                            new com.twilio.type.PhoneNumber(mobileNumber), //TO
                            new com.twilio.type.PhoneNumber("+918826424940"), //TO
                            new com.twilio.type.PhoneNumber(SUPPORT_NUMBER),//FROM
                            otp)
                    .create();

            log.info("OTP sent to mobile: " + message.getStatus());

    }

    @Override
    public void sendOTPToEmail(String email, String otp, RequestTypeDictionary requestTypeDictionary) {
        log.info("Preparing email to send...");
        String msgBody = "Your 6 digit OTP is: " + otp;
        EmailBean emailBean = new EmailBean(email, requestTypeDictionary.getMailSubject(), msgBody);
        emailBean.setFrom(SUP_EMAIL);
        send(emailBean);
    }

    @Override
    public String buildEmail(RequestTypeDictionary requestTypeDictionary, String token, String url) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">" + requestTypeDictionary.getMailSubject() + "</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hello AESH User,</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">" + requestTypeDictionary.getMailBody() + "</p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + url + requestTypeDictionary.getLink() + token + "\">" + requestTypeDictionary.getMailButton() + "</a> </p></blockquote>\n This link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
