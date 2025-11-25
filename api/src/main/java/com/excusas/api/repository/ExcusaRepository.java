package com.excusas.api.repository;

import com.excusas.api.model.Excusa;
import com.excusas.api.model.Enums.EstadoExcusa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExcusaRepository extends JpaRepository<Excusa, Long> {

    List<Excusa> findByEmpleadoLegajo(String legajo);

    List<Excusa> findByEstado(EstadoExcusa estado);

    List<Excusa> findByEmpleadoLegajoAndFechaRegistroBetween(String legajo, LocalDate start, LocalDate end);

    List<Excusa> findByFechaRegistroBefore(LocalDate fecha);
}