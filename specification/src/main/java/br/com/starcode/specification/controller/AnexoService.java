package br.com.autbank.abutils.agendavisitas.webapp;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.com.autbank.abutils.agendavisitas.models.Anexo;
import br.com.autbank.abutils.agendavisitas.models.Entrada;
import br.com.autbank.abutils.agendavisitas.models.LogAcao;
import br.com.autbank.abutils.agendavisitas.models.TipoAcao;
import br.com.autbank.abutils.agendavisitas.models.TipoItem;
import br.com.autbank.abutils.agendavisitas.utils.JPA;
import br.com.autbank.abutils.agendavisitas.webapp.fw.BaseService;
import br.com.autbank.abutils.utils.Log;
import br.com.autbank.abutils.utils.dt.Data;
import br.com.autbank.abutils.utils.dt.Texto;
import br.com.autbank.abutils.webapp.fw.UploadWrapper;
import br.com.autbank.abutils.webapp.fw.UploadedFile;
import br.com.autbank.abutils.webapp.fw.security.RequerAutenticacao;

import com.sun.jersey.multipart.FormDataMultiPart;


/**
 * Controlador de anexos
 */
@Path("anexo")
public class AnexoService extends BaseService {

	/**
	 * Retorna um objeto json correspondente a um anexo para a lista de anexos do formulário de alteração de entrada
	 * @throws JSONException 
	 */
	private JSONObject getJson(Anexo anexo) throws JSONException {
		
		JSONObject anexoJson = new JSONObject();
		anexoJson.put("name", anexo.getNomeArquivo());
		anexoJson.put("description", anexo.getDescricao());
		anexoJson.put("update_url", url("/anexo/" + anexo.getId() + "/"));
		anexoJson.put("date", Data.imprimeData(anexo.getData()));
		anexoJson.put("analista", anexo.getAnalista().getLogin());
		anexoJson.put("nomeAnalista", anexo.getAnalista().getNome());
		anexoJson.put("size", anexo.getFile().length());
		anexoJson.put("url", url("/anexo/" + anexo.getId() + "/"));
		anexoJson.put("delete_url", url("/anexo/" + anexo.getId() + "/"));
		anexoJson.put("delete_type", "DELETE");
		
		return anexoJson;
		
	}
	
	/**
	 * Lista os arquivos anexos de uma entrada
	 */
	@GET
	@Path("listar/{idEntrada}")
	@RequerAutenticacao
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarAnexos(@PathParam("idEntrada") Long idEntrada) {

		//listar anexos da entrada
		List<Anexo> anexos = Anexo.list(idEntrada);
		
		//montar array json para saída
		JSONArray arquivosAnexados = new JSONArray();
		for (Anexo anexo : anexos) {
			
			//inclui no array
			try {
				
				arquivosAnexados.put(getJson(anexo));
				
			} catch (JSONException e) {
				
				Log.error(e);
				
			}
			
		}
		
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("files", arquivosAnexados);
		return json(mapa).build();
		
	}
		
	/**
	 * Trata upload de um arquivo de anexo
	 */
	@POST
	@Path("enviar/{idEntrada}")
	@RequerAutenticacao
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadAnexo(
			@PathParam("idEntrada") Long idEntrada, 
			FormDataMultiPart form) {
		
		UploadWrapper upload = new UploadWrapper(form, "files[]");
		
		//recupera entrada que vai receber o anexo
		Entrada entrada = Entrada.get(idEntrada);
		
		//verifica se tem algum anexo
		if (upload.isEmpty()) {
			
			return Response.status(Status.BAD_REQUEST).entity("Nenhum anexo encontrado!").build();
			
		}
		
		//montar array json para saída
		JSONArray arquivosAnexados = new JSONArray();
		
		try {
			
			JPA.begin();
			
			//trata arquivos individualmente
			for (UploadedFile file : upload) {
				
				//preenche dados do anexo
				Anexo novoAnexo = new Anexo();
				novoAnexo.setAnalista(analista());
				novoAnexo.setData(new Date());
				novoAnexo.setDescricao(null); //será preenchido dentro incluir
				novoAnexo.setNomeArquivo(file.getFileName());
				novoAnexo.setEntrada(entrada);
				
				//incluir anexo
				Anexo.incluir(novoAnexo, file);
				
				//cria log
				criarLogAnexo(novoAnexo, TipoAcao.INCLUSAO);
	
				//inclui no array
				arquivosAnexados.put(getJson(novoAnexo));
				
			}
			
			JPA.commit();
			
			Map<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("files", arquivosAnexados);
			return json(mapa).build();
			
		} catch (Exception e) {
			
			Log.error(e);
			try {
				
				JPA.rollback();
				
			} catch (Exception e2) {
				
				Log.error(e2);
				
			}
			return Response.serverError().entity(e).build();
			
		}
		
	}
	
	/**
	 * Envia o arquivo anexo
	 */
	@POST
	@Path("{idAnexo}")
	@RequerAutenticacao
	@Produces(MediaType.APPLICATION_JSON)
	public Response alterarAnexo(@PathParam("idAnexo") Long idAnexo, JSONObject json) {

		//recupera descrição do json
		String descricao;
		try {
			descricao = json.getString("descricao");
			if (Texto.ehVazio(descricao)) {
				throw new RuntimeException("Descrição não pode ser vazia!");
			}
		} catch (JSONException e) {
			return Response.serverError().entity(e).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.toString()).build();
		}
		
		try {
			
			JPA.begin();
			
			//atualiza anexo
			Anexo anexo = Anexo.get(idAnexo);
			anexo.setDescricao(descricao);
			Anexo.alterar(anexo);
			
			//prepara resultado
			JSONObject resultado = new JSONObject();
			resultado.put("descricao", anexo.getDescricao());
			
			//cria log
			criarLogAnexo(anexo, TipoAcao.ALTERACAO);
			
			JPA.commit();
			return Response.ok(resultado).build();
			
		} catch (Exception e) {
			
			Log.error(e);
			try {
				
				JPA.rollback();
				
			} catch (Exception e2) {
				
				Log.error(e2);
				
			}
			return Response.serverError().entity(e).build();
			
		}
		
	}
	
	/**
	 * Exclui o anexo
	 */
	@DELETE
	@Path("{idAnexo}")
	@RequerAutenticacao
	@Produces(MediaType.APPLICATION_JSON)
	public Response excluirAnexo(@PathParam("idAnexo") Long idAnexo) {
		
		try {
			
			JPA.begin();
			
			//cria log
			criarLogAnexo(Anexo.get(idAnexo), TipoAcao.EXCLUSAO);
			
			//apaga
			Anexo.delete(idAnexo);
			
			JPA.commit();
			
			return Response.ok(Boolean.TRUE).build();
		
		} catch (Exception e) {
			
			Log.error(e);
			try {
				
				JPA.rollback();
				
			} catch (Exception e2) {
				
				Log.error(e2);
				
			}
			return Response.serverError().entity(e).build();
			
		}
		
	}
	
	/**
	 * Envia o arquivo anexo para o usuário
	 */
	@GET
	@Path("{idAnexo}")
	public Response downloadAnexo(@PathParam("idAnexo") Long idAnexo) {
		
		Anexo anexo = Anexo.get(idAnexo);
		File arquivoAnexo = anexo.getFile();
		
		if (arquivoAnexo.getName().toLowerCase().endsWith(".htm") || arquivoAnexo.getName().toLowerCase().endsWith(".html")) {
			
			return Response.ok(arquivoAnexo)
					.type(MediaType.TEXT_HTML)
					.build();
			
		} else {
			
			return Response.ok(arquivoAnexo)
					.header("Content-disposition", "attachment; filename=\"" + anexo.getNomeArquivo() +"\"")
					.header("Content-Transfer-Encoding", "binary")
					.build();
			
		}
			
	}
	
	protected void criarLogAnexo(Anexo anexo, TipoAcao tipoAcao) {
		
		LogAcao log = new LogAcao();
		log.setTipoAcao(tipoAcao);
		log.setTipoItem(TipoItem.ANEXO);
		log.setUsuario(analista());
		log.setAnalista(anexo.getAnalista());
		log.setIdItem(anexo.getId());
		LogAcao.incluir(log);
		
	}

}