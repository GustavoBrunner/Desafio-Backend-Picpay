package com.picpaysimplificado.services.contracts;

import java.math.BigDecimal;
import java.util.List;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dto.UserDto;

public interface UserService {
    public Boolean validateTransaction(User sender, BigDecimal amount) throws Exception;
    public User findUserById(Long id) throws Exception;

    public UserDto create(UserDto entity);
    public UserDto update(UserDto entity);
    public List<User> getAllUsers();
}
