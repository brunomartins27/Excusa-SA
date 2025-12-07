package com.excusas.api.controller;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ExcusaControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private ExcusaService service; // Mockeamos para no usar lógica real

    @Test
    void testCrearExcusa_SinLegajo_DebeRetornar400() throws Exception {

        ExcusaRequestDTO dtoInvalido = new ExcusaRequestDTO();
        dtoInvalido.setMotivo("MEDICA");

        mockMvc.perform(post("/api/excusas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoInvalido)))
            .andExpect(status().isBadRequest()) // 400
            .andExpect(jsonPath("$.error").value("Error de Validación"))
            .andExpect(jsonPath("$.messages.legajoEmpleado").exists());
    }

    @Test
    void testCrearExcusa_SinMotivo_DebeRetornar400() throws Exception {
        ExcusaRequestDTO dtoInvalido = new ExcusaRequestDTO();
        dtoInvalido.setLegajoEmpleado("E001");

        mockMvc.perform(post("/api/excusas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoInvalido)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.messages.motivo").exists());
    }
}
