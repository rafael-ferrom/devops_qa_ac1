package com.example.DQAP.entity;

import com.example.DQAP.ValueObjects.IdentificacaoCurso;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private IdentificacaoCurso identificacao;

    private String titulo;
    private double notaFinal;

    // Construtor extra apenas com os campos essenciais (opcional)
    public Curso(IdentificacaoCurso identificacao, String titulo, double notaFinal) {
        this.identificacao = identificacao;
        this.titulo = titulo;
        this.notaFinal = notaFinal;
    }
}
