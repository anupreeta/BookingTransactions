package com.transactions.api.controller;

import com.transactions.api.business.TransactionService;
import com.transactions.api.dto.RejectedTransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public RejectedTransactionDTO process(@RequestBody List<String> transactionList) {
        RejectedTransactionDTO dto = transactionService.processTransactions(transactionList);
        transactionService.clearMap();
        return dto;
    }
}
