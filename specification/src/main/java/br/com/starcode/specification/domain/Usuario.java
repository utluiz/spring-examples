/**
 * Autbank Projetos e Consultoria Ltda.
 * <br>
 * Criado em 21/03/2012 - 15:35:45
 * <br>
 * @version $Revision$ de $Date$<br>
 *           por $Author$<br>
 * @author luizricardo<br>
 */
package br.com.autbank.abutils.agendavisitas.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.autbank.abutils.agendavisitas.utils.JPA;
import br.com.autbank.abutils.webapp.fw.Formulario;


@Entity
@Formulario
public class Usuario implements Serializable {

	@Id
	@Column(name = "login", insertable = false, updatable = false)
	public String login;

	@Column(name = "senha", insertable = false, updatable = false)
	public String senha;

	public Usuario() {
	}

	public String getLogin() {
		return login;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public static Usuario get(String login) {
		
		TypedQuery<Usuario> q = JPA.em().createQuery("select u from Usuario u where login = :login", Usuario.class);
		q.setParameter("login", login);
		try {
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		
	}
	
}
