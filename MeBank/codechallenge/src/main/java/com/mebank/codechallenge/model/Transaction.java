package com.mebank.codechallenge.model;

import java.time.LocalDateTime;

import com.mebank.codechallenge.util.CommonUtil;

import lombok.Data;

@Data
public class Transaction {

    private String transactionID;
    private String fromAccountID;
    private String toAccountID;
    private LocalDateTime createdAt;
    private long amountInCents;
    private TransactionTypes transactionType;
    private String relatedPayment;

    public Transaction(String... transactionData) {
        this.transactionID = transactionData[0].strip();
        this.fromAccountID = transactionData[1].strip();
        this.toAccountID = transactionData[2].strip();
        this.createdAt = CommonUtil.getLocalDateTimeFromString(transactionData[3].strip());
        this.amountInCents = CommonUtil.convertStringToLong(transactionData[4].strip());
        this.transactionType = TransactionTypes.valueOf(transactionData[5].strip());
        if(this.transactionType.equals(TransactionTypes.REVERSAL)) {
            this.relatedPayment = transactionData[6].strip();
        }
    }
}
