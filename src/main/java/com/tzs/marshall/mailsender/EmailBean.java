package com.tzs.marshall.mailsender;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailBean {
    private String to;
    private String from;
    private String subject;
    private String issue;
    private String question;
    private String message;
    private MultipartFile attachment;

    public EmailBean(String to, String subject, String message) {
        this.to = to;
        this.subject = subject;
        this.message = message;
    }
}
