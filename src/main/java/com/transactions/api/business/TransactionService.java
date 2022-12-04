package com.transactions.api.business;

import com.transactions.api.dto.RejectedTransactionDTO;
import com.transactions.api.dto.TransactionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class TransactionService {

    private final ConcurrentHashMap<String, Float> store = new ConcurrentHashMap<>();

    public void clearMap(){
        store.clear();
    }

    public RejectedTransactionDTO processTransactions(List<String> transactionList) {
        log.info("Starting to process {} transactions", transactionList.size());
        Flux<TransactionDTO> flux = Flux.fromIterable(transactionList).flatMap(this::processTransaction);
        List<TransactionDTO> rejectedTransactions = new ArrayList<>();
        RejectedTransactionDTO dto = new RejectedTransactionDTO(rejectedTransactions);
        flux.collectList().subscribe(rejectedTransactions::addAll);
        return dto;
    }

    public Mono<TransactionDTO> processTransaction(String transaction) {
        String[] transactionDetails = transaction.split(",");
        if (transactionDetails.length != 5) {
            log.info("Transaction Format not correct : {}", transaction);
        }
        String firstName = transactionDetails[0];
        String lastName = transactionDetails[1];
        String email = transactionDetails[2];
        Float amount = Float.valueOf(transactionDetails[3]);
        String txId = transactionDetails[4];

        if (!store.containsKey(email)) {
            if (200f - amount < 0) {
                log.info("Rejected Transaction : {}", txId);
                return Mono.just(new TransactionDTO(firstName, lastName, email, txId));
            }
            store.put(email, 200f - amount);
        } else {
            Float currentBalance = store.get(email);
            if (currentBalance < amount) {
                log.info("Rejected Transaction : {}", txId);
                return Mono.just(new TransactionDTO(firstName, lastName, email, txId));
            } else {
                store.put(email, currentBalance - amount);
            }
        }
        return Mono.empty();
    }


}
