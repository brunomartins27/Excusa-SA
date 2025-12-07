package com.excusas.api.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para el DTO de Empleado")
class EmpleadoDTOTest {

    @Test
    @DisplayName("Debe asignar y recuperar valores correctamente (Getters/Setters)")
    void testDataIntegrity() {

        EmpleadoDTO dto = new EmpleadoDTO();
        String legajo = "E123";
        String nombre = "Juan Perez";
        String email = "juan@test.com";

        dto.setLegajo(legajo);
        dto.setNombre(nombre);
        dto.setEmail(email);

        assertAll("Verificando propiedades del DTO",
            () -> assertEquals(legajo, dto.getLegajo(), "El legajo no coincide"),
            () -> assertEquals(nombre, dto.getNombre(), "El nombre no coincide"),
            () -> assertEquals(email, dto.getEmail(), "El email no coincide")
        );
    }

    @Test
    @DisplayName("Debe verificar igualdad entre objetos con mismos datos (Equals/HashCode)")
    void testEqualsAndHashCode() {

        EmpleadoDTO dto1 = new EmpleadoDTO();
        dto1.setLegajo("E001");
        dto1.setNombre("Ana");

        EmpleadoDTO dto2 = new EmpleadoDTO();
        dto2.setLegajo("E001");
        dto2.setNombre("Ana");

        EmpleadoDTO dto3 = new EmpleadoDTO();
        dto3.setLegajo("E002"); // Diferente

        assertEquals(dto1, dto2, "Objetos con mismos datos deberían ser iguales");
        assertEquals(dto1.hashCode(), dto2.hashCode(), "HashCodes deberían ser iguales");

        assertNotEquals(dto1, dto3, "Objetos con distintos datos no deberían ser iguales");
    }

    @Test
    @DisplayName("Debe generar una representación en String válida")
    void testToString() {

        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setLegajo("TEST-LEGAJO");

        String result = dto.toString();

        assertNotNull(result);
        assertTrue(result.contains("TEST-LEGAJO"), "El toString debe contener el valor del legajo");
    }
}
