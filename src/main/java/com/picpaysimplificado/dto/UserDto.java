package com.picpaysimplificado.dto;

import java.math.BigDecimal;

import com.picpaysimplificado.domain.user.UserType;

public record UserDto(Long id, String firstName, String lastName, 
                      String email, String document, String password, 
                      BigDecimal balance, UserType userType) { }
