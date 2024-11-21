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
 * @author Lucas Bomfim 
 */

@RestController
@RequestMapping(path = "api/tarefas")
public class TarefaController {
	
    private static final Logger logger = LoggerFactory.getLogger(TarefaController.class);
	
	private TarefaService tarefaService;
	
	public TarefaController(TarefaService tarefaService) {
		this.tarefaService = tarefaService;
	}
	
	@GetMapping
	public List<Tarefa> buscarTarefas() {
	    logger.debug("Recebida requisição para listar tarefas ordenadas.");
	    return tarefaService.buscarTodasOrdenadas();
	}
	
	@PostMapping
	public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) {
		logger.info("Recebida requisição para criar nova tarefa: {}", tarefa.getNome());
		Tarefa novaTarefa = tarefaService.salvarTarefa(tarefa);
		return ResponseEntity.ok(novaTarefa);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefa) {
		logger.info("Recebida requisição para atualizar tarefa com ID: {}", id);
		
		Tarefa tarefaExistente = tarefaService.buscarPorId(id);
		tarefaExistente.setNome(tarefa.getNome());
		tarefaExistente.setCusto(tarefa.getCusto());
		tarefaExistente.setDataLimite(tarefa.getDataLimite());
		tarefaExistente.setOrdem(tarefa.getOrdem());
		
		Tarefa tarefaAtualizada = tarefaService.salvarTarefa(tarefaExistente);
		return ResponseEntity.ok(tarefaAtualizada);		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirTarefa(@PathVariable Long id) {
		logger.info("Recebida requisição para excluir tarefa com ID: {}", id);
		tarefaService.excluirTarefa(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/reordenar")
	public ResponseEntity<Void> reordenarTarefas(@RequestBody List<TarefaOrdemDTO> tarefasOrdem) {
	    logger.info("Recebida requisição para reordenar tarefas.");
	    tarefaService.reordenarTarefas(tarefasOrdem);
	    return ResponseEntity.noContent().build();
	}
}
