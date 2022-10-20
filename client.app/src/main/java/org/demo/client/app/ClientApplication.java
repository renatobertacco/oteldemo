package org.demo.client.app;

import org.demo.client.app.service.ClientService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication implements ApplicationRunner{

    @Autowired
    ClientService clientService;
    
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        clientService.createDemoData();
        LoggerFactory.getLogger(getClass()).info("Demo data created");
        
    }
}