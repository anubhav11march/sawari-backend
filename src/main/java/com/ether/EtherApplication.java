package com.ether;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.ether"} )
public class EtherApplication {

    public static void main(String[] args) {
        SpringApplication.run(EtherApplication.class, args);
    }

}
