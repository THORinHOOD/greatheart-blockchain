package com.hakaton.blockchain.controllers.models;

import java.io.Serializable;

public class Operation implements Serializable {
    private final String login;
    private final Long amount;
    private final String timestamp;
    private final String id;
    private final String operationType;
    private final String description;

    public static String generateId(String login, String timestamp, String operationType) {
        return operationType + "_" + login + "_" + timestamp;
    }

    public Operation(String login, Long amount, String timestamp, String id, String operationType,
                     String description) {
        this.login = login;
        this.amount = amount;
        this.timestamp = timestamp;
        this.id = id;
        this.operationType = operationType;
        this.description = description;
    }

    public String getLogin() {
        return login;
    }

    public Long getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getId() {
        return id;
    }

    public String getOperationType() {
        return operationType;
    }

    public String getDescription() {
        return description;
    }
}
