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
 * Classe de serviço responsável pela lógica de negócio relacionada às tarefas.
 * Realiza operações como salvar, atualizar, excluir e reordenar tarefas.
 * 
 * @author Lucas
 */

@Service
public class TarefaService {

	private static final Logger logger = LoggerFactory.getLogger(TarefaService.class);

	private final TarefaRepository tarefaRepository;

	// Injeta a dependência do repositório TarefaRepository via construtor.
	public TarefaService(TarefaRepository tarefaRepository) {
		this.tarefaRepository = tarefaRepository;
	}

	
	/**
     * Salva uma nova tarefa no banco de dados.
     * 
     * @param tarefa Dados da tarefa a ser salva.
     * @return A tarefa salva.
     */
	public Tarefa salvarTarefa(Tarefa tarefa) {
		logger.info("Tentando salvar tarefa: {}", tarefa.getNome());

		// Verifica se já existe uma tarefa com o mesmo nome.
		tarefaRepository.findByNome(tarefa.getNome()).ifPresent(existingTask -> {
			throw new IllegalArgumentException("Já existe uma tarefa com o nome: " + tarefa.getNome());
		});

		// Define a ordem automaticamente se não fornecida.
		if (tarefa.getOrdem() == null) {
			Integer maiorOrdem = tarefaRepository.findMaxOrdem();
			tarefa.setOrdem(maiorOrdem != null ? maiorOrdem + 1 : 1);
		}

		return tarefaRepository.save(tarefa);
	}
	
	
	/**
     * Atualiza os dados de uma tarefa existente.
     * 
     * @param id ID da tarefa a ser atualizada.
     * @param tarefaAtualizada Dados atualizados da tarefa.
     * @return A tarefa atualizada.
     */
	public Tarefa atualizarTarefa(Long id, Tarefa tarefaAtualizada) {
	    logger.info("Atualizando tarefa com ID: {}", id);

	    Tarefa tarefaExistente = buscarPorId(id);
	    
	    // Verifica se o novo nome já está em uso por outra tarefa.
	    if (!tarefaExistente.getNome().equals(tarefaAtualizada.getNome())) {
	        tarefaRepository.findByNome(tarefaAtualizada.getNome()).ifPresent(existingTask -> {
	            throw new IllegalArgumentException("Já existe uma tarefa com o nome: " + tarefaAtualizada.getNome());
	        });
	    }

	    tarefaExistente.setNome(tarefaAtualizada.getNome());
	    tarefaExistente.setCusto(tarefaAtualizada.getCusto());
	    tarefaExistente.setDataLimite(tarefaAtualizada.getDataLimite());
	    tarefaExistente.setOrdem(tarefaAtualizada.getOrdem());

	    return tarefaRepository.save(tarefaExistente);
	}


	/**
     * Exclui uma tarefa pelo ID.
     * 
     * @param id ID da tarefa a ser excluída.
     */
	public void excluirTarefa(Long id) {
		logger.info("Excluindo tarefa com ID: {}", id);
		tarefaRepository.deleteById(id);
	}

	
	/**
     * Busca todas as tarefas cadastradas.
     * 
     * @return Lista de tarefas.
     */
	public List<Tarefa> buscarTodas() {
		logger.debug("Listando todas as tarefas");
		return tarefaRepository.findAll();
	}

	
	/**
     * Busca todas as tarefas ordenadas pelo campo "ordem".
     * 
     * @return Lista de tarefas ordenadas.
     */
	public List<Tarefa> buscarTodasOrdenadas() {
		logger.debug("Listando todas as tarefas ordenadas.");
		return tarefaRepository.findAllOrderByOrdem();
	}

	
	/**
     * Busca uma tarefa pelo ID.
     * 
     * @param id ID da tarefa.
     * @return A tarefa encontrada.
     * @throws IllegalArgumentException se a tarefa não for encontrada.
     */
	public Tarefa buscarPorId(Long id) {
		logger.debug("Buscando tarefa por ID: {}", id);
		return tarefaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada."));
	}

	
	/**
     * Reordena as tarefas com base em uma lista de ordens fornecida.
     * 
     * @param tarefasOrdem Lista contendo os IDs das tarefas e suas novas ordens.
     */
	@Transactional
	public void reordenarTarefas(List<TarefaOrdemDTO> tarefasOrdem) {
		logger.info("Reordenando tarefas.");

		// Valida se há valores duplicados no campo "ordem".
		Set<Integer> ordemSet = new HashSet<>();
		for (TarefaOrdemDTO tarefaOrdem : tarefasOrdem) {
			if (!ordemSet.add(tarefaOrdem.getOrdem())) {
				throw new IllegalArgumentException("Valores duplicados encontrados no campo 'ordem'.");
			}
		}

		// Atualiza a ordem de cada tarefa.
		for (TarefaOrdemDTO tarefaOrdem : tarefasOrdem) {
			Tarefa tarefaExistente = buscarPorId(tarefaOrdem.getId());
			tarefaExistente.setOrdem(tarefaOrdem.getOrdem());
			tarefaRepository.save(tarefaExistente);
		}
	}
}
