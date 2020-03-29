package com.practise.multidatasources;

import java.sql.SQLException;
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
import com.mysql.cj.jdbc.MysqlXADataSource;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
//@EnableJpaRepositories(basePackages = "com.practise.multidatasources.mysql",
//        entityManagerFactoryRef = "mysqlEntityManagerFactory",
//        transactionManagerRef= "mysqlTransactionManager")
@EnableJpaRepositories(basePackages = "com.practise.multidatasources.mysql", entityManagerFactoryRef = "mysqlEntityManagerFactoryXA", transactionManagerRef= "transactionManager")
public class MySqlDataSourceConfiguration {
	
	@Autowired
	private JpaVendorAdapter jpaVendorAdapter;
	
	@Primary
	@Bean
	@ConfigurationProperties("spring.mysql.datasource")
	public DataSourceProperties mysqlDataSourceProperties() {
		return new DataSourceProperties();
	}

//	@Primary
//	@Bean
//	@ConfigurationProperties("spring.mysql.datasource")
//	public DataSource mysqlDataSource() {
//		return mysqlDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
//	}
//
//  
//	@Primary
//    @Bean(name = "mysqlEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(EntityManagerFactoryBuilder builder) {
//        return builder
//                .dataSource(mysqlDataSource())
//                .packages("com.practise.multidatasources.mysql")
//                .build();
//    }

//    @Bean
//    public PlatformTransactionManager mysqlTransactionManager(
//            final @Qualifier("mysqlEntityManagerFactory") LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory) {
//        return new JpaTransactionManager(mysqlEntityManagerFactory.getObject());
//    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////
    
    @Primary
    @Bean(name = "mysqlDataSourceXA", initMethod = "init", destroyMethod = "close")
   	public DataSource mysqlDataSourceXA() {
		
    	MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
		
		mysqlXaDataSource.setUrl(mysqlDataSourceProperties().getUrl());
		try {
			mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mysqlXaDataSource.setPassword(mysqlDataSourceProperties().getPassword());
		mysqlXaDataSource.setUser(mysqlDataSourceProperties().getUsername());
		

		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXaDataSource);
		xaDataSource.setUniqueResourceName("xads1");
		return xaDataSource;

	}

    @Primary
    @Bean(name = "mysqlEntityManagerFactoryXA")
	@DependsOn("transactionManager")
	public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactoryXA() throws Throwable {

		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
		properties.put("javax.persistence.transactionType", "JTA");

		LocalContainerEntityManagerFactoryBean entityManagerfactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerfactory.setJtaDataSource(mysqlDataSourceXA());
		entityManagerfactory.setJpaVendorAdapter(jpaVendorAdapter);
		entityManagerfactory.setPackagesToScan("com.practise.multidatasources.mysql");
		//entityManagerfactory.setPersistenceUnitName("mysqlPersistenceUnit");
		entityManagerfactory.setJpaPropertyMap(properties);
		return entityManagerfactory;
	}
    
    
    
}
