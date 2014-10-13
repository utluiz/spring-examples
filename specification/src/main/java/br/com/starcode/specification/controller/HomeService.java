package br.com.autbank.abutils.agendavisitas.webapp;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.com.autbank.abutils.agendavisitas.models.FiltroEntrada;
import br.com.autbank.abutils.agendavisitas.webapp.fw.BaseService;
import br.com.autbank.abutils.utils.dt.Data;
import br.com.autbank.abutils.webapp.fw.FreemarkerModel;
import br.com.autbank.abutils.webapp.fw.security.RequerAutenticacao;


/**
 * Controlador da página principal de Entradas
 */
@Path("")
public class HomeService extends BaseService {

	@GET
	public Response home() {
		
		if (analista() == null || analista().login == null) {

			return Response.ok(template("home")).build();
			
		} else {
			
			return Response.seeOther(uri("/entradas/")).build();
			
		}
		
	}
	
	/**
	 * Lista entradas
	 */
	@GET
	@Path("entradas")
	@RequerAutenticacao
	public Response entradas() {
		
		ResponseBuilder r = Response.ok();
		
		//salva o filtro submetido pelo usuário para recuperar posteriormente caso os parâmetros não estejam na URL
		armazenarFiltroOrdem(r);
		
		FreemarkerModel t = template("index");
		
		//preenche o objeto do filtro
		FiltroEntrada filtro = filtro();
		
		//verifica se as datas são coerentes
		if (filtro.getDataInicial() != null && filtro.getDataInicial().length() > 0 && filtro.getDataFinal() != null && filtro.getDataFinal().length() > 0) {
			
			Date dataDe = Data.toDate(filtro.getDataInicial());
			Date dataAte = Data.toDate(filtro.getDataFinal());
			if (dataDe.after(dataAte)) {
				
				t.alerta("Ops!", "A data inicial do filtro é maior que a data final!");
				
			}
			
		}
		
		return  r.entity(t).build();

	}
	
}