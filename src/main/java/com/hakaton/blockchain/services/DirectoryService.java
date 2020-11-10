package com.hakaton.blockchain.services;

import com.hakaton.blockchain.Response;
import com.hakaton.blockchain.controllers.models.Operation;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class DirectoryService {

    private final String directoryHost;
    private final String directoryPort;
    private final String url;

    public DirectoryService(String directoryHost, String directoryPort) {
        this.directoryHost = directoryHost;
        this.directoryPort = directoryPort;
        url = "http://" + directoryHost + ":" + directoryPort;
    }

    public Response<Operation> addTransactionToEntity(Long entityId, Operation operation) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Map<String, Object[]>> request = new HttpEntity<>(Map.of("transactions",
                new Object[]{operation}));
        Response<Boolean> result = (Response<Boolean>) restTemplate.postForObject(url
                + "/entities/addTransactions?entityId=" + entityId, request, Response.class);
        return result.isSuccess() ?
                Response.OK(operation) :
                Response.BAD("Failed to update entity in directory");
    }

}
