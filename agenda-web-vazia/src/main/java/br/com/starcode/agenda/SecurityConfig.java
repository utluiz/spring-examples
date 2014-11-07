package br.com.starcode.agenda;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

	@Autowired
	public void configureGlobal(
			AuthenticationManagerBuilder auth,
			final UsuarioService usuarioService) throws Exception {
		
		auth.userDetailsService(new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String username)
					throws UsernameNotFoundException {
				Usuario usuario = usuarioService.autenticarUsuario(username);
				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				authorities.add(new SimpleGrantedAuthority("USUARIO"));
				return new AgendaUserDetails(username, usuario.getSenha(), authorities, usuario);
			}
			
		});
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/entradas/**").access("hasRole('USUARIO')")
			.antMatchers("/entrada/**").access("hasRole('USUARIO')")
			.and().formLogin().loginPage("/").defaultSuccessUrl("/entradas").permitAll()
			.and().logout().logoutUrl("/logout").logoutSuccessUrl("/");

	}

}
