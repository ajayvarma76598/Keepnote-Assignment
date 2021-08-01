package com.stackroute.keepnote.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

/*This class will contain the application-context for the application. 
 * Define the following annotations:
 * @Configuration - Annotating a class with the @Configuration indicates that the 
 *                  class can be used by the Spring IoC container as a source of 
 *                  bean definitions
 * @ComponentScan - this annotation is used to search for the Spring components amongst the application
 * @EnableWebMvc - Adding this annotation to an @Configuration class imports the Spring MVC 
 * 				   configuration from WebMvcConfigurationSupport 
 * @EnableTransactionManagement - Enables Spring's annotation-driven transaction management capability.
 *                  
 * @EnableAspectJAutoProxy - This spring aop annotation is used to enable @AspectJ support with Java @Configuration  
 * */

public class ApplicationContextConfig {

	/*
	 * Define the bean for DataSource. In our application, we are using MySQL as the
	 * dataSource. To create the DataSource bean, we need to know: 1. Driver class
	 * name 2. Database URL 3. UserName 4. Password
	 */
	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource=new DriverManagerDataSource();


//		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//		dataSource.setUrl("jdbc:mysql://localhost:3306/wave");
//		dataSource.setUsername("root");
//		dataSource.setPassword("root");
//		return dataSource;
//	}
	
	// Use this configuration while submitting solution in hobbes and CI
	 dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
	 dataSource.setUrl("jdbc:mysql://" + System.getenv("MYSQL_HOST") + ":3306/" +
	 System.getenv("MYSQL_DATABASE")
	  +"?verifyServerCertificate=false&useSSL=false&requireSSL=false");
	  dataSource.setUsername(System.getenv("MYSQL_USER"));
	 dataSource.setPassword(System.getenv("MYSQL_PASSWORD"));
	 return dataSource;
	}
		@Bean
		@Autowired
		public LocalSessionFactoryBean getSessionFactory(DataSource dataSource) {
			LocalSessionFactoryBean sessionFactory=new LocalSessionFactoryBean();
			sessionFactory.setDataSource(dataSource);
			sessionFactory.setPackagesToScan("com.stackroute.keepnote.model");
			Properties properties=new Properties();
			properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

		properties.setProperty("hibernate.hbm2ddl.auto", "update");
			sessionFactory.setHibernateProperties(properties);
			return sessionFactory;
		}
		/*
		 * Define the bean for Transaction Manager. HibernateTransactionManager handles
		 * transaction in Spring. The application that uses single hibernate session
		 * factory for database transaction has good choice to use
		 * HibernateTransactionManager. HibernateTransactionManager can work with plain
		 * JDBC too. HibernateTransactionManager allows bulk update and bulk insert and
		 * ensures data integrity.
		 */
		@Bean
		@Autowired
		public  HibernateTransactionManager getTxnManager(SessionFactory sessionFactory) {
			HibernateTransactionManager txnManager=new HibernateTransactionManager();
			txnManager.setSessionFactory(sessionFactory);
			return txnManager;
		}
	}