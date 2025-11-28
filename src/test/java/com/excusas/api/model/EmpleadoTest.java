package com.excusas.api.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmpleadoTest {

    @Test
    void testCrearEmpleadoVacio() {
        Empleado e = new Empleado();
        assertNull(e.getId());
        assertNull(e.getLegajo());
    }

    @Test
    void testSetLegajo() {
        Empleado e = new Empleado();
        e.setLegajo("E001");
        assertEquals("E001", e.getLegajo());
    }

    @Test
    void testSetNombre() {
        Empleado e = new Empleado();
        e.setNombre("Carlos");
        assertEquals("Carlos", e.getNombre());
    }

    @Test
    void testSetEmail() {
        Empleado e = new Empleado();
        e.setEmail("carlos@mail.com");
        assertEquals("carlos@mail.com", e.getEmail());
    }

    @Test
    void testConstructorCompleto() {
        Empleado e = new Empleado(1L, "E002", "Ana", "ana@mail.com");
        assertEquals(1L, e.getId());
        assertEquals("Ana", e.getNombre());
    }

    @Test
    void testEquals_MismoObjeto() {
        Empleado e = new Empleado(1L, "E001", "A", "a@a.com");
        assertEquals(e, e);
    }

    @Test
    void testEquals_ObjetosIdenticos() {
        Empleado e1 = new Empleado(1L, "E001", "A", "a@a.com");
        Empleado e2 = new Empleado(1L, "E001", "A", "a@a.com");
        assertEquals(e1, e2);
    }

    @Test
    void testNotEquals_DiferenteId() {
        Empleado e1 = new Empleado(1L, "E001", "A", "a@a.com");
        Empleado e2 = new Empleado(2L, "E001", "A", "a@a.com");
        assertNotEquals(e1, e2);
    }

    @Test
    void testToString() {
        Empleado e = new Empleado(1L, "LegajoTest", "NameTest", "EmailTest");
        String resultado = e.toString();
        assertTrue(resultado.contains("LegajoTest"));
        assertTrue(resultado.contains("NameTest"));
    }

    @Test
    void testHashCode() {
        Empleado e1 = new Empleado(1L, "E001", "A", "a@a.com");
        Empleado e2 = new Empleado(1L, "E001", "A", "a@a.com");
        assertEquals(e1.hashCode(), e2.hashCode());
    }
}