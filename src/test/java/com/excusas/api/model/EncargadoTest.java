package com.excusas.api.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Tests de Lógica de Negocio - Encargado")
class EncargadoTest {

    @Test
    @DisplayName("Debe confirmar si puede manejar un tipo de excusa específico")
    void testPuedeManejar() {
        Encargado encargado = new Encargado();
        encargado.setTiposManejables(List.of(TipoExcusa.MEDICA, TipoExcusa.FAMILIAR));

        assertTrue(encargado.puedeManejar(TipoExcusa.MEDICA), "Debería poder manejar MEDICA");
        assertFalse(encargado.puedeManejar(TipoExcusa.TRANSITO), "No debería manejar TRANSITO");
    }

    @Test
    @DisplayName("Modo INPRODUCTIVO debe aprobar siempre")
    void testEvaluar_ModoInproductivo() {
        Encargado vago = new Encargado();
        vago.setModo(ModoTrabajo.INPRODUCTIVO);

        assertEquals(EstadoExcusa.APROBADA, vago.evaluar());
    }

    @Test
    @DisplayName("Modo PRODUCTIVO debe rechazar siempre")
    void testEvaluar_ModoProductivo() {
        Encargado productivo = new Encargado();
        productivo.setModo(ModoTrabajo.PRODUCTIVO);

        assertEquals(EstadoExcusa.RECHAZADA, productivo.evaluar());
    }

    @Test
    @DisplayName("Modo NORMAL debe aprobar por defecto")
    void testEvaluar_ModoNormal() {
        Encargado normal = new Encargado();
        normal.setModo(ModoTrabajo.NORMAL);

        assertEquals(EstadoExcusa.APROBADA, normal.evaluar());
    }
}
