package com.picpaysimplificado.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dto.UserDto;
import com.picpaysimplificado.repositories.UserRepository;
import com.picpaysimplificado.services.contracts.UserService;
import com.picpaysimplificado.util.UserMapper;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Boolean validateTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Usuários do tipo lojista não podem fazer transações!");
        }

        if(sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Usuário não possui dinheiro suficiente para efetuar a transação!");
        }

        return true;
    }

    @Override
    public User findUserById(Long id) throws Exception {
        return repository.findById(id)
                         .orElseThrow(() -> new Exception("Usuário não encontrado!"));
    }

    @Override
    public UserDto create(UserDto entity) {
        User userMapped = UserMapper.convertToUser(entity);
        repository.save(userMapped);     
        return UserMapper.convertToDto(userMapped);
    }

    @Override
    public UserDto update(UserDto entity) {
        User userMapped = UserMapper.convertToUser(entity);
        repository.saveAndFlush(userMapped);
        return UserMapper.convertToDto(userMapped);
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

}
