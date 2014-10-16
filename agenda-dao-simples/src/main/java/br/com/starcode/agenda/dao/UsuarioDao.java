package br.com.starcode.agenda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import br.com.starcode.agenda.domain.Usuario;

public class UsuarioDao {
	
	private DataSource ds;
	
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}

	public Usuario findByNomeUsuario(String string) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement("select * from usuario where nome_usuario = ?");
			ps.setString(1, string);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Usuario usuario =  new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNomeUsuario(rs.getString("nome_usuario"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setNome(rs.getString("nome"));
				usuario.setUltimoAcesso(rs.getTimestamp("ultimo_acesso"));
				return usuario;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void atualizarUltimoAcesso(Integer id, Date data) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement("update usuario set ultimo_acesso = ? where id = ?");
			ps.setTimestamp(1, new java.sql.Timestamp(data.getTime()));
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
