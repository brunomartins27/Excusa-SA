package com.excusas.api.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para la Entidad Prontuario")
class ProntuarioTest {

    @Test
    @DisplayName("Debe instanciar un objeto vacío correctamente")
    void testCrearVacio() {
        Prontuario p = new Prontuario();
        assertNull(p.getId());
        assertNull(p.getMotivo());
    }

    @Test
    @DisplayName("Constructor con lógica debe asignar empleado, motivo y fecha automática")
    void testConstructorConLogica() {
        Empleado e = new Empleado();
        e.setLegajo("E001");
        String motivo = "Incumplimiento de horario";

        Prontuario p = new Prontuario(e, motivo);

        assertAll("Verificando constructor lógico",
            () -> assertEquals(motivo, p.getMotivo(), "El motivo no coincide"),
            () -> assertEquals(e, p.getEmpleado(), "El empleado no coincide"),
            () -> assertNotNull(p.getFechaCreacion(), "La fecha de creación no debe ser nula (debe ser automática)")
        );
    }

    @Test
    @DisplayName("Debe permitir modificar la fecha manualmente")
    void testSetFecha() {
        Prontuario p = new Prontuario();
        LocalDateTime ahora = LocalDateTime.now();

        p.setFechaCreacion(ahora);

        assertEquals(ahora, p.getFechaCreacion());
    }

    @Test
    @DisplayName("Debe asignar y recuperar el motivo")
    void testSetMotivo() {
        Prontuario p = new Prontuario();
        String motivo = "Falta grave";

        p.setMotivo(motivo);

        assertEquals(motivo, p.getMotivo());
    }

    @Test
    @DisplayName("Debe gestionar la relación con Empleado")
    void testRelacionEmpleado() {
        Prontuario p = new Prontuario();
        Empleado e = new Empleado();
        e.setLegajo("123");

        p.setEmpleado(e);

        assertEquals("123", p.getEmpleado().getLegajo());
    }

    @Test
    @DisplayName("Equals: Objetos con mismo ID y datos deben ser iguales")
    void testEquals() {
        Empleado e = new Empleado();
        LocalDateTime fecha = LocalDateTime.now();

        Prontuario p1 = new Prontuario(e, "Mismo");
        p1.setId(1L);
        p1.setFechaCreacion(fecha); // Forzamos misma fecha para igualdad exacta

        Prontuario p2 = new Prontuario(e, "Mismo");
        p2.setId(1L);
        p2.setFechaCreacion(fecha);

        assertEquals(p1, p2);
    }

    @Test
    @DisplayName("Equals: Objetos con diferente ID deben ser distintos")
    void testNotEquals() {
        Prontuario p1 = new Prontuario();
        p1.setId(1L);

        Prontuario p2 = new Prontuario();
        p2.setId(2L);

        assertNotEquals(p1, p2);
    }

    @Test
    @DisplayName("ToString debe contener el motivo")
    void testToStringContent() {
        Prontuario p = new Prontuario();
        p.setMotivo("MotivoUnicoDePrueba");

        String resultado = p.toString();

        assertTrue(resultado.contains("MotivoUnicoDePrueba"));
    }

    @Test
    @DisplayName("HashCode debe ser consistente")
    void testHashCodeConsistency() {
        Prontuario p = new Prontuario();
        p.setId(10L);
        int hash1 = p.hashCode();
        int hash2 = p.hashCode();

        assertEquals(hash1, hash2);
    }
}
