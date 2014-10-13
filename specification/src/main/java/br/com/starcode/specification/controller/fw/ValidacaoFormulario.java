/**
 * Autbank Projetos e Consultoria Ltda.
 * <br>
 * Criado em 01/08/2012 - 11:53:17
 * <br>
 * @version $Revision$ de $Date$<br>
 *           por $Author$<br>
 * @author luizricardo<br>
 */
package br.com.autbank.abutils.agendavisitas.webapp.fw;

import java.util.HashMap;
import java.util.Map;


public class ValidacaoFormulario {

	public Map<String, ErroValidacao> erros = new HashMap<String, ErroValidacao>();
	
	public ValidacaoFormulario() {
	}
	
	private static String ERRO_OBRIGATORIO = "O campo {0} é de preenchimento obrigatório"; 
	private static String ERRO_PERIODO_INVALIDO = "O campo {0} está num período inválido"; 
	private static String ERRO_VALOR_INVALIDO = "O campo {0} contém um valor inválido"; 
	private static String ERRO_TAMANHO_MAXIMO = "O campo {0} deve ter no máximo {1} caracteres"; 
	
	public ValidacaoFormulario obrigatorio(String campo, String nomeCampo) {
		
		erros.put(campo, new ErroValidacao(campo, nomeCampo, null, ERRO_OBRIGATORIO));
		return this;
		
	}
		
	public ValidacaoFormulario periodoInvalido(String campo, String nomeCampo) {
			
		erros.put(campo, new ErroValidacao(campo, nomeCampo, null, ERRO_PERIODO_INVALIDO));
		return this;
			
	}
	
	public ValidacaoFormulario invalido(String campo, String nomeCampo) {
		
		erros.put(campo, new ErroValidacao(campo, nomeCampo, null, ERRO_VALOR_INVALIDO));
		return this;
		
	}
	
	public ValidacaoFormulario tamanho(String campo, String nomeCampo, int maximo) {
		
		erros.put(campo, new ErroValidacao(campo, nomeCampo, new String[] { Integer.toString(maximo)} , ERRO_TAMANHO_MAXIMO));
		return this;
		
	}
	
	public String mensagem() {
		
		StringBuilder sb = new StringBuilder();
		int n = 0;
		for (String k : erros.keySet()) {
			
			ErroValidacao e = erros.get(k);

			if (sb.length() > 0) {
				sb.append("\n");
			}
			sb.append("  ");
			sb.append(++n);
			sb.append(". ");
			sb.append(e.mensagem);
			
		}
		return sb.toString();
		
	}
	
	public boolean ok() {
		
		return erros.isEmpty();
		
	}
	
}
