package com.picpaysimplificado.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.domain.user.User;

public class TransactionFactory {
    public static Transaction createTransaction(BigDecimal value, User sender, User receiver, LocalDateTime localDateTime){
        return new Transaction(sender, receiver, value, localDateTime);
    }
}
