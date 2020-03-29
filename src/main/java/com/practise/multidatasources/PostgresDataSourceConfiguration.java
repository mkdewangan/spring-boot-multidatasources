package com.practise.multidatasources;

import java.util.HashMap;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.xa.PGXADataSource;

@Configuration
//@EnableJpaRepositories(basePackages = "com.practise.multidatasources.postgres", entityManagerFactoryRef = "pgEntityManagerFactory", transactionManagerRef = "pgTransactionManager")
@EnableJpaRepositories(basePackages = "com.practise.multidatasources.postgres", entityManagerFactoryRef = "pgEntityManagerFactoryXA", transactionManagerRef = "transactionManager")
public class PostgresDataSourceConfiguration {
	
	@Autowired
	private JpaVendorAdapter jpaVendorAdapter;

	@Bean
	@ConfigurationProperties("spring.pg.datasource")
	public DataSourceProperties pgDataSourceProperties() {
		return new DataSourceProperties();
	}

//	@Bean
//	@ConfigurationProperties("spring.pg.datasource")
//	public DataSource pgDataSource() {
//		return pgDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
//	}
//
//	@Bean(name = "pgEntityManagerFactory")
//	public LocalContainerEntityManagerFactoryBean pgEntityManagerFactory(EntityManagerFactoryBuilder builder) {
//		return builder.dataSource(pgDataSource()).packages("com.practise.multidatasources.postgres").build();
//	}
//
//	@Bean
//	public PlatformTransactionManager pgTransactionManager(
//			final @Qualifier("pgEntityManagerFactory") LocalContainerEntityManagerFactoryBean pgEntityManagerFactory) {
//		return new JpaTransactionManager(pgEntityManagerFactory.getObject());
//	}

////////////////////////////////////////////////////////////////////////////////////////////


	@Bean(name = "pgDataSourceXA", initMethod = "init", destroyMethod = "close")
	public DataSource pgDataSourceXA() {

		PGXADataSource pgXADataSource = new PGXADataSource();

		pgXADataSource.setUrl(pgDataSourceProperties().getUrl());
		
		pgXADataSource.setPassword(pgDataSourceProperties().getPassword());
		pgXADataSource.setUser(pgDataSourceProperties().getUsername());

		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(pgXADataSource);
		xaDataSource.setUniqueResourceName("xads2");
		return xaDataSource;

	}

	
	@Bean(name = "pgEntityManagerFactoryXA")
	@DependsOn("transactionManager")
	public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactoryXA() throws Throwable {

		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
		properties.put("javax.persistence.transactionType", "JTA");

		LocalContainerEntityManagerFactoryBean entityManagerfactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerfactory.setJtaDataSource(pgDataSourceXA());
		entityManagerfactory.setJpaVendorAdapter(jpaVendorAdapter);
		entityManagerfactory.setPackagesToScan("com.practise.multidatasources.postgres");
		//entityManagerfactory.setPersistenceUnitName("pgPersistenceUnit");
		entityManagerfactory.setJpaPropertyMap(properties);
		return entityManagerfactory;
	}

}
