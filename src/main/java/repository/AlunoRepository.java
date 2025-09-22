package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}
