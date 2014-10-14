package br.com.autbank.abutils.agendavisitas.webapp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.com.autbank.abutils.agendavisitas.webapp.fw.BaseService;
import br.com.autbank.abutils.webapp.fw.FreemarkerModel;


/**
 * Controlador Geral
 */
@Path("sobre")
public class SobreService extends BaseService {

	@GET
	public FreemarkerModel sobre() {
		
		return template("sobre");

	}

}