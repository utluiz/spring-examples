package br.com.starcode.agenda.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import br.com.starcode.agenda.domain.Usuario;

@Ignore
public abstract class AbstractUsuarioTest {

	protected UsuarioDao usuarioDao;
	
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
