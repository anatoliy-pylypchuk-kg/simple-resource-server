package com.kindgeek.srs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SimpleResourceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleResourceServerApplication.class, args);
    }
}
