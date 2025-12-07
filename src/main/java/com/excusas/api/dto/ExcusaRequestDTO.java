package com.excusas.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank; // Usar NotBlank para Strings es mejor tipificación que NotNull
import lombok.Data;

@Data
public class ExcusaRequestDTO {
    @NotBlank(message = "El legajo es obligatorio")
    private String legajoEmpleado;

    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;

    private String descripcion;
}
