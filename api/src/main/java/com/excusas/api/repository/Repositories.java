package com.excusas.api.repository;

import com.excusas.api.model.*;
import com.excusas.api.model.Enums.EstadoExcusa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Repositories {

    @Repository
    public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
        Optional<Empleado> findByLegajo(String legajo);
    }

    @Repository
    public interface ExcusaRepository extends JpaRepository<Excusa, Long> {
        List<Excusa> findByEmpleadoLegajo(String legajo);
        List<Excusa> findByEstado(EstadoExcusa estado);

        // Busqueda compleja
        List<Excusa> findByEmpleadoLegajoAndFechaRegistroBetween(String legajo, LocalDate start, LocalDate end);

        List<Excusa> findByFechaRegistroBefore(LocalDate fecha);
    }

    @Repository
    public interface EncargadoRepository extends JpaRepository<Encargado, Long> {
    }

    @Repository
    public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {
    }
}