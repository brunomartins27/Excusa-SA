package com.excusas.api.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para el DTO de Encargado")
class EncargadoDTOTest {

    @Test
    @DisplayName("Debe asignar y recuperar valores correctamente (Getters/Setters)")
    void testDataIntegrity() {

        EncargadoDTO dto = new EncargadoDTO();
        String nombre = "Gerente General";
        String modo = "NORMAL";
        Integer orden = 1;
        List<String> motivos = List.of("MEDICA", "FAMILIAR");

        dto.setNombreCargo(nombre);
        dto.setModo(modo);
        dto.setOrden(orden);
        dto.setMotivos(motivos);

        assertAll("Verificando propiedades del DTO",
            () -> assertEquals(nombre, dto.getNombreCargo(), "El nombre del cargo no coincide"),
            () -> assertEquals(modo, dto.getModo(), "El modo no coincide"),
            () -> assertEquals(orden, dto.getOrden(), "El orden de prioridad no coincide"),
            () -> assertNotNull(dto.getMotivos(), "La lista de motivos no debe ser nula"),
            () -> assertEquals(2, dto.getMotivos().size(), "El tamaño de la lista de motivos es incorrecto"),
            () -> assertTrue(dto.getMotivos().contains("MEDICA"), "Falta el motivo MEDICA")
        );
    }

    @Test
    @DisplayName("Debe verificar igualdad entre objetos con mismos datos (Equals/HashCode)")
    void testEqualsAndHashCode() {
        EncargadoDTO dto1 = new EncargadoDTO();
        dto1.setNombreCargo("Jefe");
        dto1.setOrden(2);

        EncargadoDTO dto2 = new EncargadoDTO();
        dto2.setNombreCargo("Jefe");
        dto2.setOrden(2);

        EncargadoDTO dto3 = new EncargadoDTO();
        dto3.setNombreCargo("Supervisor"); // Diferente

        assertEquals(dto1, dto2, "Objetos con mismos datos deberían ser iguales");
        assertEquals(dto1.hashCode(), dto2.hashCode(), "HashCodes deberían ser iguales");

        assertNotEquals(dto1, dto3, "Objetos con distintos datos no deberían ser iguales");
    }

    @Test
    @DisplayName("Debe generar una representación en String válida")
    void testToString() {
        EncargadoDTO dto = new EncargadoDTO();
        dto.setNombreCargo("TEST-CARGO");

        String result = dto.toString();

        assertNotNull(result);
        assertTrue(result.contains("TEST-CARGO"), "El toString debe contener el nombre del cargo");
    }
}
