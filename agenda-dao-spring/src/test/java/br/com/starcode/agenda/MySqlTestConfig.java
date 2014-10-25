package br.com.starcode.agenda;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@Configuration
@ComponentScan(basePackages = {
		"br.com.starcode.agenda.dao.mysqltemplate",
		"br.com.starcode.agenda.dao.mysql"})
public class MySqlTestConfig {
	
	@Bean 
	@Qualifier("mysql") 
	@Primary
	public DataSource dataSourceMySql() {
		MysqlDataSource ds = new MysqlDataSource();
		ds.setUrl("jdbc:mysql://localhost/agenda");
		ds.setUser("root");
		ds.setPassword("");
		return ds;
	}
	
	@Bean
	@Qualifier("mysql")
	@Primary
	JdbcTemplate getTemplate(@Qualifier("mysql") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	@Bean
	@Qualifier("mysql")
	public DataSourceInitializer dataSourceInitializerMySql(
			@Qualifier("mysql") final DataSource dataSource,
			@Value("classpath:mysql/schema.sql") Resource schemaMySql,
			@Value("classpath:mysql/test-data.sql") Resource dataMySql) {
		
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(schemaMySql);
		populator.addScript(dataMySql);
		//DatabasePopulatorUtils.execute(populator, ds);
		
	    final DataSourceInitializer initializer = new DataSourceInitializer();
	    initializer.setDataSource(dataSource);
	    initializer.setDatabasePopulator(populator);
	    return initializer;
	}
	
}
