package com.excusas.api.dto;

import lombok.Data;
import java.util.List;

@Data
public class EncargadoDTO {
    private String nombreCargo;
    private List<String> motivos;
    private String modo;
    private Integer orden;
}