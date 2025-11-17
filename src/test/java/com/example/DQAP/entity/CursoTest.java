package com.example.DQAP.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CursoTest {

    @Test
    void construtorComCamposEssenciais_deveAtribuirValores() {
        Curso curso = new Curso(null, "Java Avançado", 9.5);

        assertEquals("Java Avançado", curso.getTitulo());
        assertEquals(9.5, curso.getNotaFinal(), 0.0001);
    }
}