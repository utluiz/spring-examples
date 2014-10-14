package br.com.autbank.abutils.agendavisitas.webapp;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.com.starcode.specification.domain.Analista;
import br.com.starcode.specification.domain.Usuario;
import br.com.autbank.abutils.agendavisitas.utils.Criptografia;
import br.com.autbank.abutils.agendavisitas.webapp.fw.BaseService;
import br.com.autbank.abutils.webapp.fw.Cooky;
import br.com.autbank.abutils.webapp.fw.FreemarkerModel;

/**
 * Controlador de Login
 */
@Path("login")
public class LoginService extends BaseService {

	@GET
	public FreemarkerModel logar() {
		
		return template("home").erro("Permissão negada!", "Você deve estar conectado para acessar a url!");
		
	}
	
	/**
	 * Fazer login
	 */
	@POST
	@Path("entrar")
	public Response login(Usuario usuario) {
		
		FreemarkerModel t = template("home");
		ResponseBuilder r = Response.ok();
		
		if (usuario.login == null || usuario.login.length() == 0 || usuario.senha == null || usuario.senha.length() == 0) {
			
			t.alerta("Ops! Erro de Login...", "Digite os dados de usuário e senha corretamente!");
			
		} else {
			
			Analista analistaBase = Analista.get(usuario.login);
			Usuario usuarioBanco = Usuario.get(usuario.login);
			
			if (analistaBase != null && usuario != null) {
				
				//autenticação
				Criptografia criptografia = new Criptografia();
			    String tmp = criptografia.criptoI(usuario.senha);
			    if (tmp.equals(usuarioBanco.getSenha())) {
			    	
			    	analista(analistaBase);
			    	
			    	Cooky c = new Cooky("login", usuario.login, 365);
			    	r.cookie(c);
			    	t = template("index");
			    	t.add("analista", analistaBase).sucesso("Você foi conectado!", "");
			    	
			    } else {
			    	
			    	t.erro("Ops! Erro de Login...", "Senha inválida!");
			    	
			    }
				
			} else {
				
				t.erro("Ops! Erro de Login...", "Usuário inválida!");
				
			}
			
		}
		
		return r.entity(t).build();
		
	}
	
	/**
	 * Fazer logout
	 */
	@GET
	@Path("sair")
	public Response logout() {
		
		analista(null);
		Cooky c = new Cooky("login", "", 0);
		return Response.ok(
				template("home").info("Você se desconectou!", "Para editar suas entradas, identifique-se novamente!"))
				.cookie(c)
				.build();
		
	}

}