package br.com.starcode.agenda.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.starcode.agenda.AppConfig;
import br.com.starcode.agenda.TestConfig;
import br.com.starcode.agenda.domain.Entrada;
import br.com.starcode.agenda.domain.FiltroEntrada;
import br.com.starcode.agenda.domain.OrdenacaoEntrada;
import br.com.starcode.agenda.domain.PrioridadeEntrada;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class, TestConfig.class})
@Profile("test")
@Transactional
public class EntradaDaoMySqlJdbcTemplateTest {
	
	@Autowired
	EntradaDao entradaDao;
	
	@Test
	public void findTest() throws Exception {
		
		Entrada entrada = entradaDao.findById(1);
		assertNotNull(entrada);
		assertEquals("Curso Spring MVC", entrada.getDescricao());
		assertEquals(new Integer(1), entrada.getIdUsuario());
		assertEquals(PrioridadeEntrada.Importantissimo, entrada.getPrioridadeEntrada());
		
	}
	
	@Test
	public void insertUpdateDeleteTest() throws Exception {

		//insere
		Date horario = new Date(); 
		Entrada nova = new Entrada();
		nova.setHorario(horario);
		nova.setDescricao("descrição nova");
		nova.setPrioridadeEntrada(PrioridadeEntrada.NadaDeMais);
		nova.setIdUsuario(1);
		entradaDao.insert(nova);
		
		//verifica se inseriu
		Entrada entrada = entradaDao.findById(nova.getId());
		assertNotNull(entrada);
		assertEquals("descrição nova", entrada.getDescricao());
		assertEquals(nova.getId(), entrada.getId());
		assertEquals(new Integer(1), entrada.getIdUsuario());
		assertEquals(PrioridadeEntrada.NadaDeMais, entrada.getPrioridadeEntrada());
		assertEquals(
				new SimpleDateFormat("dd/MM/yyyy").format(horario),
				new SimpleDateFormat("dd/MM/yyyy").format(entrada.getHorario()));
		
		//atualiza
		entrada.setDescricao("descrição nova 2");
		int count = entradaDao.update(entrada);
		assertEquals(1, count);
		
		//verifica se atualizou
		entrada = entradaDao.findById(nova.getId());
		assertEquals("descrição nova 2", entrada.getDescricao());
		
		//remove
		count = entradaDao.delete(entrada.getId());
		assertEquals(1, count);
		
		//verifica se removeu
		entrada = entradaDao.findById(nova.getId());
		assertNull(entrada);
		
	}
	
	@Test
	public void filterByDateTest() throws Exception {

		List<Entrada> entradas;
		FiltroEntrada filtro = new FiltroEntrada();
		OrdenacaoEntrada ordem = new OrdenacaoEntrada();
		
		//listar tudo (deve trazer todo os registros em ordem de data ascendente)
		entradas = entradaDao.search(filtro, ordem);
		assertEquals(3, entradas.size());
		assertEquals(new Integer(1), entradas.get(0).getId());
		assertEquals(new Integer(2), entradas.get(1).getId());
		assertEquals(new Integer(3), entradas.get(2).getId());
		
		//listar tudo (deve trazer todo os registros em ordem de data descendente)
		ordem.setOrdem("desc");
		entradas = entradaDao.search(filtro, ordem);
		assertEquals(3, entradas.size());
		assertEquals(new Integer(3), entradas.get(0).getId());
		assertEquals(new Integer(2), entradas.get(1).getId());
		assertEquals(new Integer(1), entradas.get(2).getId());
		
		//listar por data (deve ignorar a hora e trazer tudo do dia)
		ordem.setOrdem(null);
		filtro.setDe(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2014-10-14 08:01:00"));
		filtro.setAte(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2014-10-14 12:00:00"));
		entradas = entradaDao.search(filtro, ordem);
		assertEquals(2, entradas.size());
		
		//listar por data (deve trazer tudo) >= 14
		ordem.setOrdem(null);
		filtro.setDe(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2014-10-14 08:01:00"));
		filtro.setAte(null);
		entradas = entradaDao.search(filtro, ordem);
		assertEquals(3, entradas.size());
		
		//listar por data (deve trazer somente a última) >= 15
		ordem.setOrdem(null);
		filtro.setDe(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2014-10-15 08:01:00"));
		filtro.setAte(null);
		entradas = entradaDao.search(filtro, ordem);
		assertEquals(1, entradas.size());
		
		//listar por data (deve trazer tudo) <= 15
		ordem.setOrdem(null);
		filtro.setAte(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2014-10-15 08:01:00"));
		filtro.setDe(null);
		entradas = entradaDao.search(filtro, ordem);
		assertEquals(2, entradas.size());
		
		//listar por data (deve trazer os 2 primeiros) <= 14
		ordem.setOrdem(null);
		filtro.setAte(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2014-10-14 08:01:00"));
		filtro.setDe(null);
		entradas = entradaDao.search(filtro, ordem);
		assertEquals(2, entradas.size());
		
		//listar por data (deve trazer os 2 primeiros em ordem descendente) <= 14
		ordem.setOrdem(null);
		ordem.setOrdem("desc");
		entradas = entradaDao.search(filtro, ordem);
		assertEquals(2, entradas.size());
		assertEquals(new Integer(2), entradas.get(0).getId());
		assertEquals(new Integer(1), entradas.get(1).getId());
		
	}
	
	@Test
	public void filterByDescTest() throws Exception {

		List<Entrada> entradas;
		FiltroEntrada filtro = new FiltroEntrada();
		OrdenacaoEntrada ordem = new OrdenacaoEntrada();
		
		//listar tudo que tem "curso" (deve listar o primeiro e o terceiro)
		filtro.setDescricao("curso");
		entradas = entradaDao.search(filtro, ordem);
		assertEquals(2, entradas.size());
		assertEquals(new Integer(1), entradas.get(0).getId());
		assertEquals(new Integer(3), entradas.get(1).getId());
		
		//inverter ordem
		filtro.setDescricao("curso");
		ordem.setOrdem("desc");
		entradas = entradaDao.search(filtro, ordem);
		assertEquals(2, entradas.size());
		assertEquals(new Integer(3), entradas.get(0).getId());
		assertEquals(new Integer(1), entradas.get(1).getId());
		
		//listar tudo que tem "churras" (deve listar o segundo)
		filtro.setDescricao("churras");
		ordem.setOrdem("");
		entradas = entradaDao.search(filtro, ordem);
		assertEquals(1, entradas.size());
		assertEquals(new Integer(2), entradas.get(0).getId());
		
	}
	
	public void filterTest() throws Exception {
		
		List<Entrada> entradas;
		FiltroEntrada filtro = new FiltroEntrada();
		OrdenacaoEntrada ordem = new OrdenacaoEntrada();
		
		//listar tudo que tem "curso" e data <= 14 (deve listar somente o primeiro)
		filtro.setAte(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2014-10-14 08:01:00"));
		filtro.setDescricao("curso");
		entradas = entradaDao.search(filtro, ordem);
		assertEquals(1, entradas.size());
		assertEquals(new Integer(1), entradas.get(0).getId());
		
	}
	
}
