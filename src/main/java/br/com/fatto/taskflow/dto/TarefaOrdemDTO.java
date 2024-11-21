package br.com.fatto.taskflow.dto;

/**
 * @author Lucas Bomfim 
 */

public class TarefaOrdemDTO {
	
	private Long id;
	
    private Integer ordem;
    
    public TarefaOrdemDTO() {}
    
	public TarefaOrdemDTO(Long id, Integer ordem) {
		this.id = id;
		this.ordem = ordem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
}
