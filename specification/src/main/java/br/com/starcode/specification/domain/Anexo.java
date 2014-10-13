/**
 * Autbank Projetos e Consultoria Ltda.
 * <br>
 * Criado em 21/03/2012 - 15:35:45
 * <br>
 * @version $Revision$ de $Date$<br>
 *           por $Author$<br>
 * @author luizricardo<br>
 */
package br.com.autbank.abutils.agendavisitas.models;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NoResultException;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import br.com.autbank.abutils.agendavisitas.utils.JPA;
import br.com.autbank.abutils.utils.FileUtil;
import br.com.autbank.abutils.webapp.fw.UploadedFile;


@Entity
public class Anexo implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date data;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "analista", referencedColumnName = "login")
	public Analista analista;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "entrada", referencedColumnName = "id")
	public Entrada entrada;
	
	@Column(length = 250, name = "nome_arquivo")
	public String nomeArquivo;
	
	@Column(length = 255)
	public String descricao;
	
	public Anexo() {
	}

	public long getId() {
		return id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public Analista getAnalista() {
		return analista;
	}

	public void setAnalista(Analista analista) {
		this.analista = analista;
	}

	public Entrada getEntrada() {
		return entrada;
	}
	
	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
	}
	
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	/**
	 * Retorna o local do arquivo no servidor
	 */
	public File getFile() {
		
		return new File(new File(Configuracao.get().getCaminhoAnexos(), Long.toString(getEntrada().getId())), getNomeArquivo());
		
	}

	/**
	 * Retorna o local do arquivo no servidor
	 */
	public static File getFile(Long entradaId, String nomeArquivo) {
		
		return new File(new File(Configuracao.get().getCaminhoAnexos(), Long.toString(entradaId)), nomeArquivo);
		
	}

	public static List<Anexo> list(Long entradaId) {
		
		TypedQuery<Anexo> q = JPA.em().createQuery(
				"select a from Anexo a " +
				" where entrada.id = :entrada " +
				" order by data", 
				Anexo.class);
		
		q.setParameter("entrada", entradaId);
		
		return q.getResultList();
		
	}
	
	private static File incluir(Anexo anexo) {
		
		if(anexo.analista.login == null) {
			anexo.analista = null;
        } else {
        	anexo.analista = Analista.get(anexo.analista.login);
        }
		if(anexo.entrada.id <= 0) {
			anexo.entrada = null;
        } else {
        	anexo.entrada = Entrada.get(anexo.entrada.id);
        }
	    
		//calcula o caminho até a pasta de anexos configurada
		File arquivoDestino = anexo.getFile();
		
		int i = 0;
		String nomeBase = anexo.getNomeArquivo();
		String extensao = FileUtil.ext(nomeBase);		
		String nomeSemExtensao = extensao == null ? nomeBase : nomeBase.substring(0, nomeBase.length() - extensao.length());
		while (arquivoDestino.exists()) {
			
			i++;
			anexo.setNomeArquivo(nomeSemExtensao + " [" + i + "]" + extensao);
			arquivoDestino = anexo.getFile();
			
		}
		
		if (anexo.getDescricao() == null) {
			
			anexo.setDescricao(nomeSemExtensao + (i > 0 ? " [" + Integer.toString(i) + "]" : ""));
			
		}
		
		JPA.em().persist(anexo);
		JPA.em().flush();
		JPA.em().refresh(anexo.getAnalista());
		
		//cria o diretório (se não existir)
		arquivoDestino.getParentFile().mkdirs();
		
		return arquivoDestino;
		
	}
	
	public static void incluir(Anexo anexo, File arquivoOrigem) {
	
		File arquivoDestino = incluir(anexo);
		
		//move o arquivo para a pasta configurada
		arquivoOrigem.renameTo(arquivoDestino);
		
	}

	public static void incluir(Anexo anexo, UploadedFile arquivoEnviado) throws IOException {
		
		File arquivoDestino = incluir(anexo);
		
		//grava o arquivo para a pasta configurada
		arquivoEnviado.write(arquivoDestino);
		
	}

	public static void alterar(Anexo anexo) {
		
		Anexo original = get(anexo.id);
		original.setDescricao(anexo.getDescricao());
		
        JPA.em().merge(anexo);
        JPA.em().flush();
        JPA.em().refresh(anexo.getAnalista());
        
	}

	public static void delete(Long id) {
		
		Anexo anexo = get(id);
		
		//tenta apagar o arquivo
		if (anexo.getFile().exists()) {
			
			anexo.getFile().delete();
			
		}
		
		JPA.em().remove(anexo);
		JPA.em().flush();
		JPA.em().refresh(anexo.getAnalista());
		
	}
	
	public static Anexo get(Long id) {
		
		try {
			return JPA.em().find(Anexo.class, id);
		} catch (NoResultException e) {
			return null;
		}
		
	}
	
}
