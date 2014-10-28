package br.com.starcode.agenda.dao;

import java.sql.Connection;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({EntradaDaoTest.class, UsuarioDaoTest.class})
public class TestSuite {
	
	static DataSource dataSource;
	
	public static DataSource getDataSource() {
		
		if (dataSource == null) {
		
			try {
				
				Class.forName("org.h2.Driver");
	
				JdbcDataSource ds = new JdbcDataSource();
				ds.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
				ds.setUser("sa");
				ds.setPassword("sa");
				
				//Connection c = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "sa");
				Connection c = ds.getConnection();
				
				c.prepareStatement(
					"create table usuario (	"
					+ "id int auto_increment primary key,"
					+ "nome_usuario varchar(70) unique not null,"
					+ "senha varchar(20) not null,"
					+ "nome varchar(70) not null,"
					+ "ultimo_acesso datetime) ").execute();
				
				c.prepareStatement(
						"insert into usuario (nome_usuario, senha, nome) "
						+ "values ('luiz', '123', 'Luiz Ricardo')").execute();
				
				c.prepareStatement(
					"create table entrada ("
					+ "id int auto_increment primary key,"
					+ "horario datetime not null,"
					+ "descricao varchar(250) not null,"
					+ "prioridade char(1) not null,"
					+ "id_usuario int not null,"
					+ "constraint fk_entrada_usuario foreign key (id_usuario) references usuario(id) )").execute();
				
				c.prepareStatement(
						"insert into entrada (horario, descricao, prioridade, id_usuario) "
						+ "values ('2014-10-14 08:00:00', 'Curso Spring MVC', 'I', 1)").execute();
				c.prepareStatement(
						"insert into entrada (horario, descricao, prioridade, id_usuario) "
						+ "values ('2014-10-14 13:00:00', 'Churrasco', 'P', 1)").execute();
				c.prepareStatement(
						"insert into entrada (horario, descricao, prioridade, id_usuario) "
						+ "values ('2014-10-21 08:00:00', 'Continuação do Curso Spring MVC', 'P', 1)").execute();
				
				dataSource = ds;
				
			} catch (Throwable e) {
				e.printStackTrace();
			}
			
		}
		
		return dataSource;
		
	}
	
	@BeforeClass
	public static void setup() throws Exception {
		
		getDataSource();
		
	}

}

