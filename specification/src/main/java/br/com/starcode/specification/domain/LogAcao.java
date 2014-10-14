package br.com.starcode.specification.domain;

import java.io.Serializable;
import java.util.Date;

public class LogAcao implements Serializable {

	public long id;
	public String tipoAcao;
	public String tipoItem;
	public long idItem;
	public Analista usuario;
	public Analista analista;
	public Date data;
	public String descricao;
	
	public LogAcao() {
	}

	public long getId() {
		return id;
	}
	
	public TipoAcao getTipoAcao() {
		return TipoAcao.getValue(tipoAcao);
	}
	
	public void setTipoAcao(TipoAcao tipoAcao) {
		this.tipoAcao = tipoAcao.getCodigo();
	}
	
	public TipoItem getTipoItem() {
		return TipoItem.getValue(tipoItem);
	}
	
	public void setTipoItem(TipoItem tipoItem) {
		this.tipoItem = tipoItem.getCodigo();
	}
	
	public long getIdItem() {
		return idItem;
	}
	
	public void setIdItem(long idItem) {
		this.idItem = idItem;
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
	
	public Analista getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Analista usuario) {
		this.usuario = usuario;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	/*public static List<LogAcao> list(FiltroLogAcao filtro) {
		
		TypedQuery<LogAcao> q = JPA.em().createQuery(
				"select la from LogAcao la " +
				" where (data >= :inicio or :inicio is null) " +
				"	and (data <= :fim or :fim is null) " +
				"	and (analista.login = :analista or :analista is null) " +
				"	and (usuario.login = :usuario or :usuario is null) " +
				" order by data ", 
				LogAcao.class);
		
		q.setParameter("inicio", Data.toDate(filtro.getDataInicial()), TemporalType.DATE);
		q.setParameter("fim", Data.toDate(filtro.getDataFinal()), TemporalType.DATE);
		q.setParameter("analista", filtro.getAnalista());
		q.setParameter("usuario", filtro.getUsuario());
		
		return q.getResultList();
		
	}
	
	public static void incluir(LogAcao logAcao) {
		
		String descricao;
		if (logAcao.getTipoItem().equals(TipoItem.ANEXO)) {
			Anexo anexo = Anexo.get(logAcao.getIdItem());
			Entrada e = anexo.getEntrada();
			if (logAcao.getAnalista().getLogin().equalsIgnoreCase(logAcao.getUsuario().getLogin())) {
				descricao = "{4} {1} o {2} \"" + anexo.getNomeArquivo() + "\" de código \"{3}\" na entrada \"" + e.getId() + "\"";
				if (!e.getAnalista().getLogin().equalsIgnoreCase(logAcao.getUsuario().getLogin())) {
					descricao += " do(a) analista " + e.getAnalista().getNomeResumido();
				}
			} else {
				descricao = "{0} {1} o {2} \"" + anexo.getNomeArquivo() + "\" de código \"{3}\" do(a) analista {4} na entrada \"" + e.getId() + "\"";
				if (!e.getAnalista().getLogin().equalsIgnoreCase(logAcao.getAnalista().getLogin())) {
					descricao += " do(a) analista " + e.getAnalista().getNomeResumido();
				}
			}
		} else {
			if (logAcao.getAnalista().getLogin().equalsIgnoreCase(logAcao.getUsuario().getLogin())) {
				descricao = "{4} {1} a {2} de código \"{3}\"";
			} else {
				descricao = "{0} {1} a {2} de código \"{3}\" do(a) analista {4}";
			}
		}
			
		descricao = MessageFormat.format(descricao, new Object[] { 
				logAcao.getUsuario().getNomeResumido(),
				logAcao.getTipoAcao().getVerbo(),
				logAcao.getTipoItem().getVerbo(),
				logAcao.getIdItem(),
				logAcao.getAnalista().getNomeResumido()
			});
		
		logAcao.setDescricao(descricao);
		logAcao.setData(new Date());
		
		JPA.em().persist(logAcao);
		JPA.em().flush();
		
	}*/
	
}
