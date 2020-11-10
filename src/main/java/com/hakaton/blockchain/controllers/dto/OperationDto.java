package com.hakaton.blockchain.controllers.dto;

import java.io.Serializable;

public class OperationDto implements Serializable {
    private final Long userId;
    private final Long amount;
    private final String timestamp;
    private final String description;
    private final Long entityId;

    public OperationDto(Long userId, Long amount, String timestamp, String description, Long entityId) {
        this.userId = userId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.description = description;
        this.entityId = entityId;
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

    public String getDescription() {
        return description;
    }

    public Long getEntityId() {
        return entityId;
    }

}
