package com.transactions.api.controller;

import com.transactions.api.business.TransactionService;
import com.transactions.api.dto.RejectedTransactionDTO;
import com.transactions.api.dto.TransactionDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.security.RunAs;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
class TransactionControllerTest {

    @Autowired
    TransactionService transactionService;

    @Test
    void shouldRejectTransactions() {
        transactionService.clearMap();
        List<String> testTxns =  new ArrayList<>();
        testTxns.add("John,Doe,john@doe.com,190,TR0001");
        testTxns.add("John,Doe1,john@doe1.com,200,TR0002");
        testTxns.add("John,Doe2,john@doe2.com,201,TR0003");
        testTxns.add("John,Doe,john@doe.com,9,TR0004");
        testTxns.add("John,Doe,john@doe.com,2,TR0005");
        RejectedTransactionDTO result = transactionService.processTransactions(testTxns);
        assertEquals(2, result.getRejectedTransactions().size());
    }

    @Test
    void shouldPassAllTransactions() {
        transactionService.clearMap();
        List<String> testTxns =  new ArrayList<>();
        testTxns.add("John,Doe,john@doe.com,150,TR0001");
        testTxns.add("John,Doe1,john@doe1.com,200,TR0002");
        testTxns.add("John,Doe2,john@doe2.com,199,TR0003");
        testTxns.add("John,Doe,john@doe.com,9,TR0004");
        testTxns.add("John,Doe,john@doe.com,2,TR0005");
        RejectedTransactionDTO result = transactionService.processTransactions(testTxns);
        assertEquals(0, result.getRejectedTransactions().size());
    }

    @Test
    void shouldRejectAllTransactions() {
        transactionService.clearMap();
        List<String> testTxns =  new ArrayList<>();
        testTxns.add("John,Doe,john@doe.com,190,TR0001");
        testTxns.add("John,Doe1,john@doe1.com,200,TR0002");
        testTxns.add("John,Doe2,john@doe2.com,201,TR0003");
        testTxns.add("John,Doe,john@doe.com,9,TR0004");
        testTxns.add("John,Doe,john@doe.com,2,TR0005");
        RejectedTransactionDTO result = transactionService.processTransactions(testTxns);
        assertEquals(2, result.getRejectedTransactions().size());
        result = transactionService.processTransactions(testTxns);
        assertEquals(5, result.getRejectedTransactions().size());
    }
}