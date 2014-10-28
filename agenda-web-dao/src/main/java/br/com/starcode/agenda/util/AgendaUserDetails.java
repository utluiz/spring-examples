package br.com.starcode.agenda.util;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.com.starcode.agenda.domain.Usuario;

public class AgendaUserDetails extends User {

	private static final long serialVersionUID = 1L;
	protected Usuario usuario;
	
	public AgendaUserDetails(
			String username, 
			String password,
			Collection<? extends GrantedAuthority> authorities,
			Usuario usuario) {
		super(username, password, authorities);
		this.usuario = usuario;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

}
