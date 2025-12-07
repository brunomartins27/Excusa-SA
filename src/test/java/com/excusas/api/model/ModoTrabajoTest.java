package com.excusas.api.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para el Enum ModoTrabajo")
class ModoTrabajoTest {

    @Test
    @DisplayName("Debe contener exactamente los modos de trabajo esperados")
    void testEnumValues() {
        ModoTrabajo[] modos = ModoTrabajo.values();

        assertEquals(3, modos.length, "El enum debería tener exactamente 3 modos");

        assertNotNull(ModoTrabajo.NORMAL);
        assertNotNull(ModoTrabajo.INPRODUCTIVO);
        assertNotNull(ModoTrabajo.PRODUCTIVO);
    }

    @Test
    @DisplayName("Debe retornar el enum correcto desde un String válido (valueOf)")
    void testValueOfValid() {

        assertEquals(ModoTrabajo.NORMAL, ModoTrabajo.valueOf("NORMAL"));
        assertEquals(ModoTrabajo.INPRODUCTIVO, ModoTrabajo.valueOf("INPRODUCTIVO"));
        assertEquals(ModoTrabajo.PRODUCTIVO, ModoTrabajo.valueOf("PRODUCTIVO"));
    }

    @Test
    @DisplayName("Debe lanzar excepción si se busca un modo inexistente")
    void testValueOfInvalid() {

        assertThrows(IllegalArgumentException.class, () -> {
            ModoTrabajo.valueOf("MODO_DESCONOCIDO");
        });
    }
}
