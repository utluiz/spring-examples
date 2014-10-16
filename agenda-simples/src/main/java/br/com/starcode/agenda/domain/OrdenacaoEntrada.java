package br.com.starcode.agenda.domain;

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

	public String getOrderBy() {
		String coluna = this.coluna;
		if (!"desc".equals(coluna)) {
			coluna = "horario";
		}
		String ordem = this.ordem;
		if (!"desc".equals(ordem)) {
			ordem = "asc";
		}
		return coluna + " " + ordem;
	}
	
}
