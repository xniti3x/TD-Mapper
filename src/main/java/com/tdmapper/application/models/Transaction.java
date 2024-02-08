package com.tdmapper.application.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "bank_transaction")
public class Transaction extends AbstractEntity{
    public String transactionId;
    public String entryReference;
    public String endToEndId;
    public LocalDate bookingDate;
    public LocalDate valueDate;
    public double amount;
    public String debtorName;
    public String iban;
    public String remittanceInformationStructured;
    public String additionalInformation;
    public String proprietaryBankTransactionCode;
    public String creditorAgent;
    public String debtorAgent;
    public String internalTransactionId;  
}
