package com.hakaton.blockchain.configs;

import com.hakaton.blockchain.services.TransactionsStoreService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfiguration {

    @Value("${directory.host}")
    public String directoryHost;

    @Value("${directory.port}")
    public String directoryPort;

    @Value("${tracker.host}")
    public String trackerHost;

    @Value("${tracker.port}")
    public String trackerPort;

    @Bean(name = "directoryService")
    public TransactionsStoreService directoryService() {
        return new TransactionsStoreService(directoryHost, directoryPort,
                "/entities/addTransactions?entityId=");
    }

    @Bean(name = "trackerService")
    public TransactionsStoreService trackerService() {
        return new TransactionsStoreService(trackerHost, trackerPort,
                "/requests/addMoneyTransactions?requestId=");
    }

}
