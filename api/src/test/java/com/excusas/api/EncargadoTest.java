package com.excusas.api.model;

import com.excusas.api.model.Enums.EstadoExcusa;
import com.excusas.api.model.Enums.ModoTrabajo;
import com.excusas.api.model.Enums.TipoExcusa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

class EncargadoTest {

    @Test
    void testPuedeManejar_DeberiaRetornarTrue_SiTieneElMotivo() {
        Encargado encargado = new Encargado();
        encargado.setTiposManejables(List.of(TipoExcusa.MEDICA, TipoExcusa.FAMILIAR));

        assertTrue(encargado.puedeManejar(TipoExcusa.MEDICA));
        assertFalse(encargado.puedeManejar(TipoExcusa.TRANSITO));
    }

    @Test
    void testEvaluar_ModoVago_DeberiaAprobar() {
        Encargado vago = new Encargado();
        vago.setModo(ModoTrabajo.VAGO);

        assertEquals(EstadoExcusa.APROBADA, vago.evaluar());
    }

    @Test
    void testEvaluar_ModoProductivo_DeberiaRechazar() {
        Encargado productivo = new Encargado();
        productivo.setModo(ModoTrabajo.PRODUCTIVO);

        assertEquals(EstadoExcusa.RECHAZADA, productivo.evaluar());
    }

    @Test
    void testEvaluar_ModoNormal_DeberiaAprobar() {
        Encargado normal = new Encargado();
        normal.setModo(ModoTrabajo.NORMAL);

        assertEquals(EstadoExcusa.APROBADA, normal.evaluar());
    }
}