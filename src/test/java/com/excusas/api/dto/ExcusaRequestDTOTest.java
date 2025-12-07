package com.excusas.api.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para el DTO de Solicitud de Excusa")
class ExcusaRequestDTOTest {

    @Test
    @DisplayName("Debe asignar y recuperar valores correctamente (Getters/Setters)")
    void testDataIntegrity() {
        ExcusaRequestDTO dto = new ExcusaRequestDTO();
        String legajo = "E999";
        String motivo = "TRANSITO";
        String descripcion = "Mucho tráfico en la autopista";

        dto.setLegajoEmpleado(legajo);
        dto.setMotivo(motivo);
        dto.setDescripcion(descripcion);

        assertAll("Verificando propiedades del DTO",
            () -> assertEquals(legajo, dto.getLegajoEmpleado(), "El legajo no coincide"),
            () -> assertEquals(motivo, dto.getMotivo(), "El motivo no coincide"),
            () -> assertEquals(descripcion, dto.getDescripcion(), "La descripción no coincide")
        );
    }

    @Test
    @DisplayName("Debe verificar igualdad entre objetos con mismos datos (Equals/HashCode)")
    void testEqualsAndHashCode() {
        ExcusaRequestDTO dto1 = new ExcusaRequestDTO();
        dto1.setLegajoEmpleado("E001");
        dto1.setMotivo("MEDICA");

        ExcusaRequestDTO dto2 = new ExcusaRequestDTO();
        dto2.setLegajoEmpleado("E001");
        dto2.setMotivo("MEDICA");

        ExcusaRequestDTO dto3 = new ExcusaRequestDTO();
        dto3.setLegajoEmpleado("E002"); // Diferente

        assertEquals(dto1, dto2, "Objetos con mismos datos deberían ser iguales");
        assertEquals(dto1.hashCode(), dto2.hashCode(), "HashCodes deberían ser iguales");

        assertNotEquals(dto1, dto3, "Objetos con distintos datos no deberían ser iguales");
    }

    @Test
    @DisplayName("Debe generar una representación en String válida")
    void testToString() {
        ExcusaRequestDTO dto = new ExcusaRequestDTO();
        dto.setDescripcion("TEST-DESC");

        String result = dto.toString();


        assertNotNull(result);
        assertTrue(result.contains("TEST-DESC"), "El toString debe contener la descripción");
    }
}
