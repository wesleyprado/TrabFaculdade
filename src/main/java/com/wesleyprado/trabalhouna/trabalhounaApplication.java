package com.wesleyprado.trabalhouna;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class trabalhounaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(trabalhounaApplication.class, args);
    }

    @Override
    public void run(String... args) {
    }

}
