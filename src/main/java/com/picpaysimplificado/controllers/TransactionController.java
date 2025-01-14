package com.picpaysimplificado.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.dto.TransactionDto;
import com.picpaysimplificado.services.contracts.TransactionService;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Transaction>> findAllByUserId(@PathVariable Long id){
        List<Transaction> transactions = service.findAllTransactionsBySenderId(id);    

        if(transactions.isEmpty()){
            return new ResponseEntity<>(transactions, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<TransactionDto> create(@RequestBody TransactionDto transactionDto) throws Exception{
        if(transactionDto.equals(null)){
            return new ResponseEntity<>(transactionDto, HttpStatus.BAD_REQUEST);
        }
        TransactionDto result = this.service.create(transactionDto);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
