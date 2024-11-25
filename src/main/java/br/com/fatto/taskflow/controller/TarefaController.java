package br.com.fatto.taskflow.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fatto.taskflow.dto.TarefaOrdemDTO;
import br.com.fatto.taskflow.model.Tarefa;
import br.com.fatto.taskflow.service.TarefaService;

/**
 * Controlador REST responsável por gerenciar as tarefas.
 * Possui endpoints para criar, atualizar, excluir, listar e reordenar tarefas.
 * 
 * @author Lucas
 */

@RestController
@RequestMapping(path = "api/tarefas")
public class TarefaController {
	
    private static final Logger logger = LoggerFactory.getLogger(TarefaController.class);
	
	private TarefaService tarefaService;
	
	// Injeta a dependência do serviço TarefaService via construtor.
	public TarefaController(TarefaService tarefaService) {
		this.tarefaService = tarefaService;
	}
	
	
	/**
     * Endpoint para listar todas as tarefas ordenadas por sua ordem.
     * 
     * @return Lista de tarefas.
     */
	@GetMapping
	public List<Tarefa> buscarTarefas() {
	    logger.debug("Recebida requisição para listar tarefas ordenadas.");
	    return tarefaService.buscarTodasOrdenadas();
	}
	
	
	/**
     * Endpoint para criar uma nova tarefa.
     * 
     * @param tarefa Dados da tarefa a ser criada.
     * @return A tarefa recém-criada.
     */
	@PostMapping
	public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) {
		logger.info("Recebida requisição para criar nova tarefa: {}", tarefa.getNome());
		Tarefa novaTarefa = tarefaService.salvarTarefa(tarefa);
		return ResponseEntity.ok(novaTarefa);
	}
	
	
	/**
     * Endpoint para atualizar uma tarefa existente.
     * 
     * @param id ID da tarefa a ser atualizada.
     * @param tarefa Dados atualizados da tarefa.
     * @return A tarefa atualizada.
     */
	@PutMapping("/{id}")
	public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefa) {
	    logger.info("Recebida requisição para atualizar tarefa com ID: {}", id);

	    Tarefa tarefaAtualizada = tarefaService.atualizarTarefa(id, tarefa);
	    return ResponseEntity.ok(tarefaAtualizada);
	}
	
	
	/**
     * Endpoint para excluir uma tarefa.
     * 
     * @param id ID da tarefa a ser excluída.
     * @return Resposta sem conteúdo.
     */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirTarefa(@PathVariable Long id) {
		logger.info("Recebida requisição para excluir tarefa com ID: {}", id);
		tarefaService.excluirTarefa(id);
		return ResponseEntity.noContent().build();
	}
	
	
	 /**
     * Endpoint para reordenar tarefas com base em uma lista de ordens fornecidas.
     * 
     * @param tarefasOrdem Lista com os IDs das tarefas e suas novas ordens.
     * @return Resposta sem conteúdo.
     */
	@PutMapping("/reordenar")
	public ResponseEntity<Void> reordenarTarefas(@RequestBody List<TarefaOrdemDTO> tarefasOrdem) {
	    logger.info("Recebida requisição para reordenar tarefas.");
	    tarefaService.reordenarTarefas(tarefasOrdem);
	    return ResponseEntity.noContent().build();
	}
}
