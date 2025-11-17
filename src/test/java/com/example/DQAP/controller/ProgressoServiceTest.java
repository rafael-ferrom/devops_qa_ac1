package com.example.DQAP.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.DQAP.ValueObjects.Email;
import com.example.DQAP.ValueObjects.Senha;
import com.example.DQAP.dto.ProgressoDTO;
import com.example.DQAP.entity.Aluno;
import com.example.DQAP.entity.Conclusao;
import com.example.DQAP.entity.Curso;
import com.example.DQAP.exception.NotFoundException;
import com.example.DQAP.repository.AlunoRepository;
import com.example.DQAP.repository.ConclusaoRepository;
import com.example.DQAP.repository.CursoRepository;
import com.example.DQAP.service.ProgressoService;

@ExtendWith(MockitoExtension.class)
public class ProgressoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private ConclusaoRepository conclusaoRepository;

    @InjectMocks
    private ProgressoService progressoService;

    private Aluno aluno;

    @BeforeEach
    void setup() {
        aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Rafael");
        aluno.setConclusoes(new ArrayList<>());
    }

    @Test
    void calcularProgresso_casoNenhumConcluido_retornarMensagemEspecifica() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

        ProgressoDTO dto = progressoService.calcularProgresso(1L);

        assertEquals("Nenhum curso concluído. Faltam 12 cursos para atingir o bônus.", dto.getMensagem());
        assertEquals(0, dto.getCursosConcluidos());
        assertEquals(0.0, dto.getMedia(), 0.0001);
        assertEquals(12, dto.getFaltamParaBonus());
    }

    @Test
    void calcularProgresso_casoConcluidosMaiorOuIgual5EMediaMaiorQue7_mensagemEspecial() {
        // preparar 5 conclusões concluídas com media > 7
        Curso curso = new Curso(null, "X", 8.0);
        for (int i = 0; i < 5; i++) {
            Conclusao c = new Conclusao(null, aluno, curso, true);
            aluno.getConclusoes().add(c);
        }
        // definir media do aluno > 7 para cair no caso
        aluno.setMediaGeral(7.5);

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

        ProgressoDTO dto = progressoService.calcularProgresso(1L);

        assertEquals("Você concluiu 5 cursos com média acima de 7. Faltam 7 cursos para alcançar o bônus.", dto.getMensagem());
        assertEquals(5, dto.getCursosConcluidos());
        assertEquals(7.5, dto.getMedia(), 0.0001);
        assertEquals(7, dto.getFaltamParaBonus());
    }

    @Test
    void calcularProgresso_casoConcluidosIgual3EMediaMenorQue7_mensagemEspecifica() {
        Curso curso = new Curso(null, "Y", 6.0);
        for (int i = 0; i < 3; i++) {
            Conclusao c = new Conclusao(null, aluno, curso, true);
            aluno.getConclusoes().add(c);
        }
        aluno.setMediaGeral(6.5);

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

        ProgressoDTO dto = progressoService.calcularProgresso(1L);

        assertEquals("Você concluiu 3 cursos com média abaixo de 7. Faltam 10 cursos para atingir o bônus.", dto.getMensagem());
        assertEquals(3, dto.getCursosConcluidos());
        assertEquals(6.5, dto.getMedia(), 0.0001);
        assertEquals(9, dto.getFaltamParaBonus() + 1 - 1); // apenas checagem redundante; mantém consistência
    }

    @Test
    void calcularProgresso_casoGenerico_mensagemPadrao() {
        // 2 conclusões concluídas, media qualquer
        Curso curso = new Curso(null, "Z", 9.0);
        aluno.getConclusoes().add(new Conclusao(null, aluno, curso, true));
        aluno.getConclusoes().add(new Conclusao(null, aluno, curso, true));
        aluno.setMediaGeral(8.5);

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

        ProgressoDTO dto = progressoService.calcularProgresso(1L);

        assertTrue(dto.getMensagem().contains("Cursos concluídos: 2"));
        assertEquals(2, dto.getCursosConcluidos());
        assertEquals(8.5, dto.getMedia(), 0.0001);
        assertEquals(10, dto.getFaltamParaBonus());
    }

    @Test
    void calcularProgresso_quandoAlunoNaoExiste_deveLancarNotFound() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> progressoService.calcularProgresso(1L));
    }

    @Test
    void registrarConclusao_comSucesso_salvaERetorna() {
        Curso curso = new Curso(null, "Java", 9.0);
        curso.setId(2L);

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(cursoRepository.findById(2L)).thenReturn(Optional.of(curso));
        when(conclusaoRepository.save(any(Conclusao.class))).thenAnswer(inv -> inv.getArgument(0));

        Conclusao resultado = progressoService.registrarConclusao(1L, 2L, true);

        assertNotNull(resultado);
        assertEquals(aluno, resultado.getAluno());
        assertEquals(curso, resultado.getCurso());
        assertTrue(resultado.isConcluido());
        verify(conclusaoRepository, times(1)).save(any(Conclusao.class));
    }

    @Test
    void registrarConclusao_quandoAlunoNaoExiste_deveLancarNotFound() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> progressoService.registrarConclusao(1L, 2L, true));
    }

    @Test
    void registrarConclusao_quandoCursoNaoExiste_deveLancarNotFound() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(cursoRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> progressoService.registrarConclusao(1L, 2L, true));
    }
}
