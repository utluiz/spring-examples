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

import br.com.starcode.specification.domain.AnexoTemporario;
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
@Path("anexo/tmp")
public class AnexoTemporarioService extends BaseService {

	/**
	 * Retorna um objeto json correspondente a um anexo temporário para a lista de anexos do formulário de inclusão de entradas
	 */
	private JSONObject getJson(AnexoTemporario anexoTemporario) throws JSONException {
		
		JSONObject anexoJson = new JSONObject();
		anexoJson.put("name", anexoTemporario.getNomeArquivo());
		anexoJson.put("description", anexoTemporario.getDescricao());
		anexoJson.put("update_url", url("/anexo/tmp/" + anexoTemporario.getId() + "/"));
		anexoJson.put("date", Data.imprimeData(anexoTemporario.getData()));
		anexoJson.put("analista", analista().getLogin());
		anexoJson.put("nomeAnalista", analista().getNome());
		anexoJson.put("size", anexoTemporario.getFile().length());
		anexoJson.put("url", url("/anexo/tmp/" + anexoTemporario.getId() + "/"));
		anexoJson.put("delete_url", url("/anexo/tmp/" + anexoTemporario.getId() + "/"));
		anexoJson.put("delete_type", "DELETE");
		
		return anexoJson;
		
	}
	
	/**
	 * Lista os arquivos anexos de uma entrada
	 */
	@GET
	@Path("listar/{idTransacao}")
	@RequerAutenticacao
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarAnexosTemporarios(@PathParam("idTransacao") Long idTransacao) {

		//listar anexos da entrada
		List<AnexoTemporario> anexos = AnexoTemporario.list(idTransacao);
		
		//montar array json para saída
		JSONArray arquivosAnexados = new JSONArray();
		for (AnexoTemporario anexo : anexos) {
			
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
	@Path("enviar/{idTransacao}")
	@RequerAutenticacao
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadAnexoTemporario(
			@PathParam("idTransacao") Long idTransacao,
			FormDataMultiPart form) {
		
		UploadWrapper upload = new UploadWrapper(form, "files[]");
		
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
				AnexoTemporario novoAnexo = new AnexoTemporario();
				novoAnexo.setTransacao(idTransacao);
				novoAnexo.setData(new Date());
				novoAnexo.setDescricao(null); //será preenchido dentro incluir
				novoAnexo.setNomeArquivo(file.getFileName());
				
				//incluir anexo
				AnexoTemporario.incluir(novoAnexo, file);
	
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
	public Response alterarAnexoTemporario(@PathParam("idAnexo") Long idAnexo, JSONObject json) {

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
			AnexoTemporario anexo = AnexoTemporario.get(idAnexo);
			anexo.setDescricao(descricao);
			AnexoTemporario.alterar(anexo);
			
			//prepara resultado
			JSONObject resultado = new JSONObject();
			resultado.put("descricao", anexo.getDescricao());
			
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
	public Response excluirAnexoTemporario(@PathParam("idAnexo") Long idAnexo) {
		
		try {
			
			JPA.begin();
			AnexoTemporario.delete(idAnexo);
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
	public Response downloadAnexoTemporario(@PathParam("idAnexo") Long idAnexo) {
		
		AnexoTemporario anexo = AnexoTemporario.get(idAnexo);
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

}