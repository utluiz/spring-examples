package br.com.starcode.specification.domain;

import java.io.Serializable;

public class Usuario implements Serializable {

	public String login;
	public String senha;

	public Usuario() {
	}

	public String getLogin() {
		return login;
	}
	
	public String getSenha() {
		return senha;
	}
	
	/*public static Usuario get(String login) {
		
		TypedQuery<Usuario> q = JPA.em().createQuery("select u from Usuario u where login = :login", Usuario.class);
		q.setParameter("login", login);
		try {
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		
	}*/
	
}
