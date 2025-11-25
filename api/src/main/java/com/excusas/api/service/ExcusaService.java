package com.excusas.api.service;

import com.excusas.api.dto.ExcusaRequestDTO;
import com.excusas.api.dto.EncargadoDTO;
import com.excusas.api.dto.EmpleadoDTO;

import com.excusas.api.model.Empleado;
import com.excusas.api.model.Encargado;
import com.excusas.api.model.Excusa;
import com.excusas.api.model.Prontuario;
import com.excusas.api.model.Enums.TipoExcusa;
import com.excusas.api.model.Enums.EstadoExcusa;
import com.excusas.api.model.Enums.ModoTrabajo;

import com.excusas.api.repository.ExcusaRepository;
import com.excusas.api.repository.EmpleadoRepository;
import com.excusas.api.repository.EncargadoRepository;
import com.excusas.api.repository.ProntuarioRepository;

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
    @Autowired private ProntuarioRepository prontuarioRepo;

    // Inyectamos el servicio de notificaciones
    @Autowired private NotificationService notificationService;

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

        // --- LOGICA DE EMAIL ---
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
                    generarProntuario(excusa.getEmpleado(), "Rechazada por " + encargado.getNombreCargo());
                }
                break;
            }
        }

        if (!atendida) {
            excusa.setEstado(EstadoExcusa.RECHAZADA);
            generarProntuario(excusa.getEmpleado(), "Sin encargado disponible para este motivo");
        }
    }

    private void enviarNotificacionResultado(Excusa excusa) {
        String emailEmpleado = excusa.getEmpleado().getEmail();
        String asunto = "Resultado de tu Excusa: " + excusa.getEstado();
        String cuerpo = "Hola " + excusa.getEmpleado().getNombre() + ", tu excusa ha sido "
                + excusa.getEstado() + " por " + excusa.getEncargadoAprobador();

        if(emailEmpleado != null) {
            notificationService.enviarEmail(emailEmpleado, asunto, cuerpo);
        }
    }

    private void generarProntuario(Empleado emp, String motivo) {
        prontuarioRepo.save(new Prontuario(emp, motivo));
    }

    // --- Métodos Auxiliares ---

    public List<Excusa> buscarPorLegajo(String legajo) {
        return excusaRepo.findByEmpleadoLegajo(legajo);
    }

    // MEJORA: Unificación de filtros básicos
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
        return prontuarioRepo.findAll();
    }

    public List<Empleado> listarEmpleados() {
        return empRepo.findAll();
    }
}