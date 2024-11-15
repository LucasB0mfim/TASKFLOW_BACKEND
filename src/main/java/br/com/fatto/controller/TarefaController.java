package br.com.fatto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fatto.model.Tarefa;
import br.com.fatto.service.TarefaService;

/**
 * @author Lucas Bomfim 
 */

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) {
        try {
            tarefaService.salvarTarefa(tarefa);
            return ResponseEntity.ok(tarefa);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTarefas() {
        try {
            List<Tarefa> tarefas = tarefaService.listarTarefas();
            return ResponseEntity.ok(tarefas);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarTarefa(@PathVariable String id) {
        try {
            List<Tarefa> tarefas = tarefaService.listarTarefas();
            Tarefa tarefaEncontrada = tarefas.stream()
                .filter(tarefa -> tarefa.getId().equals(id))
                .findFirst()
                .orElse(null);

            if (tarefaEncontrada == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(tarefaEncontrada);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarTarefa(@PathVariable String id, @RequestBody Tarefa tarefaAtualizada) {
        try {
            List<Tarefa> tarefas = tarefaService.listarTarefas();
            boolean existe = tarefas.stream().anyMatch(tarefa -> tarefa.getId().equals(id));

            if (!existe) {
                return ResponseEntity.notFound().build();
            }

            tarefaAtualizada.setId(id);
            tarefaService.salvarTarefa(tarefaAtualizada);
            return ResponseEntity.ok("Tarefa atualizada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao atualizar tarefa: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirTarefa(@PathVariable String id) {
        try {
            List<Tarefa> tarefas = tarefaService.listarTarefas();
            boolean existe = tarefas.stream().anyMatch(tarefa -> tarefa.getId().equals(id));

            if (!existe) {
                return ResponseEntity.notFound().build();
            }

            tarefaService.excluirTarefa(id);
            return ResponseEntity.ok("Tarefa excluída com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao excluir tarefa: " + e.getMessage());
        }
    }
}
