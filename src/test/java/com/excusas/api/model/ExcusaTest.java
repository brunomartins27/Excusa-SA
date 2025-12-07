package com.excusas.api.model;

import com.excusas.api.model.EstadoExcusa;
import com.excusas.api.model.TipoExcusa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para la Entidad Excusa")
class ExcusaTest {

    @Test
    @DisplayName("Debe instanciar un objeto vacío correctamente")
    void testValoresIniciais() {
        Excusa excusa = new Excusa();
        assertNull(excusa.getId());
        assertNull(excusa.getEstado());
        assertNull(excusa.getFechaRegistro());
    }

    @Test
    @DisplayName("Debe asignar y recuperar los datos básicos (Descripción, Tipo, Estado)")
    void testDataIntegrity() {
        // Arrange
        Excusa excusa = new Excusa();
        String descripcion = "Dolor de cabeza intenso";
        TipoExcusa tipo = TipoExcusa.MEDICA;
        EstadoExcusa estado = EstadoExcusa.PENDIENTE;
        LocalDate fecha = LocalDate.now();
        String aprobador = "Dr. House";


        excusa.setDescripcion(descripcion);
        excusa.setTipo(tipo);
        excusa.setEstado(estado);
        excusa.setFechaRegistro(fecha);
        excusa.setEncargadoAprobador(aprobador);


        assertAll("Verificando propiedades de la Excusa",
            () -> assertEquals(descripcion, excusa.getDescripcion(), "Descripción incorrecta"),
            () -> assertEquals(tipo, excusa.getTipo(), "Tipo incorrecto"),
            () -> assertEquals(estado, excusa.getEstado(), "Estado incorrecto"),
            () -> assertEquals(fecha, excusa.getFechaRegistro(), "Fecha incorrecta"),
            () -> assertEquals(aprobador, excusa.getEncargadoAprobador(), "Aprobador incorrecto")
        );
    }

    @Test
    @DisplayName("Debe gestionar correctamente la relación con Empleado")
    void testRelacionEmpleado() {

        Empleado emp = new Empleado();
        emp.setLegajo("E001");
        emp.setNombre("Pepe");

        Excusa excusa = new Excusa();


        excusa.setEmpleado(emp);


        assertNotNull(excusa.getEmpleado(), "El empleado no debería ser nulo");
        assertEquals("E001", excusa.getEmpleado().getLegajo(), "El legajo del empleado asociado no coincide");
        assertEquals(emp, excusa.getEmpleado(), "El objeto empleado debe ser el mismo");
    }

    @Test
    @DisplayName("Constructor completo debe asignar todas las propiedades")
    void testConstructorCompleto() {

        Empleado emp = new Empleado();
        LocalDate fecha = LocalDate.of(2025, 1, 1);


        Excusa e = new Excusa(1L, fecha, "Desc", TipoExcusa.FAMILIAR, EstadoExcusa.APROBADA, emp, "Jefe");


        assertAll("Verificando constructor completo",
            () -> assertEquals(1L, e.getId()),
            () -> assertEquals(fecha, e.getFechaRegistro()),
            () -> assertEquals("Desc", e.getDescripcion()),
            () -> assertEquals(TipoExcusa.FAMILIAR, e.getTipo()),
            () -> assertEquals(EstadoExcusa.APROBADA, e.getEstado()),
            () -> assertEquals(emp, e.getEmpleado()),
            () -> assertEquals("Jefe", e.getEncargadoAprobador())
        );
    }

    @Test
    @DisplayName("Equals: Objetos con el mismo ID deben ser iguales")
    void testEquals() {
        Excusa e1 = new Excusa();
        e1.setId(100L);

        Excusa e2 = new Excusa();
        e2.setId(100L);

        Excusa e3 = new Excusa();
        e3.setId(200L); // Diferente

        assertEquals(e1, e2, "Excusas con mismo ID deberían ser iguales");
        assertNotEquals(e1, e3, "Excusas con distinto ID deberían ser diferentes");
    }

    @Test
    @DisplayName("HashCode debe ser consistente")
    void testHashCode() {
        Excusa e1 = new Excusa();
        e1.setId(50L);

        Excusa e2 = new Excusa();
        e2.setId(50L);

        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    @DisplayName("ToString debe contener información relevante")
    void testToString() {
        Excusa e = new Excusa();
        e.setId(1L);
        e.setDescripcion("TextoUnicoDePrueba");

        String resultado = e.toString();

        assertTrue(resultado.contains("TextoUnicoDePrueba"), "El toString debe contener la descripción");
        assertTrue(resultado.contains("id=1"), "El toString debe contener el ID");
    }
}
