package com.excusas.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.excusas.api.model.Enums.*;
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

    // Método de negócio para Chain of Responsibility
    public boolean puedeManejar(TipoExcusa tipo) {
        return tiposManejables.contains(tipo);
    }

    // Método de negócio (Lógica do Encargado)
    public EstadoExcusa evaluar() {
        if (this.modo == ModoTrabajo.VAGO) return EstadoExcusa.APROBADA;
        if (this.modo == ModoTrabajo.PRODUCTIVO) return EstadoExcusa.RECHAZADA;
        return EstadoExcusa.APROBADA; // NORMAL (Default)
    }
}