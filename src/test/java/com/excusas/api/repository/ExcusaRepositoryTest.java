package com.excusas.api.repository;

import com.excusas.api.model.Empleado;
import com.excusas.api.model.EstadoExcusa;
import com.excusas.api.model.Excusa;
import com.excusas.api.model.TipoExcusa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests de Integración - ExcusaRepository")
class ExcusaRepositoryTest {

    @Autowired private ExcusaRepository excusaRepository;
    @Autowired private EmpleadoRepository empleadoRepository;

    private Empleado empleado;

    @BeforeEach
    void setUp() {
        empleado = new Empleado();
        empleado.setLegajo("E001");
        empleado.setNombre("Test User");
        empleado.setEmail("test@email.com");
        empleadoRepository.save(empleado);
    }

    @Test
    @DisplayName("Debe buscar excusas por legajo del empleado")
    void findByEmpleadoLegajo() {
        Excusa excusa1 = crearExcusa(empleado, LocalDate.now(), EstadoExcusa.PENDIENTE);
        excusaRepository.save(excusa1);

        List<Excusa> resultado = excusaRepository.findByEmpleadoLegajo("E001");

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("E001", resultado.get(0).getEmpleado().getLegajo());
    }

    @Test
    @DisplayName("Debe filtrar excusas por estado")
    void findByEstado() {
        Excusa e1 = crearExcusa(empleado, LocalDate.now(), EstadoExcusa.APROBADA);
        Excusa e2 = crearExcusa(empleado, LocalDate.now(), EstadoExcusa.RECHAZADA);
        excusaRepository.saveAll(List.of(e1, e2));

        List<Excusa> aprobadas = excusaRepository.findByEstado(EstadoExcusa.APROBADA);

        assertEquals(1, aprobadas.size());
        assertEquals(EstadoExcusa.APROBADA, aprobadas.get(0).getEstado());
    }

    @Test
    @DisplayName("Debe buscar por legajo y rango de fechas")
    void findByEmpleadoLegajoAndFechaRegistroBetween() {
        LocalDate hoy = LocalDate.now();
        Excusa e1 = crearExcusa(empleado, hoy.minusDays(5), EstadoExcusa.PENDIENTE); // Dentro del rango
        Excusa e2 = crearExcusa(empleado, hoy.minusDays(20), EstadoExcusa.PENDIENTE); // Fuera del rango
        excusaRepository.saveAll(List.of(e1, e2));

        List<Excusa> resultado = excusaRepository.findByEmpleadoLegajoAndFechaRegistroBetween(
            "E001", hoy.minusDays(10), hoy);

        assertEquals(1, resultado.size());
        assertEquals(e1.getFechaRegistro(), resultado.get(0).getFechaRegistro());
    }

    @Test
    @DisplayName("Debe encontrar excusas anteriores a una fecha límite (para eliminar)")
    void findByFechaRegistroBefore() {
        LocalDate fechaLimite = LocalDate.of(2023, 1, 1);
        Excusa vieja = crearExcusa(empleado, LocalDate.of(2022, 12, 31), EstadoExcusa.APROBADA); // Vieja
        Excusa nueva = crearExcusa(empleado, LocalDate.of(2023, 1, 2), EstadoExcusa.APROBADA);   // Nueva
        excusaRepository.saveAll(List.of(vieja, nueva));

        List<Excusa> resultado = excusaRepository.findByFechaRegistroBefore(fechaLimite);

        assertEquals(1, resultado.size());
        assertEquals(vieja.getId(), resultado.get(0).getId());
    }

    private Excusa crearExcusa(Empleado emp, LocalDate fecha, EstadoExcusa estado) {
        Excusa e = new Excusa();
        e.setEmpleado(emp);
        e.setFechaRegistro(fecha);
        e.setEstado(estado);
        e.setTipo(TipoExcusa.MEDICA);
        e.setDescripcion("Test description");
        return e;
    }
}
