package com.excusas.api.service;

import com.excusas.api.dto.ExcusaRequestDTO;
import com.excusas.api.dto.EncargadoDTO;
import com.excusas.api.dto.EmpleadoDTO;

import com.excusas.api.model.Empleado;
import com.excusas.api.model.Encargado;
import com.excusas.api.model.Excusa;
import com.excusas.api.model.Prontuario;

import com.excusas.api.model.TipoExcusa;
import com.excusas.api.model.EstadoExcusa;
import com.excusas.api.model.ModoTrabajo;

import com.excusas.api.repository.ExcusaRepository;
import com.excusas.api.repository.EmpleadoRepository;
import com.excusas.api.repository.EncargadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExcusaService {

    @Autowired private ExcusaRepository excusaRepo;
    @Autowired private EmpleadoRepository empRepo;
    @Autowired private EncargadoRepository encargadoRepo;

    @Autowired private ProntuarioService prontuarioService;

    @Autowired private NotificationService notificationService;

    /**
     * Registra una nueva excusa y hace el proceso de avaliación.
     * @param dto Datos de excusa recebidos de API.
     * @return LA excusa guarda el estado atuaclizado.
     */

    @Transactional
    public Excusa registrarExcusa(ExcusaRequestDTO dto) {
        Empleado emp = empRepo.findByLegajo(dto.getLegajoEmpleado())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado"));

        TipoExcusa tipo;
        try {
            tipo = TipoExcusa.valueOf(dto.getMotivo().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de excusa inválido");
        }

        Excusa excusa = new Excusa();
        excusa.setEmpleado(emp);
        excusa.setTipo(tipo);
        excusa.setDescripcion(dto.getDescripcion());
        excusa.setFechaRegistro(LocalDate.now());
        excusa.setEstado(EstadoExcusa.PENDIENTE);

        procesarExcusaEnCadena(excusa);

        Excusa guardada = excusaRepo.save(excusa);

        enviarNotificacionResultado(guardada);

        return guardada;
    }

    private void procesarExcusaEnCadena(Excusa excusa) {
        List<Encargado> cadena = encargadoRepo.findAll(Sort.by("ordenPrioridad"));
        boolean atendida = false;

        for (Encargado encargado : cadena) {
            if (encargado.puedeManejar(excusa.getTipo())) {
                EstadoExcusa resultado = encargado.evaluar();
                excusa.setEstado(resultado);
                excusa.setEncargadoAprobador(encargado.getNombreCargo());
                atendida = true;

                if (resultado == EstadoExcusa.RECHAZADA) {
                    prontuarioService.registrarSancion(excusa.getEmpleado(), "Rechazada por " + encargado.getNombreCargo());
                }
                break;
            }
        }

        if (!atendida) {
            excusa.setEstado(EstadoExcusa.RECHAZADA);

            prontuarioService.registrarSancion(excusa.getEmpleado(), "Sin encargado disponible para este motivo");
        }
    }

    private void enviarNotificacionResultado(Excusa excusa) {
        if (excusa.getEmpleado().getEmail() != null) {
            String asunto = "Excusas S.A. - Actualización de Estado";
            String cuerpo = "Estimado/a " + excusa.getEmpleado().getNombre() +
                    ", su excusa ha sido " + excusa.getEstado() +
                    " por " + (excusa.getEncargadoAprobador() != null ? excusa.getEncargadoAprobador() : "el sistema") + ".";

            notificationService.enviarEmail(excusa.getEmpleado().getEmail(), asunto, cuerpo);
        }
    }

    public List<Excusa> buscarPorLegajo(String legajo) {
        return excusaRepo.findByEmpleadoLegajo(legajo);
    }

    public List<Excusa> todasLasExcusas() {
        return excusaRepo.findAll();
    }

    public List<Excusa> excusasRechazadas() {
        return excusaRepo.findByEstado(EstadoExcusa.RECHAZADA);
    }

    public List<Excusa> busquedaAvanzada(String legajo, LocalDate desde, LocalDate hasta) {
        return excusaRepo.findByEmpleadoLegajoAndFechaRegistroBetween(legajo, desde, hasta);
    }

    @Transactional
    public int eliminarExcusas(LocalDate fechaLimite) {
        List<Excusa> lista = excusaRepo.findByFechaRegistroBefore(fechaLimite);
        excusaRepo.deleteAll(lista);
        return lista.size();
    }

    @Transactional
    public Encargado agregarEncargado(EncargadoDTO dto) {
        Encargado e = new Encargado();
        e.setNombreCargo(dto.getNombreCargo());
        e.setModo(ModoTrabajo.valueOf(dto.getModo().toUpperCase()));
        e.setOrdenPrioridad(dto.getOrden());

        List<TipoExcusa> tipos = dto.getMotivos().stream()
                .map(s -> TipoExcusa.valueOf(s.toUpperCase()))
                .collect(Collectors.toList());
        e.setTiposManejables(tipos);

        return encargadoRepo.save(e);
    }

    public Empleado crearEmpleado(EmpleadoDTO dto) {
        Empleado e = new Empleado();
        e.setLegajo(dto.getLegajo());
        e.setNombre(dto.getNombre());
        e.setEmail(dto.getEmail());
        return empRepo.save(e);
    }

    public void cambiarModoEncargado(Long id, String nuevoModo) {
        Encargado e = encargadoRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Encargado no encontrado"));
        e.setModo(ModoTrabajo.valueOf(nuevoModo.toUpperCase()));
        encargadoRepo.save(e);
    }

    public List<Encargado> listarEncargados() {
        return encargadoRepo.findAll(Sort.by("ordenPrioridad"));
    }

    public List<Prontuario> listarProntuarios() {
        return prontuarioService.listarTodos();
    }

    public List<Empleado> listarEmpleados() {
        return empRepo.findAll();
    }
}