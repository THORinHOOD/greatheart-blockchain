package com.hakaton.blockchain.controllers.models;

import java.io.Serializable;

public class Operation implements Serializable {
    private final Long userId;
    private final Long amount;
    private final String timestamp;
    private final String id;
    private final String operationType;
    private final String description;

    public static String generateId(Long userId, String timestamp, String operationType) {
        return operationType + "_" + userId + "_" + timestamp;
    }

    public Operation(Long userId, Long amount, String timestamp, String id, String operationType, String description) {
        this.userId = userId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.id = id;
        this.operationType = operationType;
        this.description = description;
    }

    public Long getUserId() {
        return userId;
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
