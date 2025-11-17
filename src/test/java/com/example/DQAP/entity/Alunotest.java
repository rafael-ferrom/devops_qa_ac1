package com.example.DQAP.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Alunotest {

    private Aluno aluno;

    @BeforeEach
    void setup() {
        aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Rafael");
        aluno.setConclusoes(new ArrayList<>());
        aluno.setMediaGeral(0.0);
    }

    @Test
    void adicionarConclusao_deveVincularAlunoEAdicionarNaLista() {
        Conclusao c = new Conclusao();
        c.setConcluido(true);

        aluno.adicionarConclusao(c);

        assertEquals(1, aluno.getConclusoes().size());
        assertSame(aluno, c.getAluno(), "Conclusão deve referenciar o aluno após adicionar");
    }

    @Test
    void recalcularMedia_semConclusoes_deveFicarZero() {
        aluno.getConclusoes().clear();

        aluno.recalcularMedia();

        assertEquals(0.0, aluno.getMediaGeral(), 0.0001);
    }

    @Test
    void recalcularMedia_deveCalcularApenasConclusoesConcluidas() {
        Curso c1 = new Curso(null, "Curso A", 8.0);
        Curso c2 = new Curso(null, "Curso B", 10.0);
        Curso c3 = new Curso(null, "Curso C", 7.0);

        Conclusao concl1 = new Conclusao(null, aluno, c1, true);
        Conclusao concl2 = new Conclusao(null, aluno, c2, false); // não concluído — deve ser ignorado
        Conclusao concl3 = new Conclusao(null, aluno, c3, true);

        aluno.getConclusoes().add(concl1);
        aluno.getConclusoes().add(concl2);
        aluno.getConclusoes().add(concl3);

        aluno.recalcularMedia();

        // média considerando apenas c1 (8.0) e c3 (7.0) => (8+7)/2 = 7.5
        assertEquals(7.5, aluno.getMediaGeral(), 0.0001);
    }

    @Test
    void recalcularMedia_seNaoHouverConclusoesConcluidas_mediaZero() {
        Curso c1 = new Curso(null, "Curso A", 9.0);
        Conclusao concl1 = new Conclusao(null, aluno, c1, false);

        aluno.getConclusoes().add(concl1);

        aluno.recalcularMedia();

        assertEquals(0.0, aluno.getMediaGeral(), 0.0001);
    }
}