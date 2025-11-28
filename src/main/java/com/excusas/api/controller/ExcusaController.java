package com.excusas.api.controller;

import com.excusas.api.dto.ExcusaRequestDTO;
import com.excusas.api.dto.EmpleadoDTO;
import com.excusas.api.dto.EncargadoDTO;
import com.excusas.api.model.Empleado;
import com.excusas.api.model.Encargado;
import com.excusas.api.model.Excusa;
import com.excusas.api.model.Prontuario;
import com.excusas.api.service.ExcusaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ExcusaController {

    private final ExcusaService service;

    public ExcusaController(ExcusaService service) {
        this.service = service;
    }

    @GetMapping("/prontuarios")
    public List<Prontuario> getProntuarios() {
        return service.listarProntuarios();
    }

    @GetMapping("/empleados")
    public List<Empleado> getEmpleados() {
        return service.listarEmpleados();
    }

    @GetMapping("/encargados")
    public List<Encargado> getEncargados() {
        return service.listarEncargados();
    }

    @GetMapping("/excusas")
    public List<Excusa> getAllExcusas() {
        return service.todasLasExcusas();
    }

    @GetMapping("/excusas/{legajo}")
    public List<Excusa> getExcusasPorLegajo(@PathVariable String legajo) {
        return service.buscarPorLegajo(legajo);
    }

    @GetMapping("/excusas/rechazadas")
    public List<Excusa> getRechazadas() {
        return service.excusasRechazadas();
    }

    @GetMapping("/excusas/busqueda")
    public List<Excusa> buscarExcusas(
            @RequestParam String legajo,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta) {
        return service.busquedaAvanzada(legajo, fechaDesde, fechaHasta);
    }

    @PostMapping("/empleados")
    public ResponseEntity<Empleado> crearEmpleado(@RequestBody EmpleadoDTO dto) {
        return new ResponseEntity<>(service.crearEmpleado(dto), HttpStatus.CREATED);
    }

    @PostMapping("/encargados")
    public ResponseEntity<Encargado> agregarEncargado(@RequestBody EncargadoDTO dto) {
        return ResponseEntity.ok(service.agregarEncargado(dto));
    }

    @PostMapping("/excusas")
    public ResponseEntity<Excusa> crearExcusa(@RequestBody @Valid ExcusaRequestDTO dto) {
        return new ResponseEntity<>(service.registrarExcusa(dto), HttpStatus.CREATED);
    }

    @PutMapping("/encargados/modo")
    public ResponseEntity<?> cambiarModo(@RequestParam Long encargadoId, @RequestParam String modo) {
        service.cambiarModoEncargado(encargadoId, modo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/excusas/eliminar")
    public ResponseEntity<?> eliminarExcusas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaLimite) {

        if (fechaLimite == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "fechaLimite es obligatoria"));
        }

        int count = service.eliminarExcusas(fechaLimite);
        return ResponseEntity.ok(Map.of("mensaje", "Se eliminaron " + count + " excusas anteriores a " + fechaLimite));
    }
}