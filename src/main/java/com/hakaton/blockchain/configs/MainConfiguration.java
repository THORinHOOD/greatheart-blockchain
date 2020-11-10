package com.hakaton.blockchain.configs;

import com.hakaton.blockchain.services.DirectoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfiguration {

    @Value("${directory.host}")
    public String directoryHost;

    @Value("${directory.port}")
    public String directoryPort;

    @Bean
    public DirectoryService directoryService() {
        return new DirectoryService(directoryHost, directoryPort);
    }

}
