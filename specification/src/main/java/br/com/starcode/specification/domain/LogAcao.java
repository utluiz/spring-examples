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

import java.io.Serializable;
import java.text.MessageFormat;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import br.com.autbank.abutils.agendavisitas.utils.JPA;
import br.com.autbank.abutils.utils.dt.Data;


@Entity
@Table(name = "log_acao")
public class LogAcao implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long id;
			
	@Column(name = "tipo_acao", length = 1)
	public String tipoAcao;
	
	@Column(name = "tipo_item", length = 1)
	public String tipoItem;
	
	@Column(name = "id_item")
	public long idItem;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "usuario", referencedColumnName = "login")
	public Analista usuario;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "analista", referencedColumnName = "login")
	public Analista analista;

	@Temporal(TemporalType.TIMESTAMP)
	public Date data;
	
	@Column(length = 1000)
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
	
	public static List<LogAcao> list(FiltroLogAcao filtro) {
		
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
		
	}
	
}
