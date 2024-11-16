package br.com.fatto.taskflow.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.fatto.taskflow.model.Tarefa;
import br.com.fatto.taskflow.repository.TarefaRepository;

/**
 * @author Lucas Bomfim 
 */

@Service
public class TarefaService {
	
	private static final Logger logger = LoggerFactory.getLogger(TarefaService.class);
	
	private final TarefaRepository tarefaRepository;
	
	public TarefaService(TarefaRepository tarefaRepository) {
		this.tarefaRepository = tarefaRepository;
	}
	
	public Tarefa salvarTarefa(Tarefa tarefa) {
		logger.info("Salvando nova tarefa: {}", tarefa.getNome());
		return tarefaRepository.save(tarefa);
	}
	
	public void excluirTarefa(Long id) {
		logger.info("Excluindo tarefa com ID: {}", id);
		tarefaRepository.deleteById(id);
	}
	
	public List<Tarefa> buscarTodas() {
		logger.debug("Listando todas as tarefas");
		return tarefaRepository.findAll();
	}
	
	public Tarefa buscarPorId(Long id) {
		logger.debug("Buscando tarefa por ID: {}", id);
		return tarefaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada."));
	}
}
