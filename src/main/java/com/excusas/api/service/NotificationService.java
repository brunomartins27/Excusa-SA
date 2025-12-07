package com.excusas.api.service;

import org.springframework.stereotype.Service;

public interface NotificationService {
    void enviarEmail(String destinatario, String asunto, String cuerpo);
}


@Service
class ConsoleNotificationService implements NotificationService {
    @Override
    public void enviarEmail(String destinatario, String asunto, String cuerpo) {
        System.out.println("==================================================");
        System.out.println(">>> [EMAIL SIMULADO] Enviando a: " + destinatario);
        System.out.println(">>> Asunto: " + asunto);
        System.out.println(">>> Cuerpo: " + cuerpo);
        System.out.println("==================================================");
    }
}
