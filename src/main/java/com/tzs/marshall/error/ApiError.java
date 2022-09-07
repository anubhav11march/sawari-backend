package com.tzs.marshall.error;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ApiError {
    private ZonedDateTime timestamp;
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiError(HttpStatus status, String message, List<String> errors) {
        super();
        this.timestamp = ZonedDateTime.now();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message, String errors) {
        super();
        this.timestamp = ZonedDateTime.now();
        this.status = status;
        this.message = message;
        this.errors = Collections.singletonList(errors);
    }
}
