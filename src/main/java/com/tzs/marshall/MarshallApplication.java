package com.tzs.marshall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication(scanBasePackages = { "com.tzs"} )
@EnableJdbcRepositories(basePackages = {"com.tzs"} )
public class MarshallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarshallApplication.class, args);
    }

}
