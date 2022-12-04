package com.transactions.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class RejectedTransactionDTO {

    public RejectedTransactionDTO(List<TransactionDTO> transactionDTOList){
        this.rejectedTransactions = transactionDTOList;
    }
    List<TransactionDTO> rejectedTransactions;
}
