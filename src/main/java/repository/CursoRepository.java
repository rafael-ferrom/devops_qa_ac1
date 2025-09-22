package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{

}
