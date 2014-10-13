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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.autbank.abutils.agendavisitas.utils.JPA;
import br.com.autbank.abutils.utils.dt.Texto;


@Entity
public class Analista implements Serializable {

	@Id
	@Column(name = "login", insertable = false, updatable = false)
	public String login;
	
	@Column(name = "nome", insertable = false, updatable = false)
	public String nome;

	@Column(name = "email", insertable = false, updatable = false)
	public String email;
	
	@Column(name = "tipo", insertable = false, updatable = false)
	public int tipo;
	
	@Column(name = "perfil", insertable = false, updatable = false)
	public int perfil;
	
	@Column(name = "disponibilidade", insertable = false, updatable = false)
	public String disponibilidade;
	
	@Column(name = "opcao_notificacao", insertable = false, updatable = false)
	public int notificacao;
	
	@Column(name = "indlider", insertable = false, updatable = false)
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
	public String getNomeSimplificado() {
		
		String primeiroNome = nome.split("\\s")[0];
		return Texto.apenasPrimeiraMaiuscula(primeiroNome);
		
	}

	/**
	 * Primeiro e último nome
	 */
	public String getNomeResumido() {
		
		String[] partesNome = nome.split("\\s");
		String primeiroNome = Texto.apenasPrimeiraMaiuscula(partesNome[0]);
		if(partesNome.length > 1) {
			
			String ultimoNome = Texto.apenasPrimeiraMaiuscula(partesNome[partesNome.length - 1]);
			return primeiroNome + " " + ultimoNome;
			
		} else {
			
			return primeiroNome;
			
		}
		
	}
	
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

	public static Analista get(String login) {
		
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
	
}
