package com.excusas.api.repository;

import com.excusas.api.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    Optional<Empleado> findByLegajo(String legajo);
}