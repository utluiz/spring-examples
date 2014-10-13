package br.com.autbank.abutils.agendavisitas.webapp;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.jfree.util.Log;

import br.com.autbank.abutils.agendavisitas.models.Analista;
import br.com.autbank.abutils.agendavisitas.models.Cliente;
import br.com.autbank.abutils.agendavisitas.models.Entrada;
import br.com.autbank.abutils.agendavisitas.models.FiltroEntrada;
import br.com.autbank.abutils.agendavisitas.models.FiltroLogAcao;
import br.com.autbank.abutils.agendavisitas.models.LogAcao;
import br.com.autbank.abutils.agendavisitas.models.OrdemEntrada;
import br.com.autbank.abutils.agendavisitas.reports.GeradorRelatorio;
import br.com.autbank.abutils.agendavisitas.reports.TitleBuilder;
import br.com.autbank.abutils.agendavisitas.webapp.fw.BaseService;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Controlador de relatórios
 */
@Path("relatorio")
public class RelatorioService extends BaseService {
	
	/**
	 * Relatório de Entradas
	 */
	@GET
	@Path("entradas/{formato}")
	public Response entradas(@PathParam("formato") String formato) {
		
		GeradorRelatorio gerador;
		JRDataSource jrds;
		try {
			
			//recuperar valores
			List<Entrada> entradas = Entrada.list(ordem(), filtro());
			jrds = new JRBeanCollectionDataSource(entradas);
			
			//gerador específico
			gerador = GeradorRelatorio.valueOf(formato);
			
		} catch (Throwable e) {
			
			Log.error(e);
			return Response.ok(template("index").erro("", "Formato inválido!")).build();
			
		}
		
		try {
			
			FiltroEntrada filtro = filtro();
			OrdemEntrada ordem = ordem();
			
			//título (filtro)
			TitleBuilder tb = TitleBuilder.create();
			tb.addPeriodo(filtro.getDataInicial(), filtro.getDataFinal());
			if (filtro.getCliente() != null && filtro.getCliente().length() > 0) {
				tb.add(Cliente.get(filtro.getCliente()).getNomeResumido(), "Cliente");
			}
			if (filtro.getAnalista() != null && filtro.getAnalista().length() > 0) {
				tb.add(Analista.get(filtro.getAnalista()).getNomeResumido(), "Analista");
			}
			tb.add("Ordenado por " + ordem.getOrdenarPorDesc() + ("asc".equals(ordem.getOrdem()) ? " ascendente": " descendente")); 
			
			//gerar relatório
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("filtro", tb.toString());
			InputStream is = gerador.gerar("entradas_no_periodo", jrds, parametros);
			
			//retornar
			return Response.ok(is)
					.header("Content-disposition", "attachment; filename=\"entradas_no_periodo." + formato +"\"")
					.header("Content-Transfer-Encoding", "binary")
					.build();
		
		} catch (Throwable e) {
			
			Log.error(e);
			return Response.ok(template("index").erro("Erro ao gerar o relatório!", e.getMessage())).build();
			
		}
		
	}
	
	/**
	 * Relatório de Log
	 */
	@GET
	@Path("log/{formato}")
	public Response log(@PathParam("formato") String formato) {

		//recuperar valores
		FiltroEntrada filtroEntrada = filtro();
		
		FiltroLogAcao filtroLog = new FiltroLogAcao();
		filtroLog.setAnalista(filtroEntrada.getAnalista());
		filtroLog.setDe(filtroEntrada.getDataInicial());
		filtroLog.setAte(filtroEntrada.getDataFinal());
		
		GeradorRelatorio gerador;
		JRDataSource jrds;
		try {
			
			List<LogAcao> logs = LogAcao.list(filtroLog);
			jrds = new JRBeanCollectionDataSource(logs);
			
			//gerador específico
			gerador = GeradorRelatorio.valueOf(formato);
			
		} catch (Throwable e) {
			
			Log.error(e);
			return Response.ok(template("index").erro("", "Formato inválido!")).build();
			
		}
		
		try {
			
			//título (filtro)
			TitleBuilder tb = TitleBuilder.create();
			tb.addPeriodo(filtroLog.getDataInicial(), filtroLog.getDataFinal());
			if (filtroLog.getAnalista() != null && filtroLog.getAnalista().length() > 0) {
				tb.add(Analista.get(filtroLog.getAnalista()).getNomeResumido(), "Analista");
			}
			tb.add("Ordenado por data ascendente"); 
			
			//gerar relatório
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("filtro", tb.toString());
			InputStream is = gerador.gerar("log_no_periodo", jrds, parametros);
			
			//retornar
			return Response.ok(is)
					.header("Content-disposition", "attachment; filename=\"log_no_periodo." + formato +"\"")
					.header("Content-Transfer-Encoding", "binary")
					.build();
		
		} catch (Throwable e) {
			
			Log.error(e);
			return Response.ok(template("index").erro("Erro ao gerar o relatório!", e.getMessage())).build();
			
		}
		
	}

}