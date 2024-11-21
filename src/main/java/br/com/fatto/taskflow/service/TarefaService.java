package br.com.fatto.taskflow.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.fatto.taskflow.dto.TarefaOrdemDTO;
import br.com.fatto.taskflow.model.Tarefa;
import br.com.fatto.taskflow.repository.TarefaRepository;
import jakarta.transaction.Transactional;

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
	    if (tarefa.getOrdem() == null) {
	    	logger.info("Salvando nova tarefa: {}", tarefa.getNome());
	        Integer maiorOrdem = tarefaRepository.findMaxOrdem();
	        tarefa.setOrdem(maiorOrdem != null ? maiorOrdem + 1 : 1);
	    }
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

	public List<Tarefa> buscarTodasOrdenadas() {
		logger.debug("Listando todas as tarefas ordenadas.");
		return tarefaRepository.findAllOrderByOrdem();
	}

	public Tarefa buscarPorId(Long id) {
		logger.debug("Buscando tarefa por ID: {}", id);
		return tarefaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada."));
	}

	@Transactional
	public void reordenarTarefas(List<TarefaOrdemDTO> tarefasOrdem) {
	    logger.info("Reordenando tarefas.");

	    // Validação de duplicidade e consistência
	    Set<Integer> ordemSet = new HashSet<>();
	    for (TarefaOrdemDTO tarefaOrdem : tarefasOrdem) {
	        if (!ordemSet.add(tarefaOrdem.getOrdem())) {
	            throw new IllegalArgumentException("Valores duplicados encontrados no campo 'ordem'.");
	        }
	    }

	    // Atualizar ordens no banco
	    for (TarefaOrdemDTO tarefaOrdem : tarefasOrdem) {
	        Tarefa tarefaExistente = buscarPorId(tarefaOrdem.getId());
	        tarefaExistente.setOrdem(tarefaOrdem.getOrdem());
	        tarefaRepository.save(tarefaExistente);
	    }
	}
}
