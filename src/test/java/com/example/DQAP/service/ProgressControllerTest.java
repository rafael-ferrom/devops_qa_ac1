package com.example.DQAP.service;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.DQAP.controller.ProgressController;
import com.example.DQAP.dto.ProgressoDTO;
import com.example.DQAP.entity.Conclusao;
import com.example.DQAP.exception.ApiExceptionHandler;
import com.example.DQAP.exception.NotFoundException;
import com.example.DQAP.service.ProgressoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = ProgressController.class)
@Import(ApiExceptionHandler.class) // garante que o @RestControllerAdvice seja considerado
public class ProgressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProgressoService progressoService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void getProgresso_deveRetornarOkComDTO() throws Exception {
        ProgressoDTO dto = new ProgressoDTO("OK", 2, 8.5, 10);
        when(progressoService.calcularProgresso(1L)).thenReturn(dto);

        mockMvc.perform(get("/progresso/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cursosConcluidos").value(2))
                .andExpect(jsonPath("$.mensagem").value("OK"))
                .andExpect(jsonPath("$.media").value(8.5));
    }

    @Test
    void getProgresso_quandoNaoEncontrado_deveRetornar404() throws Exception {
        when(progressoService.calcularProgresso(1L)).thenThrow(new NotFoundException("Aluno não encontrado"));

        mockMvc.perform(get("/progresso/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Aluno não encontrado"));
    }

    @Test
    void registrarConclusao_deveRetornarOk() throws Exception {
        Conclusao conclusao = new Conclusao();
        when(progressoService.registrarConclusao(1L, 2L, true)).thenReturn(conclusao);

        mockMvc.perform(post("/progresso/1/2")
                        .param("concluido", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void registrarConclusao_quandoException_devePropagar404() throws Exception {
        when(progressoService.registrarConclusao(1L, 2L, true)).thenThrow(new NotFoundException("Curso não encontrado"));

        mockMvc.perform(post("/progresso/1/2")
                        .param("concluido", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Curso não encontrado"));
    }
}
