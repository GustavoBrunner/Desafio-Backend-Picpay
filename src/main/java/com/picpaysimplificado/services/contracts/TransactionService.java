package com.picpaysimplificado.services.contracts;

import java.math.BigDecimal;
import java.util.List;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dto.TransactionDto;

public interface TransactionService {
    public TransactionDto create(TransactionDto transaction) throws Exception;

    public List<Transaction> findAllTransactionsBySenderId(Long userId);

    

    public Boolean authorizeTransaction(User sender, BigDecimal amount);
    
}
