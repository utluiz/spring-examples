/**
 * Autbank Projetos e Consultoria Ltda.
 * <br>
 * Criado em 26/03/2012 - 10:41:04
 * <br>
 * @version $Revision$ de $Date$<br>
 *           por $Author$<br>
 * @author luizricardo<br>
 */
package br.com.autbank.abutils.agendavisitas.models;

import java.util.Calendar;

import br.com.autbank.abutils.utils.dt.Data;
import br.com.autbank.abutils.webapp.fw.Formulario;


@Formulario
public class FiltroLogAcao {
	
	public String dataInicial;
	public String dataFinal;
	public String analista;
	public String usuario;
	public String tipoItem;
	
	public FiltroLogAcao() {
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		int dif = c.get(Calendar.DAY_OF_WEEK);
		if (dif > 2) {
			c.add(Calendar.DATE, -dif + 2);
		}
		
		dataInicial = Data.imprimeData(c.getTime());
		
		c.add(Calendar.DAY_OF_WEEK, 6);
		dataFinal = Data.imprimeData(c.getTime());
		
		analista = null;
		usuario = null;
		tipoItem = null;
		
	}
	
	public FiltroLogAcao setTipoItem(String tipoItem) {
		if (tipoItem != null && tipoItem.length() > 0) {
			this.tipoItem = tipoItem;
		}
		return this;
	}
	
	public FiltroLogAcao setDe(String dataInicial) {
		if (dataInicial != null && dataInicial.length() > 0) {
			this.dataInicial = dataInicial;
		}
		return this;
	}

	public FiltroLogAcao setAte(String dataFinal) {
		if (dataFinal != null && dataFinal.length() > 0) {
			this.dataFinal = dataFinal;
		}
		return this;
	}

	public FiltroLogAcao setAnalista(String analista) {
		if (analista != null && analista.length() > 0) {
			this.analista = analista;
		}
		return this;
	}

	public FiltroLogAcao setUsuario(String usuario) {
		if (usuario != null && usuario.length() > 0) {
			this.usuario = usuario;
		}
		return this;
	}
	
	public String getId() {
		return usuario;
	}
	
	public String getDataInicial() {
		return dataInicial;
	}
	
	public String getDataFinal() {
		return dataFinal;
	}
	
	public String getAnalista() {
		return analista;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
}