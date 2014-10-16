package br.com.starcode.agenda.dao.memory.mysql;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.starcode.agenda.TestConfig;
import br.com.starcode.agenda.dao.AbstractUsuarioTest;
import br.com.starcode.agenda.dao.UsuarioDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
@Profile("test")
@Transactional
public class UsuarioDaoMySqlTest extends AbstractUsuarioTest {
	
	@Autowired
	@Qualifier("mysql")
	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	
}
