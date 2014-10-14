package br.com.autbank.abutils.agendavisitas.webapp;

import java.util.Date;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import br.com.starcode.specification.domain.*;
import br.com.autbank.abutils.agendavisitas.utils.JPA;
import br.com.autbank.abutils.agendavisitas.webapp.fw.BaseService;
import br.com.autbank.abutils.agendavisitas.webapp.fw.ValidacaoFormulario;
import br.com.autbank.abutils.utils.Log;
import br.com.autbank.abutils.webapp.fw.FreemarkerModel;
import br.com.autbank.abutils.webapp.fw.security.RequerAutenticacao;


/**
 * Controlador das Entradas
 */
@Path("entrada")
public class EntradaService extends BaseService {

	/**
	 * Verifica se há erros no formulário de uma entrada
	 */
	private ValidacaoFormulario verificaErros(Entrada entrada) {
		
		ValidacaoFormulario erros = new ValidacaoFormulario();
		
		if (entrada.getData() == null) {
			
			erros.obrigatorio("data", "Data");
			
		}
		
		if (entrada.getAnalista() == null || entrada.getAnalista().getLogin() == null || entrada.getAnalista().getLogin().length() == 0) {
			
			erros.obrigatorio("analista.login", "Analista");
			
		}
		
		if (entrada.getCliente() == null || entrada.getCliente().getCodigo() == null || entrada.getCliente().getCodigo().length() == 0) {
			
			erros.obrigatorio("cliente.codigo", "Cliente");
			
		}
		
		if (entrada.getPeriodo() == null || entrada.getPeriodo().length() == 0) {
			
			erros.obrigatorio("periodo", "Período");
			
		} else {
			
			if (entrada.getPeriodo().length() > 100) {
				
				erros.tamanho("periodo", "Período", 100);
				
			}
			
		}
		
		if (entrada.getAssunto() == null || entrada.getAssunto().length() == 0) {
			
			erros.obrigatorio("assunto", "Assunto");
			
		} else {
			
			if (entrada.getAssunto().length() > 5000) {
				
				erros.tamanho("assunto", "Assunto", 5000);
				
			}
			
		}
		
		return erros;

	}
	
	/**
	 * Exibe formulário para adicionar uma entrada 
	 */
	@GET
	@RequerAutenticacao
	@Path("incluir")
	public FreemarkerModel novaEntrada() {
		
		Entrada novaEntrada = new Entrada();
		novaEntrada.setData(new Date());
		novaEntrada.setAnalista(analista());
		novaEntrada.setCliente(null);
		novaEntrada.setPeriodo("");
		novaEntrada.setAssunto("");
		
		return template("entrada")
				.add("validacao", new ValidacaoFormulario())
				.add("entrada", novaEntrada)
				.add("transacao", Long.toString(System.currentTimeMillis()));
		
	}
	
	/**
	 * Adiciona uma entrada 
	 */
	@POST
	@Path("incluir")
	@RequerAutenticacao
	public FreemarkerModel incluirEntrada(@FormParam("transacao") Long transacao, Entrada entrada) {
		
		//verifica erros
		ValidacaoFormulario erros = verificaErros(entrada);
		if (!erros.ok()) {
			
			return template("entrada")
					.erro("", erros.mensagem())
					.add("entrada", entrada)
					.add("validacao", erros)
					.add("transacao",  Long.toString(transacao));
			
		}
		
		try {
			
			JPA.begin();
			
			//inclui entrada
			Entrada.incluir(entrada);
			
			//log entrada
			criarLogEntrada(entrada, TipoAcao.INCLUSAO);
			
			//transforma anexos temporários em definitivos
			List<AnexoTemporario> anexos = AnexoTemporario.list(transacao);
			for (AnexoTemporario anexoTemporario : anexos) {
				
				Anexo novoAnexo = new Anexo();
				novoAnexo.setAnalista(analista());
				novoAnexo.setData(anexoTemporario.getData());
				novoAnexo.setDescricao(anexoTemporario.getDescricao()); //será preenchido dentro incluir
				novoAnexo.setNomeArquivo(anexoTemporario.getNomeArquivo());
				novoAnexo.setEntrada(entrada);
				
				//incluir anexo
				Anexo.incluir(novoAnexo, anexoTemporario.getFile());
				
				//log anexo
				LogAcao log = new LogAcao();
				log.setTipoAcao(TipoAcao.INCLUSAO);
				log.setTipoItem(TipoItem.ANEXO);
				log.setUsuario(analista());
				log.setAnalista(novoAnexo.getAnalista());
				log.setIdItem(novoAnexo.getId());
				LogAcao.incluir(log);
				
				AnexoTemporario.delete(anexoTemporario.getId());
				
			}
			
			JPA.em().flush();
			JPA.em().refresh(entrada);
			
			//limpar anexos antigos, se houver
			AnexoTemporario.clear();
			
			JPA.commit();
			
			return template("index").sucesso("", "Entrada de código <strong>" + entrada.getId() + "</strong> incluída com sucesso!");
			
		} catch (Exception e) {
			
			Log.error(e);
			try {
			
				JPA.rollback();
				
			} catch (Exception e2) {
				
				Log.error(e2);
				
			}
			
			return template("entrada")
					.erro(e)
					.add("entrada", entrada)
					.add("validacao", erros)
					.add("transacao",  Long.toString(transacao));
			
		}
		
	}
	
	/**
	 * Exibe formulário para alteração de uma entrada 
	 */
	@GET
	@Path("{id}/editar")
	@RequerAutenticacao
	public FreemarkerModel editarEntrada(@PathParam("id") Long id) {
		
		return template("entrada")
				.add("validacao", new ValidacaoFormulario())
				.add("entrada", Entrada.get(id))
				.add("id", id);
		
	}
	
	/**
	 * Confirma dados da alteração da entrada 
	 */
	@POST
	@RequerAutenticacao
	@Path("{id}/editar")
	public FreemarkerModel alterarEntrada(@PathParam("id") Long id, Entrada entrada) {
		
		//verifica erros
		ValidacaoFormulario erros = verificaErros(entrada);
		if (!erros.ok()) {
			
			return template("entrada")
					.erro("", erros.mensagem())
					.add("entrada", entrada)
					.add("validacao", erros)
					.add("id", id);
			
		}

		try {
		
			JPA.begin();
			
			//altera
			entrada.id = id;
			Entrada.alterar(entrada);
			
			//limpar anexos antigos, se houver
			AnexoTemporario.clear();
			
			//log entrada
			criarLogEntrada(entrada, TipoAcao.ALTERACAO);
			
			JPA.commit();
			
			return template("index").sucesso("", "Entrada de código <strong>" + id + "</strong> alterada com sucesso!");
			
		} catch (Exception e) {
			
			Log.error(e);
			try {
				
				JPA.rollback();
				
			} catch (Exception e2) {
				
				Log.error(e2);
				
			}
			
			return template("entrada")
					.erro(e)
					.add("entrada", entrada)
					.add("validacao", erros)
					.add("id", id);
			
		}
		
	}
	
	/**
	 * Exclui uma entrada 
	 */
	@GET
	@Path("{id}/apagar")
	@RequerAutenticacao
	public FreemarkerModel excluirEntrada(@PathParam("id") Long id) {

		try {
			
			JPA.begin();
			
			//log entrada
			criarLogEntrada(Entrada.get(id), TipoAcao.EXCLUSAO);
			
			//apaga
			Entrada.delete(id);
			
			JPA.commit();
			return template("index").sucesso("", "Entrada de código <strong>" + id + "</strong> excluída com sucesso!");
			
		} catch (Exception e) {
			
			Log.error(e);
			try {
				
				JPA.rollback();
				
			} catch (Exception e2) {
				
				Log.error(e2);
				
			}
			return template("index").erro(e);
			
		}
		
	}
	
	private void criarLogEntrada(Entrada entrada, TipoAcao tipoAcao) {
		
		LogAcao log = new LogAcao();
		log.setTipoAcao(tipoAcao);
		log.setTipoItem(TipoItem.ENTRADA);
		log.setUsuario(analista());
		log.setAnalista(entrada.getAnalista());
		log.setIdItem(entrada.getId());
		LogAcao.incluir(log);
		
	}
	
}