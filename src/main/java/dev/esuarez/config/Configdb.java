package dev.esuarez.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configdb {

    @Bean
    public CommandLineRunner unitDataBase(){
        return args -> {
            System.out.println("ok");
        };

    }
}
