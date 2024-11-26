package br.com.fatto.taskflow.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidade que representa uma Tarefa no sistema.
 * Contém informações como nome, custo, data limite e ordem.
 * 
 * @author Lucas
 */

@Entity
public class Tarefa {
	
	public enum Status {
		Aberto,
		Concluido,
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true )
	private String nome;
	
	@Column
	private String descricao;
	
	@Column(nullable = false )
	private BigDecimal custo;
	
	@Column(nullable = false )
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataLimite;
	
	@Column(nullable = false )
	private Integer ordem;
	
	@Enumerated(EnumType.STRING)
	@Column
	private Status status;
	
	public Tarefa() {}
	
	public Tarefa(Long id, String nome, String descricao, BigDecimal custo, LocalDate dataLimite, Integer ordem, Status status) {
		this.id = id;
		this.descricao = descricao;
		this.nome = nome;
		this.custo = custo;
		this.dataLimite = dataLimite;
		this.ordem = ordem;
		this.status = status;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public BigDecimal getCusto() {
		return custo;
	}
	
	public void setCusto(BigDecimal custo) {
		this.custo = custo;
	}
	
	public LocalDate getDataLimite() {
		return dataLimite;
	}
	
	public void setDataLimite(LocalDate dataLimite) {
		this.dataLimite = dataLimite;
	}
	
	public Integer getOrdem() {
		return ordem;
	}
	
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
}
