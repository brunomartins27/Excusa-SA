package com.excusas.api.service;

import com.excusas.api.model.Empleado;
import com.excusas.api.model.Prontuario;
import com.excusas.api.repository.ProntuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios del Servicio de Prontuarios")
class ProntuarioServiceTest {

    @Mock
    private ProntuarioRepository prontuarioRepo;

    @InjectMocks
    private ProntuarioService service;

    @Test
    @DisplayName("Debe listar todos los prontuarios llamando al repositorio")
    void listarTodos() {
        when(prontuarioRepo.findAll()).thenReturn(List.of(new Prontuario(), new Prontuario()));

        List<Prontuario> resultado = service.listarTodos();

        assertEquals(2, resultado.size(), "Debería retornar 2 prontuarios");
        verify(prontuarioRepo).findAll();
    }

    @Test
    @DisplayName("Debe registrar una sanción correctamente")
    void registrarSancion() {
        Empleado empleado = new Empleado();
        empleado.setLegajo("E001");
        String motivo = "Falta grave";

        when(prontuarioRepo.save(any(Prontuario.class))).thenAnswer(i -> i.getArguments()[0]);

        Prontuario resultado = service.registrarSancion(empleado, motivo);

        assertNotNull(resultado);
        assertEquals("Falta grave", resultado.getMotivo());
        assertEquals(empleado, resultado.getEmpleado());
        assertNotNull(resultado.getFechaCreacion(), "La fecha debe generarse automáticamente");

        verify(prontuarioRepo).save(any(Prontuario.class));
    }

    @Test
    @DisplayName("Debe filtrar prontuarios por empleado (Lógica en memoria)")
    void buscarPorEmpleado() {
        Empleado emp1 = new Empleado();
        emp1.setLegajo("TARGET");

        Empleado emp2 = new Empleado();
        emp2.setLegajo("OTHER");

        Prontuario p1 = new Prontuario(emp1, "Motivo 1");
        Prontuario p2 = new Prontuario(emp2, "Motivo 2");
        Prontuario p3 = new Prontuario(emp1, "Motivo 3");

        when(prontuarioRepo.findAll()).thenReturn(List.of(p1, p2, p3));

        List<Prontuario> resultado = service.buscarPorEmpleado("TARGET");

        assertEquals(2, resultado.size(), "Debería encontrar solo los 2 prontuarios de TARGET");
        assertTrue(resultado.stream().allMatch(p -> p.getEmpleado().getLegajo().equals("TARGET")));
    }
}
