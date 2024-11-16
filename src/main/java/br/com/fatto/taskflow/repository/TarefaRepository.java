package br.com.fatto.taskflow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fatto.taskflow.model.Tarefa;

/**
 * @author Lucas Bomfim 
 */

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long>{
	Optional<Tarefa> findByNome(String nome);
}
