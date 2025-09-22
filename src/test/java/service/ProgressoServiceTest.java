package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import dto.ProgressoDTO;
import entity.Aluno;
import entity.Conclusao;
import entity.Curso;
import repository.AlunoRepository;

public class ProgressoServiceTest {
	
	 private AlunoRepository alunoRepository;
	    private ProgressoService progressoService;

	    @BeforeEach
	    void setup() {
	        alunoRepository = Mockito.mock(AlunoRepository.class);
	        progressoService = new ProgressoService(alunoRepository);
	    }

	    @Test
	    void deveIndicarQueFaltam7CursosQuandoAlunoConclui5EMediaAcima7() {
	        Aluno aluno = new Aluno();
	        aluno.setId(1L);
	        aluno.setMediaGeral(8.0);
	        aluno.setConclusoes(Arrays.asList(
	                criarConclusao(aluno, true),
	                criarConclusao(aluno, true),
	                criarConclusao(aluno, true),
	                criarConclusao(aluno, true),
	                criarConclusao(aluno, true)
	        ));

	        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

	        ProgressoDTO progresso = progressoService.calcularProgresso(1L);

	        assertEquals("Você concluiu 5 cursos com média acima de 7. Faltam 7 cursos para alcançar o bônus.", progresso.getMensagem());
	        assertEquals(5, progresso.getCursosConcluidos());
	        assertEquals(8.0, progresso.getMedia());
	        assertEquals(7, progresso.getFaltamParaBonus());
	    }

	    @Test
	    void deveIndicarQueFaltam10CursosQuandoAlunoConclui3EMediaAbaixo7() {
	        Aluno aluno = new Aluno();
	        aluno.setId(2L);
	        aluno.setMediaGeral(6.0);
	        aluno.setConclusoes(Arrays.asList(
	                criarConclusao(aluno, true),
	                criarConclusao(aluno, true),
	                criarConclusao(aluno, true)
	        ));

	        when(alunoRepository.findById(2L)).thenReturn(Optional.of(aluno));

	        ProgressoDTO progresso = progressoService.calcularProgresso(2L);

	        assertEquals("Você concluiu 3 cursos com média abaixo de 7. Faltam 10 cursos para atingir o bônus.", progresso.getMensagem());
	        assertEquals(3, progresso.getCursosConcluidos());
	        assertEquals(6.0, progresso.getMedia());
	        assertEquals(9, progresso.getFaltamParaBonus()); // regra geral
	    }

	    @Test
	    void deveIndicarQueFaltam12CursosQuandoAlunoNaoConcluiuNenhum() {
	        Aluno aluno = new Aluno();
	        aluno.setId(3L);
	        aluno.setMediaGeral(0.0);
	        aluno.setConclusoes(Arrays.asList());

	        when(alunoRepository.findById(3L)).thenReturn(Optional.of(aluno));

	        ProgressoDTO progresso = progressoService.calcularProgresso(3L);

	        assertEquals("Nenhum curso concluído. Faltam 12 cursos para atingir o bônus.", progresso.getMensagem());
	        assertEquals(0, progresso.getCursosConcluidos());
	        assertEquals(0.0, progresso.getMedia());
	        assertEquals(12, progresso.getFaltamParaBonus());
	    }

	    private Conclusao criarConclusao(Aluno aluno, boolean concluido) {
	        Conclusao c = new Conclusao();
	        c.setAluno(aluno);
	        c.setCurso(new Curso());
	        c.setConcluido(concluido);
	        return c;
	    }

}
