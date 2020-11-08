package com.hakaton.blockchain.controllers.models;

import java.io.Serializable;

public class Operation implements Serializable {
    private Long userId;
    private Long amount;
    private String timestamp;
    private String id;
    private String operationType;

    public static String generateId(Long userId, String timestamp, String operationType) {
        return operationType + "_" + userId + "_" + timestamp;
    }

    public Operation(Long userId, Long amount, String timestamp, String id, String operationType) {
        this.userId = userId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.id = id;
        this.operationType = operationType;
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
}
