package br.com.starcode.agenda;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@Configuration
@ComponentScan(basePackages = {"br.com.starcode.agenda"})
public class TestConfig {
	
	@Bean 
	@Qualifier("h2") 
	public DataSource dataSourceH2() {
		return new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.H2)
			.addScript("classpath:/h2/schema.sql")
			.addScript("classpath:/h2/test-data.sql")
			.build();
	}
	
	@Bean 
	@Qualifier("mysql") 
	@Primary
	public DataSource dataSourceMySql() {
		MysqlDataSource ds = new MysqlDataSource();
		ds.setUrl("jdbc:mysql://localhost:3311/agenda");
		ds.setUser("root");
		ds.setPassword("root");
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
	
	@Bean 
	@Qualifier("pg") 
	public DataSource dataSourcePostgreSql() throws SQLException {
		PGPoolingDataSource source = new PGPoolingDataSource();
		source.setUrl("jdbc:postgresql://localhost/agenda");
		source.setUser("postgres");
		source.setMaxConnections(10);
//		source.setPassword("testpassword");
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
