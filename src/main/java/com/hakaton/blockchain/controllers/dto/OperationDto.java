package com.hakaton.blockchain.controllers.dto;

import java.io.Serializable;

public class OperationDto implements Serializable {
    private final String login;
    private final Long amount;
    private final String timestamp;
    private final String description;
    private final Long entityId;

    public OperationDto(String login, Long amount, String timestamp, String description, Long entityId) {
        this.login = login;
        this.amount = amount;
        this.timestamp = timestamp;
        this.description = description;
        this.entityId = entityId;
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

    public String getDescription() {
        return description;
    }

    public Long getEntityId() {
        return entityId;
    }

}
