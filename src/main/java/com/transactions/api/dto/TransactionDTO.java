package com.transactions.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class TransactionDTO {
    String firstName;
    String lastName;
    String email;
    String transactionId;

    public TransactionDTO(String firstName, String lastName, String email, String transactionId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.transactionId = transactionId;
    }
}
