package com.picpaysimplificado.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dto.NotificationDto;
import com.picpaysimplificado.services.contracts.NotificationService;

public class NotificationServiceImpl implements NotificationService{

    private final RestTemplate restTemplate;

    @Autowired
    public NotificationServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();

        NotificationDto notificationDto = new NotificationDto(email, message);
        
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", notificationDto, String.class);
        
        if(!(responseEntity.getStatusCode() == HttpStatus.OK)){
            throw new Exception("Serviço fora do ar!");   
        }
    
    }
    

}
