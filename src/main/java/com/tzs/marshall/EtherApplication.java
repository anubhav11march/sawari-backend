package com.tzs.marshall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.tzs"} )
public class EtherApplication {

    public static void main(String[] args) {
        SpringApplication.run(EtherApplication.class, args);
    }

}
