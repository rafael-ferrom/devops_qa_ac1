package com.example.DQAP.entity;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ConclusaoTest {

    @Test
    void marcarComoConcluido_deveMudarEstado() {
        Conclusao c = new Conclusao();
        c.setConcluido(false);

        c.marcarComoConcluido();

        assertTrue(c.isConcluido());
    }
}