package com.tdmapper.application.models;

import com.tdmapper.application.data.AbstractEntity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Transaction extends AbstractEntity{
    public String transactionId;
    public String entryReference;
    public String endToEndId;
    public String bookingDate;
    public String valueDate;
    public String amount;
    public String debtorName;
    public String iban;
    public String remittanceInformationStructured;
    public String additionalInformation;
    public String proprietaryBankTransactionCode;
    public String creditorAgent;
    public String debtorAgent;
    public String internalTransactionId;
}
