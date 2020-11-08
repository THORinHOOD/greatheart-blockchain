package com.hakaton.blockchain.controllers.models;

import java.io.Serializable;

public class FundWallet implements Serializable {
    private Long balance;

    public FundWallet(Long balance) {
        this.balance = balance;
    }

    public Long getBalance() {
        return balance;
    }

}
