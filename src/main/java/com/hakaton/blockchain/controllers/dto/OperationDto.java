package com.hakaton.blockchain.controllers.dto;

import java.io.Serializable;

public class OperationDto implements Serializable {
    private Long userId;
    private Long amount;
    private String timestamp;

    public OperationDto(Long userId, Long amount, String timestamp) {
        this.userId = userId;
        this.amount = amount;
        this.timestamp = timestamp;
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
}
