package br.com.fatto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.com.fatto.config.FirebaseInitializer;
import br.com.fatto.model.Tarefa;

@Service
public class TarefaService {

    private final DatabaseReference database;

    public TarefaService(FirebaseInitializer firebaseInitializer) {
        this.database = firebaseInitializer.getDatabase();
    }

    public void salvarTarefa(Tarefa tarefa) {
        if (tarefa == null) {
            throw new IllegalArgumentException("A tarefa não pode ser nula.");
        }

        // Gera um ID aleatório se não for fornecido
        if (tarefa.getId() == null || tarefa.getId().isEmpty()) {
            tarefa.setId(database.child("tarefas").push().getKey());
        }

        database.child("tarefas").child(tarefa.getId()).setValueAsync(tarefa);
    }

    public List<Tarefa> listarTarefas() throws InterruptedException, ExecutionException {
        CompletableFuture<DataSnapshot> future = new CompletableFuture<>();
        database.child("tarefas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                future.complete(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(new RuntimeException(databaseError.getMessage()));
            }
        });

        DataSnapshot snapshot = future.get();
        List<Tarefa> tarefas = new ArrayList<>();
        for (DataSnapshot snap : snapshot.getChildren()) {
            Tarefa tarefa = snap.getValue(Tarefa.class);
            tarefas.add(tarefa);
        }
        return tarefas;
    }
    
    public void excluirTarefa(String id) {
        database.child("tarefas").child(id).removeValueAsync();
    }

}