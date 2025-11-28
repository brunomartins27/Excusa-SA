package com.excusas.api.service;

import com.excusas.api.model.Empleado;
import com.excusas.api.model.Prontuario;
import com.excusas.api.repository.ProntuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProntuarioService {

    private final ProntuarioRepository prontuarioRepo;

    public ProntuarioService(ProntuarioRepository prontuarioRepo) {
        this.prontuarioRepo = prontuarioRepo;
    }

    public List<Prontuario> listarTodos() {
        return prontuarioRepo.findAll();
    }

    @Transactional
    public Prontuario registrarSancion(Empleado empleado, String motivo) {
        Prontuario prontuario = new Prontuario(empleado, motivo);
        return prontuarioRepo.save(prontuario);
    }

    public List<Prontuario> buscarPorEmpleado(String legajo) {
        return prontuarioRepo.findAll().stream()
                .filter(p -> p.getEmpleado().getLegajo().equals(legajo))
                .toList();
    }
}