package com.a.exboard5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class Exboard5Application {

    public static void main(String[] args) {
        SpringApplication.run(Exboard5Application.class, args);
    }

}
