package br.com.fatto.taskflow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.fatto.taskflow.model.Tarefa;

/**
 * Repositório JPA para gerenciar operações de banco de dados relacionadas à entidade Tarefa.
 * Fornece métodos personalizados para consultas específicas.
 * 
 * @author Lucas
 */

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    
	/**
     * Busca uma tarefa pelo nome.
     * 
     * @param nome Nome da tarefa.
     * @return Um Optional contendo a tarefa encontrada ou vazio se não existir.
     */
	Optional<Tarefa> findByNome(String nome);

	
	/**
     * Busca todas as tarefas ordenadas pelo campo "ordem".
     * 
     * @return Lista de tarefas ordenadas.
     */
    @Query("SELECT t FROM Tarefa t ORDER BY t.ordem ASC")
    List<Tarefa> findAllOrderByOrdem();
    
    
    /**
     * Busca o maior valor do campo "ordem" entre as tarefas.
     * 
     * @return O maior valor encontrado ou null se não houver tarefas.
     */
    @Query("SELECT MAX(t.ordem) FROM Tarefa t")
    Integer findMaxOrdem();
}