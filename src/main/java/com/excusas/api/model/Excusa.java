package com.excusas.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Excusa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaRegistro;
    private String descripcion;

    // AGORA USA O ENUM DIRETO (Não usa mais Enums.TipoExcusa)
    @Enumerated(EnumType.STRING)
    private TipoExcusa tipo;

    @Enumerated(EnumType.STRING)
    private EstadoExcusa estado;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    private String encargadoAprobador;
}