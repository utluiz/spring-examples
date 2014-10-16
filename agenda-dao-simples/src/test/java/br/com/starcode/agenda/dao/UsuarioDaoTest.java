package br.com.starcode.agenda.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import br.com.starcode.agenda.domain.Usuario;

public class UsuarioDaoTest {
	
	UsuarioDao usuarioDao;
	
	@Before
	public void setup() throws Exception {
		
		usuarioDao = new UsuarioDao();
		usuarioDao.setDataSource(TestSuite.getDataSource());
			
	}

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
		assertEquals(data, usuario.getUltimoAcesso());
		
	}
	
}
