package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan //filter scan
@SpringBootApplication
public class ApiApp {


    public static void main(String[] args) {
        SpringApplication.run(ApiApp.class, args);
    }


}
