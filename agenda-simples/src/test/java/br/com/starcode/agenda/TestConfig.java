package br.com.starcode.agenda;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
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
	
	@Value("classpath:mysql/schema.sql")
	private Resource schema;
	
	@Value("classpath:mysql/test-data.sql")
	private Resource data;
	
	@Bean
	@Qualifier("mysql")
	public DataSourceInitializer dataSourceInitializerMySql(@Qualifier("mysql") final DataSource dataSource) {
		
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(schema);
		populator.addScript(data);
		//DatabasePopulatorUtils.execute(populator, ds);
		
	    final DataSourceInitializer initializer = new DataSourceInitializer();
	    initializer.setDataSource(dataSource);
	    initializer.setDatabasePopulator(populator);
	    return initializer;
	}
	
}
