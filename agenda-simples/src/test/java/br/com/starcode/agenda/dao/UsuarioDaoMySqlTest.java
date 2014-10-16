package br.com.starcode.agenda.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.starcode.agenda.AppConfig;
import br.com.starcode.agenda.TestConfig;
import br.com.starcode.agenda.domain.Usuario;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class, TestConfig.class})
@Profile("test")
@Transactional
public class UsuarioDaoMySqlTest {
	
	@Autowired
	@Qualifier("mysql")
	UsuarioDao usuarioDao;
	
	@Test
	public void findByNomeUsuarioTest() throws Exception {
		
		Usuario usuario = usuarioDao.findByNomeUsuario("luiz");
		assertNotNull(usuario);
		assertEquals("luiz", usuario.getNomeUsuario());
		assertEquals("Luiz Ricardo", usuario.getNome());
		
	}
	
	@Test
	public void atualizarUltimoAcessoTest() throws Exception {
		
		Usuario usuario = usuarioDao.findByNomeUsuario("luiz");
		Date data = new Date();
		usuarioDao.atualizarUltimoAcesso(usuario.getId(), data);
		usuario = usuarioDao.findByNomeUsuario("luiz");
		assertEquals(
				new SimpleDateFormat("dd/MM/yyyy").format(data),
				new SimpleDateFormat("dd/MM/yyyy").format(usuario.getUltimoAcesso()));
		
	}
	
}
