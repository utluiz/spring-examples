package br.com.starcode.agenda;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@ComponentScan(basePackages = {"br.com.starcode.agenda.dao.memory"})
public class MemoryTestConfig {
	
	@Bean 
	@Qualifier("h2") 
	public DataSource dataSourceH2() {
		return new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.H2)
			.addScript("classpath:/h2/schema.sql")
			.addScript("classpath:/h2/test-data.sql")
			.build();
	}
	
}
