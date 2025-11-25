package com.excusas.api;

import com.excusas.api.model.Empleado;
import com.excusas.api.model.Enums.EstadoExcusa;
import com.excusas.api.model.Enums.TipoExcusa;
import com.excusas.api.model.Excusa;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExcusaTest {

    @Test
     void testCrearExcusa_ValoresIniciais() {
        Excusa excusa = new Excusa();
        excusa.setDescripcion("Teste");
        excusa.setTipo(TipoExcusa.OTRO);

        assertEquals("Teste", excusa.getDescripcion());
        assertEquals(TipoExcusa.OTRO, excusa.getTipo());
    }

    @Test
    void testAsignarEmpleado() {
        Empleado emp = new Empleado();
        emp.setLegajo("E999");

        Excusa excusa = new Excusa();
        excusa.setEmpleado(emp);

        assertNotNull(excusa.getEmpleado());
        assertEquals("E999", excusa.getEmpleado().getLegajo());
    }

    @Test
    void testCambioDeEstado() {
        Excusa excusa = new Excusa();
        excusa.setEstado(EstadoExcusa.PENDIENTE);

        assertEquals(EstadoExcusa.PENDIENTE, excusa.getEstado());

        excusa.setEstado(EstadoExcusa.APROBADA);
        assertEquals(EstadoExcusa.APROBADA, excusa.getEstado());
    }
}