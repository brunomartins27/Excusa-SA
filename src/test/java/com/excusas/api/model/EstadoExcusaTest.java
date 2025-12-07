package com.excusas.api.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para el Enum EstadoExcusa")
class EstadoExcusaTest {

    @Test
    @DisplayName("Debe contener exactamente los valores esperados")
    void testEnumValues() {

        EstadoExcusa[] estados = EstadoExcusa.values();

        assertEquals(3, estados.length, "El enum debería tener exactamente 3 estados");

        assertNotNull(EstadoExcusa.PENDIENTE);
        assertNotNull(EstadoExcusa.APROBADA);
        assertNotNull(EstadoExcusa.RECHAZADA);
    }

    @Test
    @DisplayName("Debe retornar el enum correcto desde un String válido (valueOf)")
    void testValueOfValid() {
        assertEquals(EstadoExcusa.APROBADA, EstadoExcusa.valueOf("APROBADA"));
        assertEquals(EstadoExcusa.RECHAZADA, EstadoExcusa.valueOf("RECHAZADA"));
        assertEquals(EstadoExcusa.PENDIENTE, EstadoExcusa.valueOf("PENDIENTE"));
    }

    @Test
    @DisplayName("Debe lanzar excepción si se busca un estado inexistente")
    void testValueOfInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            EstadoExcusa.valueOf("INEXISTENTE");
        });
    }
}
