package com.excusas.api.repository;

import com.excusas.api.model.Empleado;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests de Integración - EmpleadoRepository")
class EmpleadoRepositoryTest {

    @Autowired
    private EmpleadoRepository repository;

    @Test
    @DisplayName("Debe encontrar un empleado por su legajo si existe")
    void testFindByLegajo_Exito() {

        Empleado empleado = new Empleado();
        empleado.setLegajo("L-100");
        empleado.setNombre("Carlos Test");
        empleado.setEmail("carlos@test.com");

        repository.save(empleado);

        Optional<Empleado> resultado = repository.findByLegajo("L-100");

        assertTrue(resultado.isPresent(), "Debería haber encontrado al empleado");
        assertEquals("Carlos Test", resultado.get().getNombre());
    }

    @Test
    @DisplayName("Debe retornar vacío (Empty) si el legajo no existe")
    void testFindByLegajo_NoEncontrado() {
        Optional<Empleado> resultado = repository.findByLegajo("L-999");

        assertTrue(resultado.isEmpty(), "No debería encontrar un legajo inexistente");
    }
}
