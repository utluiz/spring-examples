/**
 * Autbank Projetos e Consultoria Ltda.
 * <br>
 * Criado em 29/06/2012 - 17:13:30
 * <br>
 * @version $Revision$ de $Date$<br>
 *           por $Author$<br>
 * @author luizricardo<br>
 */
package br.com.autbank.abutils.agendavisitas.webapp.fw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.com.autbank.abutils.agendavisitas.models.Analista;
import br.com.autbank.abutils.agendavisitas.models.Cliente;
import br.com.autbank.abutils.agendavisitas.models.Entrada;
import br.com.autbank.abutils.agendavisitas.models.FiltroEntrada;
import br.com.autbank.abutils.agendavisitas.models.OrdemEntrada;
import br.com.autbank.abutils.utils.Log;
import br.com.autbank.abutils.utils.dt.Data;
import br.com.autbank.abutils.webapp.fw.AbstractService;
import br.com.autbank.abutils.webapp.fw.Cooky;
import br.com.autbank.abutils.webapp.fw.FreemarkerModel;


public abstract class BaseService extends AbstractService {
	
	private static Analista visitante = new Analista();
	
	private Analista analista;
	private FiltroEntrada filtro;
	private OrdemEntrada ordem;
	
	
	/**
	 * @param view Nome do template
	 * @return Instância a ser retornada para renderização da view
	 */
	protected FreemarkerModel template(String view) {
	
		Map<String,Object> defaultModel = new HashMap<String, Object>();
		
		//atributos padrão para os templates
		defaultModel.put("ordem", ordem());
		defaultModel.put("filtro", filtro());
		defaultModel.put("analista", analista());
		defaultModel.put("cumprimento", Data.cumprimento());
		defaultModel.put("path", request.getContextPath());
		
		if (view.equals("index")) {
			
			List<Entrada> entradas = Entrada.list(ordem(), filtro());
			if (entradas.size() == 0) {
				defaultModel.put("totalEntradas", "Nenhuma entrada para o filtro selecionado.");
			} else if (entradas.size() == 1) {
				defaultModel.put("totalEntradas", "Uma entrada para o filtro selecionado.");
			} else {
				defaultModel.put("totalEntradas", entradas.size() + " entradas para o filtro selecionado.");
			}
			defaultModel.put("entradas", entradas);
			
		} 
		
		if (view.equals("index") || view.equals("entrada")) {
			
			defaultModel.put("analistas", Analista.list());
			defaultModel.put("clientes", Cliente.list());
			
		}
		
		return new FreemarkerModel(view + ".ftl", defaultModel);
		
	}
	
	protected void analista(Analista analista) {
		
		if (analista == null) {
			
			this.analista = visitante;
			
		} else {
			
			this.analista = analista;
			
		}
		
	}
	
	protected Analista analista() {
		
		if (analista == null) {
			
			analista = visitante;
			Cookie login = context.getRequest().getCookies().get("login");
			if (login != null && login.getValue() != null && login.getValue().length() > 0) {
				
				try {
					
					Analista analistaTmp = Analista.get(login.getValue());
					if (analistaTmp == null) {
						analista = visitante;
					} else {
						analista = analistaTmp;
					}
					
				} catch (Exception e) {
					
					Log.error(e);
					
				}
				
			}
			
		}
		return analista;
		
	}
	
	protected FiltroEntrada filtro() {
		
		if (filtro == null) {
			
			MultivaluedMap<String, String> mapa = context.getRequest().getCookieNameValueMap();
			
			filtro = Entrada.getFiltro();
			filtro.setDe(mapa.getFirst("de"));
			filtro.setAte(mapa.getFirst("ate"));
			filtro.setCliente(mapa.getFirst("cliente"));
			filtro.setAnalista(mapa.getFirst("analista"));
			filtro.setId(mapa.getFirst("id"));
		
		}
		return filtro;
		
	}
	
	protected OrdemEntrada ordem() {
		
		if (ordem == null) {
			
			MultivaluedMap<String, String> mapa = context.getRequest().getCookieNameValueMap();
			
			ordem = Entrada.getOrdem();
			ordem.setOrdenarPor(mapa.getFirst("ordenarPor"));
			ordem.setOrdem(mapa.getFirst("ordem"));
			
		}
		return ordem;
		
	}
	
	/**
	 * Armazena os filtros aplicados na lista de entradas na sessão (cookie).
	 * Os filtros duram até o navegador ser fechado ou até a opção default ser selecionada.
	 */
	protected void armazenarFiltroOrdem(ResponseBuilder r) {

		boolean limpar = request.getParameter("limparfiltro") != null;
		if (limpar) {
			
			filtro = Entrada.getFiltro();
			ordem = Entrada.getOrdem();
			r.cookie(new Cooky("de", "", -1));
			r.cookie(new Cooky("ate", "", -1));
			r.cookie(new Cooky("analista", "", -1));
			r.cookie(new Cooky("cliente", "", -1));
			r.cookie(new Cooky("id", "", -1));
			r.cookie(new Cooky("ordenarPor", "", -1));
			r.cookie(new Cooky("ordem", "", -1));

		} else {
		
			//recupera do request
			FiltroEntrada def = Entrada.getFiltro();
			filtro = filtro();
			if (request.getParameter("de") != null) {
				filtro.dataInicial = def.dataInicial;
				filtro.setDe(request.getParameter("de"));
				r.cookie(new Cooky("de", filtro.getDataInicial(), request.getParameter("de").equals(def.dataInicial) ? 0 : -1));
			}
			if (request.getParameter("ate") != null) {
				filtro.dataFinal = def.dataFinal;
				filtro.setAte(request.getParameter("ate"));
				r.cookie(new Cooky("ate", filtro.getDataFinal(), request.getParameter("ate").equals(def.dataFinal) ? 0 : -1));
			}
			if (request.getParameter("analista") != null) {
				filtro.analista = def.analista;
				filtro.setAnalista(request.getParameter("analista"));
				r.cookie(new Cooky("analista", filtro.getAnalista(), request.getParameter("analista").equals("") ? 0 : -1));
			}
			if (request.getParameter("cliente") != null) {
				filtro.cliente = def.cliente;
				filtro.setCliente(request.getParameter("cliente"));
				r.cookie(new Cooky("cliente", filtro.getCliente(), request.getParameter("cliente").equals("") ? 0 : -1));
			}
			if (request.getParameter("id") != null) {
				filtro.id = def.id;
				filtro.setId(request.getParameter("id"));
				r.cookie(new Cooky("id", filtro.getId(), request.getParameter("id").equals("") ? 0 : -1));
			}
			
			OrdemEntrada defOrdem = Entrada.getOrdem();
			ordem = ordem();
			if (request.getParameter("ordenarPor") != null) {
				ordem.ordenarPor = defOrdem.ordenarPor;
				ordem.setOrdenarPor(request.getParameter("ordenarPor"));
				r.cookie(new Cooky("ordenarPor", ordem.getOrdenarPor(), request.getParameter("ordenarPor").equals(defOrdem.ordenarPor) ? 0 : -1));
				ordem.ordem = "asc";
			}
			if (request.getParameter("ordem") != null) {
				ordem.ordem = defOrdem.ordem;
				ordem.setOrdem(request.getParameter("ordem"));
				r.cookie(new Cooky("ordem", ordem.getOrdem(), request.getParameter("ordem").equals(defOrdem.ordem) ? 0 : -1));
			}
			
		}
		
	}
	
}
