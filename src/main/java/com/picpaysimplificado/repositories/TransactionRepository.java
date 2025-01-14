package com.picpaysimplificado.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.picpaysimplificado.domain.transaction.Transaction;

public interface TransactionRepository extends JpaRepository< Transaction, Long > {
    public List<Transaction> findTransactionsBySenderId(Long senderId);
}
