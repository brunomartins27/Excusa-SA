package com.excusas.api.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests para ConsoleNotificationService")
class ConsoleNotificationServiceTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    private ConsoleNotificationService notificationService;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        notificationService = new ConsoleNotificationService();
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("Debe imprimir el email, asunto y cuerpo en la consola")
    void testEnviarEmail() {
        String destinatario = "test@excusas.com";
        String asunto = "Prueba de Asunto";
        String cuerpo = "Este es el cuerpo del mensaje";

        notificationService.enviarEmail(destinatario, asunto, cuerpo);

        String consolaOutput = outputStreamCaptor.toString();

        assertTrue(consolaOutput.contains(destinatario), "La consola debería mostrar el destinatario");
        assertTrue(consolaOutput.contains(asunto), "La consola debería mostrar el asunto");
        assertTrue(consolaOutput.contains(cuerpo), "La consola debería mostrar el cuerpo");
        assertTrue(consolaOutput.contains("[EMAIL SIMULADO]"), "Debería indicar que es simulado");
    }
}
