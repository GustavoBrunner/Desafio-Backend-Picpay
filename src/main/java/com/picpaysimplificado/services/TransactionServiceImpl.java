package com.picpaysimplificado.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dto.AuthorizationDto;
import com.picpaysimplificado.dto.TransactionDto;
import com.picpaysimplificado.repositories.TransactionRepository;
import com.picpaysimplificado.services.contracts.TransactionService;
import com.picpaysimplificado.services.contracts.UserService;
import com.picpaysimplificado.util.TransactionFactory;
import com.picpaysimplificado.util.TransactionMapper;
import com.picpaysimplificado.util.UserMapper;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final UserService userService;

    private final TransactionRepository repository;

    private final RestTemplate restTemplate;

    @Value("${authorization.service.url}")
    private String authorizationUrl;

    
    @Autowired
    public TransactionServiceImpl(UserService userService, TransactionRepository repository,
            RestTemplate restTemplate) {
        this.userService = userService;
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @Override
    public TransactionDto create(TransactionDto transaction) throws Exception {
        User sender = this.userService.findUserById(transaction.senderId()); 
        User receiver = this.userService.findUserById(transaction.receiverId());

        userService.validateTransaction(sender, transaction.value());

        Boolean isAuthorized = authorizeTransaction(sender, transaction.value());

        if(!isAuthorized){
            throw new Exception("Transação não autorizada!");
        }

        Transaction newTransaction = TransactionFactory
                    .createTransaction(transaction.value(), sender, receiver, LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(newTransaction.getAmount()));
        receiver.setBalance(receiver.getBalance().add(newTransaction.getAmount()));;            
        
        repository.save(newTransaction);
        userService.update(UserMapper.convertToDto(sender));
        userService.update(UserMapper.convertToDto(receiver));

        return TransactionMapper.convertToDto(newTransaction);
    }

    @Override
    public List<Transaction> findAllTransactionsBySenderId(Long userId) {
        return this.repository.findTransactionsBySenderId(userId);
    }

    @Override
    public Boolean authorizeTransaction(User sender, BigDecimal amount) {
        try{

            ResponseEntity<AuthorizationDto> authorizationResponse = restTemplate.getForEntity(authorizationUrl, AuthorizationDto.class);
            Boolean authorized = authorizationResponse.getBody().data().authorization();
            
            if(authorizationResponse.getStatusCode() == HttpStatus.OK && authorized){
                return true;
            }
        } catch (Exception e){
            e.getCause();
        }

        return false;
    }

}
