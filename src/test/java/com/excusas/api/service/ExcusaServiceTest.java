package com.excusas.api.service;

import com.excusas.api.dto.EmpleadoDTO;
import com.excusas.api.dto.EncargadoDTO;
import com.excusas.api.dto.ExcusaRequestDTO;
import com.excusas.api.model.*;
import com.excusas.api.repository.EmpleadoRepository;
import com.excusas.api.repository.EncargadoRepository;
import com.excusas.api.repository.ExcusaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios del Servicio de Excusas")
class ExcusaServiceTest {

    @Mock private ExcusaRepository excusaRepo;
    @Mock private EmpleadoRepository empRepo;
    @Mock private EncargadoRepository encargadoRepo;
    @Mock private ProntuarioService prontuarioService;
    @Mock private NotificationService notificationService;

    @InjectMocks
    private ExcusaService service;

    @Test
    @DisplayName("Debe registrar y aprobar excusa si el encargado lo permite")
    void registrarExcusa_Aprobada() {
        ExcusaRequestDTO dto = new ExcusaRequestDTO();
        dto.setLegajoEmpleado("E001");
        dto.setMotivo("MEDICA");
        dto.setDescripcion("Fiebre");

        Empleado emp = new Empleado();
        emp.setEmail("test@email.com"); // Para que funcione la notificación

        Encargado encargado = new Encargado();
        encargado.setNombreCargo("Medico");
        encargado.setModo(ModoTrabajo.NORMAL);
        encargado.setTiposManejables(List.of(TipoExcusa.MEDICA));

        when(empRepo.findByLegajo("E001")).thenReturn(Optional.of(emp));
        when(encargadoRepo.findAll(any(Sort.class))).thenReturn(List.of(encargado));
        when(excusaRepo.save(any(Excusa.class))).thenAnswer(i -> i.getArguments()[0]); // Devuelve lo que guarda

        Excusa resultado = service.registrarExcusa(dto);

        assertEquals(EstadoExcusa.APROBADA, resultado.getEstado());
        assertEquals("Medico", resultado.getEncargadoAprobador());
        verify(notificationService).enviarEmail(any(), any(), any()); // Verifica que se envió email
        verify(prontuarioService, never()).registrarSancion(any(), any()); // No debe haber sanción
    }

    @Test
    @DisplayName("Debe rechazar y generar prontuario si el encargado es PRODUCTIVO")
    void registrarExcusa_Rechazada() {
        ExcusaRequestDTO dto = new ExcusaRequestDTO();
        dto.setLegajoEmpleado("E001");
        dto.setMotivo("TRANSITO");

        Empleado emp = new Empleado();
        emp.setEmail("test@email.com");

        Encargado jefeDuro = new Encargado();
        jefeDuro.setNombreCargo("Jefe Duro");
        jefeDuro.setModo(ModoTrabajo.PRODUCTIVO); // Rechaza todo
        jefeDuro.setTiposManejables(List.of(TipoExcusa.TRANSITO));

        when(empRepo.findByLegajo("E001")).thenReturn(Optional.of(emp));
        when(encargadoRepo.findAll(any(Sort.class))).thenReturn(List.of(jefeDuro));
        when(excusaRepo.save(any(Excusa.class))).thenAnswer(i -> i.getArguments()[0]);

        Excusa resultado = service.registrarExcusa(dto);

        assertEquals(EstadoExcusa.RECHAZADA, resultado.getEstado());
        verify(prontuarioService, times(1)).registrarSancion(eq(emp), contains("Rechazada por Jefe Duro"));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el empleado no existe")
    void registrarExcusa_EmpleadoNoExiste() {
        ExcusaRequestDTO dto = new ExcusaRequestDTO();
        dto.setLegajoEmpleado("INEXISTENTE");

        when(empRepo.findByLegajo("INEXISTENTE")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.registrarExcusa(dto));
    }


    @Test
    @DisplayName("Debe buscar excusas por legajo")
    void buscarPorLegajo() {
        when(excusaRepo.findByEmpleadoLegajo("E001")).thenReturn(List.of(new Excusa()));

        List<Excusa> result = service.buscarPorLegajo("E001");

        assertFalse(result.isEmpty());
        verify(excusaRepo).findByEmpleadoLegajo("E001");
    }

    @Test
    @DisplayName("Debe listar todas las excusas")
    void todasLasExcusas() {
        service.todasLasExcusas();
        verify(excusaRepo).findAll();
    }

    @Test
    @DisplayName("Debe listar solo las rechazadas")
    void excusasRechazadas() {
        service.excusasRechazadas();
        verify(excusaRepo).findByEstado(EstadoExcusa.RECHAZADA);
    }

    @Test
    @DisplayName("Debe llamar al repositorio para búsqueda avanzada")
    void busquedaAvanzada() {
        LocalDate hoy = LocalDate.now();
        service.busquedaAvanzada("E001", hoy, hoy);
        verify(excusaRepo).findByEmpleadoLegajoAndFechaRegistroBetween("E001", hoy, hoy);
    }


    @Test
    @DisplayName("Debe eliminar excusas anteriores a una fecha")
    void eliminarExcusas() {
        LocalDate fecha = LocalDate.now();
        List<Excusa> lista = List.of(new Excusa(), new Excusa());

        when(excusaRepo.findByFechaRegistroBefore(fecha)).thenReturn(lista);

        int cantidad = service.eliminarExcusas(fecha);

        assertEquals(2, cantidad);
        verify(excusaRepo).deleteAll(lista);
    }

    @Test
    @DisplayName("Debe agregar un nuevo encargado")
    void agregarEncargado() {
        EncargadoDTO dto = new EncargadoDTO();
        dto.setNombreCargo("Jefe");
        dto.setModo("NORMAL");
        dto.setOrden(1);
        dto.setMotivos(List.of("MEDICA"));

        when(encargadoRepo.save(any(Encargado.class))).thenAnswer(i -> i.getArguments()[0]);

        Encargado result = service.agregarEncargado(dto);

        assertEquals("Jefe", result.getNombreCargo());
        assertEquals(ModoTrabajo.NORMAL, result.getModo());
    }

    @Test
    @DisplayName("Debe crear un nuevo empleado")
    void crearEmpleado() {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setLegajo("E999");

        when(empRepo.save(any(Empleado.class))).thenAnswer(i -> i.getArguments()[0]);

        Empleado result = service.crearEmpleado(dto);

        assertEquals("E999", result.getLegajo());
    }

    @Test
    @DisplayName("Debe cambiar el modo de un encargado existente")
    void cambiarModoEncargado() {
        Encargado e = new Encargado();
        e.setModo(ModoTrabajo.NORMAL);

        when(encargadoRepo.findById(1L)).thenReturn(Optional.of(e));

        service.cambiarModoEncargado(1L, "PRODUCTIVO");

        assertEquals(ModoTrabajo.PRODUCTIVO, e.getModo());
        verify(encargadoRepo).save(e);
    }

    @Test
    @DisplayName("Debe listar encargados ordenados")
    void listarEncargados() {
        service.listarEncargados();
        verify(encargadoRepo).findAll(any(Sort.class));
    }

    @Test
    @DisplayName("Debe delegar el listado de prontuarios al ProntuarioService")
    void listarProntuarios() {
        service.listarProntuarios();
        verify(prontuarioService).listarTodos();
    }

    @Test
    @DisplayName("Debe listar empleados")
    void listarEmpleados() {
        service.listarEmpleados();
        verify(empRepo).findAll();
    }
}
