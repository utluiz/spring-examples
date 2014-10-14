package br.com.starcode.specification.domain;

import java.io.Serializable;

public class Analista implements Serializable {

	public String login;
	public String nome;
	public String email;
	public int tipo;
	public int perfil;
	public String disponibilidade;
	public int notificacao;
	public String lider;
	
	public Analista() {
	}

	public String getLogin() {
		return login;
	}

	public String getNome() {
		return nome;
	}
	
	/**
	 * Apenas primeiro nome
	 */
	/*public String getNomeSimplificado() {
		
		String primeiroNome = nome.split("\\s")[0];
		return Texto.apenasPrimeiraMaiuscula(primeiroNome);
		
	}*/

	/**
	 * Primeiro e último nome
	 */
	/*public String getNomeResumido() {
		
		String[] partesNome = nome.split("\\s");
		String primeiroNome = Texto.apenasPrimeiraMaiuscula(partesNome[0]);
		if(partesNome.length > 1) {
			
			String ultimoNome = Texto.apenasPrimeiraMaiuscula(partesNome[partesNome.length - 1]);
			return primeiroNome + " " + ultimoNome;
			
		} else {
			
			return primeiroNome;
			
		}
		
	}*/
	
	public String getEmail() {
		return email;
	}

	public int getTipo() {
		return tipo;
	}

	public int getPerfil() {
		return perfil;
	}

	public boolean getDisponibilidade() {
		return "S".equals(disponibilidade);
	}
	
	public int getNotificacao() {
		return notificacao;
	}
	
	public boolean isLider() {
		return "S".equals(lider);
	}

/*	public static Analista get(String login) {
		
		TypedQuery<Analista> q = JPA.em().createQuery("select a from Analista a where login = :login and disponibilidade = 'S'", Analista.class);
		q.setParameter("login", login);
		try {
			return q.getSingleResult();
		} catch (NoResultException e) {
			
			return null;
			
		}
		
	}
	
	public static List<Analista> list() {
		
		TypedQuery<Analista> q = JPA.em().createQuery("select a from Analista a where disponibilidade = 'S' order by nome", Analista.class);
		return q.getResultList();
		
	}
*/	
}
