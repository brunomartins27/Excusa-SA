package com.excusas.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String legajo;

    private String nombre;
    private String email;
}