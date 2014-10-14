package br.com.starcode.specification.domain;

import java.io.Serializable;

public class Cliente implements Serializable {

	public String codigo;
	public String nome;
	
	public Cliente() {
	}

	public Cliente(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}
	
	public String getNome() {
		return nome;
	}
	
	/**
	 * Se o nome for grande, tenta diminuir quebrando por "-" ou por espaço
	 */
	public String getNomeResumido() {
		
		if (nome.length() > 30) {
			
			String[] ns = nome.split("-");
			if (ns.length > 1) {
				
				return ns[0];
				
			} else {
				
				ns = nome.split(" ");
				return ns[0];
				
			}
			
		} else {
			
			return nome;
			
		}
		
	}

	/*public static Cliente get(String codigo) {
		
		return JPA.em().find(Cliente.class, codigo);
		
	}
	
	public static List<Cliente> list() {
		
		TypedQuery<Cliente> q = JPA.em().createQuery("select c from Cliente c order by nome", Cliente.class);
		return q.getResultList();
		
	}*/

}
