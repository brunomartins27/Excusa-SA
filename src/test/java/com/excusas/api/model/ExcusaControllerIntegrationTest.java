package com.excusas.api.model;

import com.excusas.api.dto.EmpleadoDTO;
import com.excusas.api.dto.EncargadoDTO;
import com.excusas.api.dto.ExcusaRequestDTO;
import com.excusas.api.service.ExcusaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ExcusaControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private ExcusaService service;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void testCrearEmpleado() throws Exception {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setLegajo("E001");
        dto.setNombre("Pepe");

        Empleado empMock = new Empleado();
        empMock.setLegajo("E001");

        when(service.crearEmpleado(any())).thenReturn(empMock);

        mockMvc.perform(post("/api/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.legajo").value("E001"));
    }

    @Test
    void testCrearEncargado() throws Exception {
        EncargadoDTO dto = new EncargadoDTO();
        dto.setNombreCargo("Jefe");
        dto.setMotivos(List.of("MEDICA"));
        dto.setModo("NORMAL");

        Encargado encMock = new Encargado();
        encMock.setNombreCargo("Jefe");

        when(service.agregarEncargado(any())).thenReturn(encMock);

        mockMvc.perform(post("/api/encargados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCargo").value("Jefe"));
    }

    @Test
    void testCrearExcusa_Ok() throws Exception {
        ExcusaRequestDTO dto = new ExcusaRequestDTO();
        dto.setLegajoEmpleado("E001");
        dto.setMotivo("MEDICA");

        Excusa excusaMock = new Excusa();
        excusaMock.setId(1L);
        when(service.registrarExcusa(any())).thenReturn(excusaMock);

        mockMvc.perform(post("/api/excusas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void testGetExcusas() throws Exception {
        when(service.todasLasExcusas()).thenReturn(List.of(new Excusa(), new Excusa()));

        mockMvc.perform(get("/api/excusas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testEliminarExcusas_SinFecha_BadRequest() throws Exception {
        mockMvc.perform(delete("/api/excusas/eliminar"))
                .andExpect(status().isBadRequest());
    }
}