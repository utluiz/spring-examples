package br.com.starcode.agenda.model;

public class OrdenacaoEntrada {

	private String coluna;
	private String ordem;
	
	public String getColuna() {
		return coluna;
	}
	
	public void setColuna(String coluna) {
		this.coluna = coluna;
	}
	
	public String getOrdem() {
		return ordem;
	}
	
	public void setOrdem(String ordem) {
		this.ordem = ordem;
	}
	
	public String getColunaDefault() {
		return !"descricao".equals(coluna) ? "horario" : "descricao";
	}
	
	public String getOrdemDefault() {
		return !"desc".equals(ordem) ? "asc" : "desc";
	}
	
	public String getOrderBy() {
		return getColunaDefault() + " " + getOrdemDefault();
	}
	
}
