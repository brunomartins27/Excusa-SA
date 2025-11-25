package com.excusas.api.repository;

import com.excusas.api.model.Encargado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncargadoRepository extends JpaRepository<Encargado, Long> {
    // No necesitamos métodos especiales por ahora
}