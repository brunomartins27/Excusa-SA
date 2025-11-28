package com.excusas.api.service;

import org.springframework.stereotype.Service;

public interface NotificationService {
    void enviarEmail(String destinatario, String asunto, String cuerpo);
}

@Service
class ConsoleNotificationService implements NotificationService {
    @Override
    public void enviarEmail(String destinatario, String asunto, String cuerpo) {

        System.out.println(">>> ENVIANDO EMAIL A: " + destinatario);
        System.out.println(">>> ASUNTO: " + asunto);
        System.out.println(">>> CUERPO: " + cuerpo);
        System.out.println("------------------------------------------------");
    }
}