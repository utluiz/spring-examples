package br.com.starcode.agenda;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AgendaConfig {

	public static void main(String[] args) {
		SpringApplication.run(AgendaConfig.class, args);
	}
	
	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource h2DataSource(){
	    return DataSourceBuilder
	            .create()
	            .type(org.h2.jdbcx.JdbcDataSource.class)
	            .build();
	}
	
}
