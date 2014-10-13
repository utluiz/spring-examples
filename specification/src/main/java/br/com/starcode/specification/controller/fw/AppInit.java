package br.com.autbank.abutils.agendavisitas.webapp.fw;
/**
 * Autbank Projetos e Consultoria Ltda.
 * <br>
 * Criado em 29/06/2012 - 17:19:22
 * <br>
 * @version $Revision$ de $Date$<br>
 *           por $Author$<br>
 * @author luizricardo<br>
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

import com.sun.jersey.json.impl.provider.entity.JacksonArrayProvider;
import com.sun.jersey.json.impl.provider.entity.JacksonObjectProvider;

import br.com.autbank.abutils.agendavisitas.webapp.AnexoService;
import br.com.autbank.abutils.agendavisitas.webapp.AnexoTemporarioService;
import br.com.autbank.abutils.agendavisitas.webapp.HomeService;
import br.com.autbank.abutils.agendavisitas.webapp.SobreService;
import br.com.autbank.abutils.agendavisitas.webapp.EntradaService;
import br.com.autbank.abutils.agendavisitas.webapp.LoginService;
import br.com.autbank.abutils.agendavisitas.webapp.RelatorioService;
import br.com.autbank.abutils.webapp.fw.Ambiente;
import br.com.autbank.abutils.webapp.fw.FormAttributeProvider;
import br.com.autbank.abutils.webapp.fw.FreemarkerProvider;


public class AppInit extends Application {

	private FreemarkerProvider freemarkerProvider;

	public AppInit(@Context ServletContext servletContext) {
		
		//ambiente
		Ambiente.instance().setAmbiente(servletContext.getInitParameter("env"));
		
		//inicia framework provider
		Map<String, Object> variaveis = new HashMap<String, Object>(); 
		freemarkerProvider = new FreemarkerProvider(servletContext, variaveis);
		
	}
	
	public Set<Class<?>> getClasses() {
		
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(HomeService.class);
		s.add(EntradaService.class);
		s.add(SobreService.class);
		s.add(LoginService.class);
		s.add(RelatorioService.class);
		s.add(AnexoService.class);
		s.add(AnexoTemporarioService.class);
		return s;
		
	}
	
	public Set<Object> getSingletons() {
		
		Set<Object> s = new HashSet<Object>();
		
		s.add(freemarkerProvider);
		s.add(new FormAttributeProvider());
		s.add(new JacksonArrayProvider());
		s.add(new JacksonObjectProvider());
		
		return s;
		
	}
	
}
