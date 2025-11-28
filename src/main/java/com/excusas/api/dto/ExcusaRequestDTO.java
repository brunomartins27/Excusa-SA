package com.excusas.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExcusaRequestDTO {
    @NotNull(message = "Legajo es obligatorio")
    private String legajoEmpleado;

    @NotNull(message = "Motivo es obligatorio")
    private String motivo;

    private String descripcion;
}