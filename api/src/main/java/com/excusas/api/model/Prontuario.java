package com.excusas.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Prontuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaCreacion;
    private String motivo;

    @ManyToOne
    private Empleado empleado;

    public Prontuario(Empleado empleado, String motivo) {
        this.empleado = empleado;
        this.motivo = motivo;
        this.fechaCreacion = LocalDateTime.now();
    }
}