package com.excusas.api.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para la Entidad Empleado")
class EmpleadoTest {

    @Test
    @DisplayName("Debe instanciar un objeto vacío correctamente")
    void testCrearEmpleadoVacio() {
        Empleado e = new Empleado();
        assertNull(e.getId());
        assertNull(e.getLegajo());
    }

    @Test
    @DisplayName("Debe asignar y recuperar el Legajo")
    void testSetLegajo() {
        Empleado e = new Empleado();
        e.setLegajo("E001");
        assertEquals("E001", e.getLegajo());
    }

    @Test
    @DisplayName("Debe asignar y recuperar el Nombre")
    void testSetNombre() {
        Empleado e = new Empleado();
        e.setNombre("Carlos");
        assertEquals("Carlos", e.getNombre());
    }

    @Test
    @DisplayName("Debe asignar y recuperar el Email")
    void testSetEmail() {
        Empleado e = new Empleado();
        e.setEmail("carlos@mail.com");
        assertEquals("carlos@mail.com", e.getEmail());
    }

    @Test
    @DisplayName("Constructor con argumentos debe asignar propiedades")
    void testConstructorCompleto() {
        Empleado e = new Empleado(1L, "E002", "Ana", "ana@mail.com");
        assertEquals(1L, e.getId());
        assertEquals("Ana", e.getNombre());
    }

    @Test
    @DisplayName("Lógica de Negocio: Validación de Email Corporativo")
    void testTieneEmailCorporativo() {
        Empleado e = new Empleado();

        e.setEmail("juan@excusas-sa.com");
        assertTrue(e.tieneEmailCorporativo(), "Debería ser verdadero para dominio @excusas-sa.com");

        e.setEmail("juan@gmail.com");
        assertFalse(e.tieneEmailCorporativo(), "Debería ser falso para dominio externo");
    }

    @Test
    @DisplayName("Equals: Objetos en memoria deben ser iguales a sí mismos")
    void testEquals_MismoObjeto() {
        Empleado e = new Empleado(1L, "E001", "A", "a@a.com");
        assertEquals(e, e);
    }

    @Test
    @DisplayName("Equals: Objetos con mismos datos deben ser lógicamente iguales")
    void testEquals_ObjetosIdenticos() {
        Empleado e1 = new Empleado(1L, "E001", "A", "a@a.com");
        Empleado e2 = new Empleado(1L, "E001", "A", "a@a.com");
        assertEquals(e1, e2);
    }

    @Test
    @DisplayName("Equals: Objetos con distinto ID deben ser diferentes")
    void testNotEquals_DiferenteId() {
        Empleado e1 = new Empleado(1L, "E001", "A", "a@a.com");
        Empleado e2 = new Empleado(2L, "E001", "A", "a@a.com");
        assertNotEquals(e1, e2);
    }

    @Test
    @DisplayName("ToString debe contener información legible")
    void testToString() {
        Empleado e = new Empleado(1L, "LegajoTest", "NameTest", "EmailTest");
        String resultado = e.toString();
        assertTrue(resultado.contains("LegajoTest"));
        assertTrue(resultado.contains("NameTest"));
    }

    @Test
    @DisplayName("HashCode debe ser consistente entre objetos iguales")
    void testHashCode() {
        Empleado e1 = new Empleado(1L, "E001", "A", "a@a.com");
        Empleado e2 = new Empleado(1L, "E001", "A", "a@a.com");
        assertEquals(e1.hashCode(), e2.hashCode());
    }
}
