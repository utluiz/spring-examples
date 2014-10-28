package br.com.starcode.agenda;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@Configuration
@ComponentScan(basePackages = {"br.com.starcode.agenda"})
public class AppConfig {

	@Bean 
	DataSource dataSourceMySql() {
		MysqlDataSource ds = new MysqlDataSource();
		ds.setUrl("jdbc:mysql://localhost:3311/agenda");
		ds.setPassword("root");
		ds.setUser("root");
		return ds;
	}
	
	@Bean
	JdbcTemplate getTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}
