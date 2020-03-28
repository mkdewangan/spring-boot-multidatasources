package com.practise.multidatasources;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;



@Configuration
@EnableJpaRepositories(basePackages = "com.practise.multidatasources.postgres",
        entityManagerFactoryRef = "pgEntityManagerFactory",
        transactionManagerRef= "pgTransactionManager")
public class PostgresDataSourceConfiguration {
	
	
	@Bean
	@ConfigurationProperties("spring.pg.datasource")
	public DataSourceProperties pgDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties("spring.pg.datasource")
	public DataSource pgDataSource() {
		return pgDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

  

    @Bean(name = "pgEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean pgEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(pgDataSource())
                .packages("com.practise.multidatasources.postgres")
                .build();
    }

    @Bean
    public PlatformTransactionManager pgTransactionManager(
            final @Qualifier("pgEntityManagerFactory") LocalContainerEntityManagerFactoryBean pgEntityManagerFactory) {
        return new JpaTransactionManager(pgEntityManagerFactory.getObject());
    }

}
