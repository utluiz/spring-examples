package br.com.starcode.specification.domain;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

public class AnexoTemporario implements Serializable {

	public long id;
	public long transacao;
	public Date data;
	public String nomeArquivo;
	public String descricao;
	
	public AnexoTemporario() {
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
	
	public void setTransacao(long transacao) {
		this.transacao = transacao;
	}
	
	public long getTransacao() {
		return transacao;
	}
	
	/**
	 * Retorna o local do arquivo no servidor
	 */
	public File getFile() {
		
		return new File(new File(Configuracao.get().getCaminhoAnexos(), Long.toString(transacao)), getNomeArquivo());
		
	}

	/**
	 * Retorna o local do arquivo no servidor
	 */
	public static File getFile(Long transacao, String nomeArquivo) {
		
		return new File(new File(Configuracao.get().getCaminhoAnexos(), Long.toString(transacao)), nomeArquivo);
		
	}

	/*public static List<AnexoTemporario> list(Long transacao) {
		
		TypedQuery<AnexoTemporario> q = JPA.em().createQuery(
				"select at from AnexoTemporario at " +
				" where transacao = :transacao " +
				" order by data", 
				AnexoTemporario.class);
		
		q.setParameter("transacao", transacao);
		
		return q.getResultList();
		
	}

	public static void incluir(AnexoTemporario anexoTemporario, UploadedFile arquivoEnviado) throws IOException {
		
		//calcula o caminho até a pasta de anexos configurada
		File arquivoDestino = anexoTemporario.getFile();
		
		int i = 0;
		String nomeBase = anexoTemporario.getNomeArquivo();
		String extensao = FileUtil.ext(nomeBase);		
		String nomeSemExtensao = extensao == null ? nomeBase : nomeBase.substring(0, nomeBase.length() - extensao.length());
		while (arquivoDestino.exists()) {
			
			i++;
			anexoTemporario.setNomeArquivo(nomeSemExtensao + " [" + i + "]" + extensao);
			arquivoDestino = anexoTemporario.getFile();
			
		}
		
		if (anexoTemporario.getDescricao() == null) {
			
			anexoTemporario.setDescricao(nomeSemExtensao + (i > 0 ? " [" + Integer.toString(i) + "]" : ""));
			
		}
		
		JPA.em().persist(anexoTemporario);
		JPA.em().flush();
		
		//cria o diretório (se não existir)
		arquivoDestino.getParentFile().mkdirs();
		
		//grava o arquivo para a pasta configurada
		arquivoEnviado.write(arquivoDestino);
		
	}

	public static void alterar(AnexoTemporario anexoTemporario) {
		
		AnexoTemporario original = get(anexoTemporario.id);
		original.setDescricao(anexoTemporario.getDescricao());
		
        JPA.em().merge(anexoTemporario);
        JPA.em().flush();
        
	}

	public static void delete(Long id) {
		
		AnexoTemporario anexo = get(id);
		
		//tenta apagar o arquivo
		if (anexo.getFile().exists()) {
			
			anexo.getFile().delete();
			
		}
		if (anexo.getFile().getParentFile().exists()) {
			
			anexo.getFile().getParentFile().delete();
			
		}
		
		JPA.em().remove(anexo);
		JPA.em().flush();
		
	}
	
	*//**
	 * Limpa anexos temporários antigos (sujeira).
	 * Deleta tudo que for mais antigo que um dia.
	 *//*
	public static void clear() {
		
		TypedQuery<AnexoTemporario> q = JPA.em().createQuery(
				"select at from AnexoTemporario at where data < :data", 
				AnexoTemporario.class);
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		q.setParameter("data", c.getTime());
		
		List<AnexoTemporario> anexosAntigos = q.getResultList();
		List<File> pastasTransacao = new ArrayList<File>();
		for (AnexoTemporario anexoTemporario : anexosAntigos) {

			pastasTransacao.add(anexoTemporario.getFile().getParentFile());
			if (anexoTemporario.getFile().exists()) {
				
				anexoTemporario.getFile().delete();
				JPA.em().remove(anexoTemporario);
				
			}
			
		}
		
		for (File file : pastasTransacao) {
			
			if (file.exists()) {
				
				file.delete();
				
			}
			
		}
		
		JPA.em().flush();
		
	}
	
	public static AnexoTemporario get(Long id) {
		
		try {
			
			return JPA.em().find(AnexoTemporario.class, id);
			
		} catch (NoResultException e) {
			
			return null;
			
		}
		
	}*/
	
}
