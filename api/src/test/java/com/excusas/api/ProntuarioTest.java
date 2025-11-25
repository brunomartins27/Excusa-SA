package com.excusas.api.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ProntuarioTest {

    @Test
    void testCrearVacio() {
        Prontuario p = new Prontuario();
        assertNull(p.getId());
    }

    @Test
    void testConstructorConLogica() {
        Empleado e = new Empleado();
        Prontuario p = new Prontuario(e, "Motivo X");
        assertEquals("Motivo X", p.getMotivo());
        assertEquals(e, p.getEmpleado());
        assertNotNull(p.getFechaCreacion()); // Verifica se a data foi gerada automaticamente
    }

    @Test
    void testSetFecha() {
        Prontuario p = new Prontuario();
        LocalDateTime ahora = LocalDateTime.now();
        p.setFechaCreacion(ahora);
        assertEquals(ahora, p.getFechaCreacion());
    }

    @Test
    void testSetMotivo() {
        Prontuario p = new Prontuario();
        p.setMotivo("Falta grave");
        assertEquals("Falta grave", p.getMotivo());
    }

    @Test
    void testRelacionEmpleado() {
        Prontuario p = new Prontuario();
        Empleado e = new Empleado();
        e.setLegajo("123");
        p.setEmpleado(e);
        assertEquals("123", p.getEmpleado().getLegajo());
    }

    @Test
    void testId() {
        Prontuario p = new Prontuario();
        p.setId(50L);
        assertEquals(50L, p.getId());
    }

    @Test
    void testEquals() {
        Empleado e = new Empleado();
        Prontuario p1 = new Prontuario(e, "Mismo");
        p1.setId(1L);
        Prontuario p2 = new Prontuario(e, "Mismo");
        p2.setId(1L);
        // Ajustamos datas para serem iguais pois o construtor gera 'now'
        p2.setFechaCreacion(p1.getFechaCreacion());

        assertEquals(p1, p2);
    }

    @Test
    void testNotEquals() {
        Prontuario p1 = new Prontuario();
        p1.setId(1L);
        Prontuario p2 = new Prontuario();
        p2.setId(2L);
        assertNotEquals(p1, p2);
    }

    @Test
    void testToStringContent() {
        Prontuario p = new Prontuario();
        p.setMotivo("MotivoUnico");
        assertTrue(p.toString().contains("MotivoUnico"));
    }

    @Test
    void testHashCodeConsistency() {
        Prontuario p = new Prontuario();
        p.setId(10L);
        int hash1 = p.hashCode();
        int hash2 = p.hashCode();
        assertEquals(hash1, hash2);
    }
}