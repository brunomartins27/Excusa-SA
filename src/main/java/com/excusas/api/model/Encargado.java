package com.excusas.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Encargado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCargo; // Ex: "Gerente", "Coach"

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<TipoExcusa> tiposManejables;

    @Enumerated(EnumType.STRING)
    private ModoTrabajo modo;

    private Integer ordenPrioridad; // 1, 2, 3... define a ordem na Chain

    public boolean puedeManejar(TipoExcusa tipo) {
        return tiposManejables.contains(tipo);
    }

    public EstadoExcusa evaluar() {
        if (this.modo == ModoTrabajo.INPRODUCTIVO) return EstadoExcusa.APROBADA;
        if (this.modo == ModoTrabajo.PRODUCTIVO) return EstadoExcusa.RECHAZADA;
        return EstadoExcusa.APROBADA; // NORMAL (Default)
    }
}