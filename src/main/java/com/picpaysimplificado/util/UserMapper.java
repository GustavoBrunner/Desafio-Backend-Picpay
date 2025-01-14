package com.picpaysimplificado.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dto.UserDto;

public class UserMapper {

    public static UserDto convertToDto(User user){
        UserDto dto = new UserDto(user.getId(),user.getFirstName(), user.getLastName(), 
                        user.getEmail(), user.getDocument(), user.getPassword(), 
                        user.getBalance(), user.getUserType());
        return dto;
    }
    public static User convertToUser(UserDto dto){
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        return user;
    }

    public static <S, T> List<T> copyListProperties(List<S> source, Class<T> targetClass){
        return source.stream().map(item -> {
            try{
                T target = targetClass.getDeclaredConstructor().newInstance();
                BeanUtils.copyProperties(item, target);
                return target;
            }catch(Exception e){
                throw new RuntimeException("Erro ao copiar propriedade", e);
            }
        }).collect(Collectors.toList());
    }

}
