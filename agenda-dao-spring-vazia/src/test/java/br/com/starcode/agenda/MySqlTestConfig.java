package br.com.starcode.agenda;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@Configuration
@ComponentScan(basePackages = {"br.com.starcode.agenda.dao"})
public class MySqlTestConfig {
	
	@Bean 
	public DataSource dataSourceMySql() {
		MysqlDataSource ds = new MysqlDataSource();
		ds.setUrl("jdbc:mysql://localhost:3311/agenda");
		ds.setUser("root");
		ds.setPassword("root");
		return ds;
	}
	
	@Bean
	JdbcTemplate getTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	@Bean
	public DataSourceInitializer dataSourceInitializerMySql(
			DataSource dataSource,
			@Value("classpath:mysql/schema.sql") Resource schemaMySql,
			@Value("classpath:mysql/test-data.sql") Resource dataMySql) {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(schemaMySql);
		populator.addScript(dataMySql);
		
	    final DataSourceInitializer initializer = new DataSourceInitializer();
	    initializer.setDataSource(dataSource);
	    initializer.setDatabasePopulator(populator);
	    return initializer;
	}
	
}
