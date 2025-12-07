package com.excusas.api.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para el Enum TipoExcusa")
class TipoExcusaTest {

    @Test
    @DisplayName("Debe contener exactamente los tipos de excusa esperados")
    void testEnumValues() {
        TipoExcusa[] tipos = TipoExcusa.values();

        assertEquals(7, tipos.length, "El enum debería tener exactamente 7 tipos");

        assertNotNull(TipoExcusa.MEDICA);
        assertNotNull(TipoExcusa.FAMILIAR);
        assertNotNull(TipoExcusa.TRANSITO);
        assertNotNull(TipoExcusa.DEPORTIVA);
        assertNotNull(TipoExcusa.COACHING);
        assertNotNull(TipoExcusa.OTRO);
        assertNotNull(TipoExcusa.OTROS);
    }

    @Test
    @DisplayName("Debe retornar el enum correcto desde un String válido (valueOf)")
    void testValueOfValid() {
        assertEquals(TipoExcusa.MEDICA, TipoExcusa.valueOf("MEDICA"));
        assertEquals(TipoExcusa.TRANSITO, TipoExcusa.valueOf("TRANSITO"));
        assertEquals(TipoExcusa.COACHING, TipoExcusa.valueOf("COACHING"));
    }

    @Test
    @DisplayName("Debe lanzar excepción si se busca un tipo inexistente")
    void testValueOfInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            TipoExcusa.valueOf("EXCUSA_INVENTADA");
        });
    }
}
