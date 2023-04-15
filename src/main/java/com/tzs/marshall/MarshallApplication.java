package com.tzs.marshall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@SpringBootApplication(scanBasePackages = { "com.tzs"} )
@EnableJdbcRepositories(basePackages = {"com.tzs"} )
@EnableJdbcHttpSession
public class MarshallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarshallApplication.class, args);
    }

}
