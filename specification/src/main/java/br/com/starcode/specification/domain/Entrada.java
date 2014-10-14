package br.com.starcode.specification.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Entrada implements Serializable {

	public long id;
	public Date data;
	public Analista analista;
	public Cliente cliente;
	public List<Anexo> anexos;
	public String periodo;
	public String assunto;
	
	public Entrada() {
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

	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getAssunto() {
		return assunto;
	}
	
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	
	public List<Anexo> getAnexos() {
		return anexos;
	}
	
	public static FiltroEntrada getFiltro() {
		
		return new FiltroEntrada();
		
	}
	
	public static OrdemEntrada getOrdem() {
		
		return new OrdemEntrada();
		
	}
	
/*	public static List<Entrada> list(OrdemEntrada ordenacao, FiltroEntrada filtro) {
		
		TypedQuery<Entrada> q = JPA.em().createQuery(
				"select e from Entrada e " +
				" where (data >= :inicio or :inicio is null) " +
				"	and (data <= :fim or :fim is null) " +
				"	and (analista.login = :analista or :analista is null) " +
				"	and (cliente.codigo = :cliente or :cliente is null) " +
				(ordenacao != null ? " order by " + ordenacao.toString() : ""), 
				Entrada.class);
		
		q.setParameter("inicio", Data.toDate(filtro.getDataInicial()), TemporalType.DATE);
		q.setParameter("fim", Data.toDate(filtro.getDataFinal()), TemporalType.DATE);
		q.setParameter("analista", filtro.getAnalista());
		q.setParameter("cliente", filtro.getCliente());
		
		return q.getResultList();
		
	}

	public static void incluir(Entrada entrada) {
		
		if(entrada.analista.login == null) {
			entrada.analista = null;
        } else {
        	entrada.analista = Analista.get(entrada.analista.login);
        }
		if(entrada.cliente.codigo == null) {
			entrada.cliente = null;
        } else {
        	entrada.cliente = Cliente.get(entrada.cliente.codigo);
        }
	    JPA.em().persist(entrada);
	    JPA.em().flush();
	    
	}

	public static void alterar(Entrada entrada) {
		
		if(entrada.analista.login == null) {
			entrada.analista = null;
        } else {
        	entrada.analista = Analista.get(entrada.analista.login);
        }
		if(entrada.cliente.codigo == null) {
			entrada.cliente = null;
        } else {
        	entrada.cliente = Cliente.get(entrada.cliente.codigo);
        }
		
		Entrada original = get(entrada.id);
		original.setAnalista(entrada.getAnalista());
		original.setAssunto(entrada.getAssunto());
		original.setCliente(entrada.getCliente());
		original.setPeriodo(entrada.getPeriodo());
		original.setData(entrada.getData());
		
        JPA.em().merge(original);
        JPA.em().flush();
        
	}

	public static void delete(Long id) {

		//remove anexos manualmente
		Entrada e = get(id);
		if (e == null) {
			
			throw new RuntimeException("Entrada não cadastrada!");
			
		}
		
		List<Anexo> anexos = e.getAnexos();
		for (Iterator<Anexo> i = anexos.iterator(); i.hasNext();) {
			Anexo.delete(i.next().getId());
		}
		
		//remove entrada
		JPA.em().remove(get(id));
		JPA.em().flush();
		
	}
	
	public static Entrada get(Long id) {
		
		try {
			return JPA.em().find(Entrada.class, id);
		} catch (NoResultException e) {
			return null;
		}
		
	}
*/	
}
