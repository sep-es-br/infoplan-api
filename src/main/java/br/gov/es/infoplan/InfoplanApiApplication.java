package br.gov.es.infoplan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InfoplanApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfoplanApiApplication.class, args);
    }

}
