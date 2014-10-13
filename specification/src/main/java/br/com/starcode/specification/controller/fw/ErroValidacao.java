/**
 * Autbank Projetos e Consultoria Ltda.
 * <br>
 * Criado em 01/08/2012 - 11:39:29
 * <br>
 * @version $Revision$ de $Date$<br>
 *           por $Author$<br>
 * @author luizricardo<br>
 */
package br.com.autbank.abutils.agendavisitas.webapp.fw;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ErroValidacao {

	public String campo;
	public String nomeCampo;
	public String[] params;
	public String mensagem;
	
	public ErroValidacao(String campo, String nomeCampo, String params[], String mensagem) {
		
		this.campo = campo;
		this.nomeCampo = nomeCampo;
		this.params = params;
		
		List<String> p = params != null ? Arrays.asList(params) : new ArrayList<String>();
		p.add(0, nomeCampo);
		this.mensagem = MessageFormat.format(mensagem, p.toArray());
		
	}
	
}
