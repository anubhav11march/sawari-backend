package com.tzs.marshall.author.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ApiExceptionBean {
    private String messgae;
    private HttpStatus httpStatus;
    private ZonedDateTime zonedDateTime;

    public ApiExceptionBean(String messgae, HttpStatus httpStatus, ZonedDateTime zonedDateTime) {
        this.messgae = messgae;
        this.httpStatus = httpStatus;
        this.zonedDateTime = zonedDateTime;
    }
}
