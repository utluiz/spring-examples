package br.com.starcode.agenda.dao.mysqltemplate;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.starcode.agenda.MySqlTestConfig;
import br.com.starcode.agenda.dao.AbstractUsuarioTest;
import br.com.starcode.agenda.dao.UsuarioDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={MySqlTestConfig.class})
@Profile("test")
@Transactional
public class UsuarioDaoMySqlJdbcTemplateTest extends AbstractUsuarioTest {
	
	@Autowired
	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	
}
