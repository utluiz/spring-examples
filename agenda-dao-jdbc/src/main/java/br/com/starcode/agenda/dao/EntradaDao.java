package br.com.starcode.agenda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.starcode.agenda.domain.Entrada;
import br.com.starcode.agenda.domain.FiltroEntrada;
import br.com.starcode.agenda.domain.OrdenacaoEntrada;
import br.com.starcode.agenda.domain.PrioridadeEntrada;

public class EntradaDao {
	
	private DataSource ds;
	
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}

	public Entrada findById(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(
					"select * from entrada where id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Entrada e = new Entrada();
				e.setId(rs.getInt("id"));
				e.setHorario(rs.getTimestamp("horario"));
				e.setDescricao(rs.getString("descricao"));
				e.setPrioridade(PrioridadeEntrada.fromCode(rs.getString("prioridade")));
				e.setIdUsuario(rs.getInt("id_usuario"));
				return e;
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

	public void insert(Entrada entrada) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(
					"insert into entrada (horario, descricao, prioridade, id_usuario) "
					+ "values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			ps.setTimestamp(1, new Timestamp(entrada.getHorario().getTime()));
			ps.setString(2, entrada.getDescricao());
			ps.setString(3, entrada.getPrioridade().getCode());
			ps.setInt(4, entrada.getIdUsuario());
			ps.executeUpdate();
			ResultSet keyRs = ps.getGeneratedKeys();
			if (keyRs.next()) {
				entrada.setId(keyRs.getInt(1));
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

	public int update(Entrada entrada) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(
					"update entrada set horario=?, descricao=?, prioridade=? where id=?");
			ps.setTimestamp(1, new Timestamp(entrada.getHorario().getTime()));
			ps.setString(2, entrada.getDescricao());
			ps.setString(3, entrada.getPrioridade().getCode());
			ps.setInt(4, entrada.getId());
			return ps.executeUpdate();
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

	public int remove(Integer id) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(
					"delete from entrada where id=?");
			ps.setInt(1, id);
			return ps.executeUpdate();
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

	public List<Entrada> search(
			FiltroEntrada filtro,
			OrdenacaoEntrada ordem) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			//montar query
			con = ds.getConnection();
			ps = con.prepareStatement(
					"select * from entrada "
					+ "where (? is null or TRUNC(horario) >= ?) "
					+ "and (? is null or TRUNC(horario) <= ?) "
					+ "and (? is null or UPPER(descricao) like CONCAT('%', ? , '%')) "
					+ "order by " + ordem.getOrderBy() );
			
			//data inicial do intervalo (setDate ignora a hora)
			if (filtro.getDe() == null) {
				ps.setNull(1, Types.DATE);
				ps.setNull(2, Types.DATE);
			} else {
				ps.setDate(1, new java.sql.Date(filtro.getDe().getTime()));
				ps.setDate(2, new java.sql.Date(filtro.getDe().getTime()));
			}
			
			//data final do intervalo
			if (filtro.getAte() == null) {
				ps.setNull(3, Types.DATE);
				ps.setNull(4, Types.DATE);
			} else {
				ps.setDate(3, new java.sql.Date(filtro.getAte().getTime()));
				ps.setDate(4, new java.sql.Date(filtro.getAte().getTime()));
			}
			
			//descrição
			if (filtro.getDescricao() == null) {
				ps.setNull(5, Types.VARCHAR);
				ps.setNull(6, Types.VARCHAR);
			} else {
				ps.setString(5, filtro.getDescricao());
				ps.setString(6, filtro.getDescricao().toUpperCase());
			}
			
			ResultSet rs = ps.executeQuery();
			List<Entrada> lista = new ArrayList<Entrada>();
			while (rs.next()) {
				Entrada e = new Entrada();
				e.setId(rs.getInt("id"));
				e.setHorario(rs.getTimestamp("horario"));
				e.setDescricao(rs.getString("descricao"));
				e.setPrioridade(PrioridadeEntrada.fromCode(rs.getString("prioridade")));
				e.setIdUsuario(rs.getInt("id_usuario"));
				lista.add(e);
			}
			return lista;			
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
