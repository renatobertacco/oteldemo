package org.demo.bike.app;

import org.demo.bike.app.service.BikeService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BikeApplication implements ApplicationRunner{

    @Autowired
    BikeService bikeService;

    public static void main(String[] args) {
        SpringApplication.run(BikeApplication.class, args);
    }
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        bikeService.createDemoData();
        LoggerFactory.getLogger(getClass()).info("Demo data created");
        
    }
}