package br.com.fatto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Lucas Bomfim 
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tarefa {

    private String id;
    private String nome;
    private Double custo;
    private String dataLimite;
    private Integer ordemApresentacao;

    public Tarefa() {}

    public Tarefa(String id, String nome, Double custo, String dataLimite, Integer ordemApresentacao) {
        this.id = id;
        this.nome = nome;
        this.custo = custo;
        this.dataLimite = dataLimite;
        this.ordemApresentacao = ordemApresentacao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getCusto() {
        return custo;
    }

    public void setCusto(Double custo) {
        this.custo = custo;
    }

    public String getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(String dataLimite) {
        this.dataLimite = dataLimite;
    }

    public Integer getOrdemApresentacao() {
        return ordemApresentacao;
    }

    public void setOrdemApresentacao(Integer ordemApresentacao) {
        this.ordemApresentacao = ordemApresentacao;
    }
}
