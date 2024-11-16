package br.com.fatto.taskflow.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fatto.taskflow.model.Tarefa;
import br.com.fatto.taskflow.repository.TarefaRepository;

/**
 * @author Lucas Bomfim 
 */

@Service
public class TarefaService {
	
	private final TarefaRepository tarefaRepository;
	
	public TarefaService(TarefaRepository tarefaRepository) {
		this.tarefaRepository = tarefaRepository;
	}
	
	public Tarefa salvarTarefa(Tarefa tarefa) {
		return tarefaRepository.save(tarefa);
	}
	
	public void excluirTarefa(Long id) {
		tarefaRepository.deleteById(id);
	}
	
	public List<Tarefa> buscarTodas() {
		return tarefaRepository.findAll();
	}
	
	public Tarefa buscarPorId(Long id) {
		return tarefaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada."));
	}
}
