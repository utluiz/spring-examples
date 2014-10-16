package br.com.starcode.agenda.dao.postgresqltemplate;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.starcode.agenda.TestConfig;
import br.com.starcode.agenda.dao.AbstractEntradaTest;
import br.com.starcode.agenda.dao.EntradaDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
@Profile("test")
@Transactional
public class EntradaDaoPostgreSqlJdbcTemplateTest extends AbstractEntradaTest {
	
	@Autowired
	@Qualifier("pg")
	public void setEntradaDao(EntradaDao entradaDao) {
		this.entradaDao = entradaDao;
	}
	
}
