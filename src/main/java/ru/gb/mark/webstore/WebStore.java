package ru.gb.mark.webstore;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;


@Log4j2
@SpringBootApplication
@EnableCaching
@EnableDiscoveryClient
public class WebStore {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(WebStore.class, args);
    }

}
