package com.practise.multidatasources;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
@EnableJpaRepositories(basePackages = "com.practise.multidatasources.mysql",
        entityManagerFactoryRef = "mysqlEntityManagerFactory",
        transactionManagerRef= "mysqlTransactionManager")
public class MySqlDataSourceConfiguration {
	
	
	@Bean
	@Primary
	@ConfigurationProperties("spring.mysql.datasource")
	public DataSourceProperties mysqlDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties("spring.mysql.datasource")
	public DataSource mysqlDataSource() {
		return mysqlDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

  
	@Primary
    @Bean(name = "mysqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(mysqlDataSource())
                .packages("com.practise.multidatasources.mysql")
                .build();
    }

    @Bean
    public PlatformTransactionManager mysqlTransactionManager(
            final @Qualifier("mysqlEntityManagerFactory") LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory) {
        return new JpaTransactionManager(mysqlEntityManagerFactory.getObject());
    }
}
