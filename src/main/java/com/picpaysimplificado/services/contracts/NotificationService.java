package com.picpaysimplificado.services.contracts;

import com.picpaysimplificado.domain.user.User;

public interface NotificationService {
    public void sendNotification(User user, String message) throws Exception;
}
