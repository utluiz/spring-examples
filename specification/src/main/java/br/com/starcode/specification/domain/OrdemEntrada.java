/**
 * Autbank Projetos e Consultoria Ltda.
 * <br>
 * Criado em 26/03/2012 - 10:57:59
 * <br>
 * @version $Revision$ de $Date$<br>
 *           por $Author$<br>
 * @author luizricardo<br>
 */
package br.com.autbank.abutils.agendavisitas.models;


public class OrdemEntrada {

	public String ordenarPor;
	public String ordem;
	
	private String ajustaOrdenacao() {
		
		if (ordenarPor.equals("data")) {
			return "data " + ordem + ", analista.nome " + ordem + ", cliente.nome " + ordem;
		} else if (ordenarPor.equals("analista")) {
			return "analista.nome " + ordem + ", data " + ordem + ", cliente.nome " + ordem;
		} else if (ordenarPor.equals("cliente")) {
			return "cliente.nome " + ordem + ", data " + ordem + ", analista.nome " + ordem;
		} else if (ordenarPor.equals("periodo")) {
			return "data " + ordem + ", periodo " + ordem + ", analista.nome " + ordem;
		} else if (ordenarPor.equals("assunto")) {
			return "SUBSTRING(assunto, 1, 1000) " + ordem + ", data " + ordem + ", analista.nome " + ordem;
		} else if (ordenarPor.equals("id")) {
			return "id " + ordem;
		}
		return ordenarPor + " " + ordem;
		
	}
	
	public OrdemEntrada() {
		
		ordem = "asc";
		ordenarPor = "data";
		
	}
	
	public void setOrdem(String ordem) {
		
		if (ordem != null && ordem.length() > 0 && ("asc".equals(ordem) || "desc".equals(ordem))) {
			
			this.ordem = ordem;
			
		}
		
	}
	
	public String inverso() {
		
		if (this.ordem.equals("asc")) {
			
			return "desc";
			
		} else {
			
			return "asc";
			
		}
		
	}
	
	public void setOrdenarPor(String ordenarPor) {
		
		if (ordenarPor != null && ordenarPor.length() > 0 
				&& ("id".equals(ordenarPor) 
						|| "data".equals(ordenarPor) 
						|| "analista".equals(ordenarPor) 
						|| "cliente".equals(ordenarPor) 
						|| "periodo".equals(ordenarPor) 
						|| "assunto".equals(ordenarPor))) {
			
			this.ordenarPor = ordenarPor;
			
		}
		
	}
	
	public String getOrdenarPorDesc() {
		if (ordenarPor.equals("periodo")) {
			return "período";
		} else if (ordenarPor.equals("id")) { 
			return "código";
		}
		return ordenarPor;
	}
	
	public String getOrdenarPor() {
		return ordenarPor;
	}
	
	public String getOrdem() {
		return ordem;
	}
	
	public String toString() {
		return ajustaOrdenacao();
	}
	
}
