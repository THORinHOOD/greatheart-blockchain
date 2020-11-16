package com.hakaton.blockchain.services;

import com.hakaton.blockchain.Response;
import com.hakaton.blockchain.controllers.models.Operation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class TransactionsStoreService {

    private final String host;
    private final String port;
    private final String url;

    public TransactionsStoreService(String host, String port, String endpoint) {
        this.host = host;
        this.port = port;
        url = "http://" + host + ":" + port + endpoint;
    }

    public Response<Operation> storeTransaction(Long id, Operation operation, String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Map<String, Object[]>> request = new HttpEntity<>(Map.of("transactions",
                new Object[]{operation}), headers);
        Response<Boolean> result = (Response<Boolean>) restTemplate.postForObject(url + id, request,
                Response.class);
        return result.isSuccess() ?
                Response.OK(operation) :
                Response.BAD("Failed to update entity in directory");
    }

}
