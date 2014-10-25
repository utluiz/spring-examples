package br.com.starcode.agenda;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@ComponentScan(basePackages = {
		"br.com.starcode.agenda.dao.postgresqltemplate",
		"br.com.starcode.agenda.dao.postgresqltemplate"})
public class PostgreSqlTestConfig {
	
	@Bean 
	@Qualifier("pg") 
	public DataSource dataSourcePostgreSql() throws SQLException {
		PGPoolingDataSource source = new PGPoolingDataSource();
		source.setUrl("jdbc:postgresql://localhost/agenda");
		source.setUser("postgres");
		source.setPassword("0");
		source.setMaxConnections(10);
		return source;
	}
	
	@Bean
	@Qualifier("pg")
	JdbcTemplate getTemplatePostgreSql(@Qualifier("pg") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	@Bean
	@Qualifier("pg")
	public DataSourceInitializer dataSourceInitializerPostgreSql(
			@Qualifier("pg") final DataSource dataSource,
			@Value("classpath:postgresql/schema.sql") Resource schemaPostgreSql,
			@Value("classpath:postgresql/test-data.sql") Resource dataPostgreSql) {
		
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(schemaPostgreSql);
		populator.addScript(dataPostgreSql);
		//DatabasePopulatorUtils.execute(populator, ds);
		
	    final DataSourceInitializer initializer = new DataSourceInitializer();
	    initializer.setDataSource(dataSource);
	    initializer.setDatabasePopulator(populator);
	    return initializer;
	}
	
}
