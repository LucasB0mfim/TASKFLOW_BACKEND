package br.com.fatto.taskflow.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * @author Lucas Bomfim 
 */

@Entity
public class Tarefa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false )
	private String nome;
	
	@Column(nullable = false )
	private BigDecimal custo;
	
	@Column(nullable = false )
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataLimite;
	
	@Column(nullable = false )
	private Integer ordem;
	
	public Tarefa() {}
	
	public Tarefa(Long id, String nome, BigDecimal custo, LocalDate dataLimite, Integer ordem) {
		this.id = id;
		this.nome = nome;
		this.custo = custo;
		this.dataLimite = dataLimite;
		this.ordem = ordem;
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
}
