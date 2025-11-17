package com.example.DQAP.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.DQAP.ValueObjects.Email;
import com.example.DQAP.ValueObjects.Senha;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private double mediaGeral;

    @Embedded
    private Email email;

    @Embedded
    private Senha senha;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Conclusao> conclusoes = new ArrayList<>();

    public void adicionarConclusao(Conclusao conclusao) {
        conclusao.setAluno(this);
        conclusoes.add(conclusao);
    }

    public void recalcularMedia() {
        if (conclusoes.isEmpty()) {
            this.mediaGeral = 0.0;
            return;
        }

        double soma = conclusoes.stream()
                .filter(Conclusao::isConcluido)
                .mapToDouble(c -> c.getCurso().getNotaFinal())
                .sum();

        long total = conclusoes.stream()
                .filter(Conclusao::isConcluido)
                .count();

        this.mediaGeral = total > 0 ? soma / total : 0.0;
    }
}
