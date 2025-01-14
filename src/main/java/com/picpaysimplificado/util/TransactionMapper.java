package com.picpaysimplificado.util;

import org.springframework.beans.BeanUtils;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.dto.TransactionDto;

public class TransactionMapper {
    public static TransactionDto convertToDto(Transaction transaction){
        TransactionDto dto = new TransactionDto(transaction.getAmount(), 
                            transaction.getSender().getId(), transaction.getReceiver().getId(), 
                            transaction.getTransactionHour());
        return dto;
    }
    public static Transaction convertToTransaction(TransactionDto dto){
        Transaction transaction = new Transaction();
        BeanUtils.copyProperties(dto, transaction);
        return transaction;
    }

}
