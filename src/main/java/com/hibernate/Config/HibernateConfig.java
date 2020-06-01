package com.hibernate.Config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.hibernate.model.Student;

@Configuration
@ComponentScan(basePackages = {"com.hibernate"})
@EnableTransactionManagement
public class HibernateConfig {

	private final static String DATABASE_DIALECT = "org.hibernate.dialect.MySQLDialect";

	@Bean
	public DataSource getdatasource()
	{
		BasicDataSource datasource=new BasicDataSource();
		datasource.setDriverClassName("com.mysql.jdbc.Driver");
		datasource.setUrl("jdbc:mysql://localhost:3306/test");
		datasource.setUsername("root");
		datasource.setPassword("password");
		return datasource;
	}
	
	@Bean
	public SessionFactory getSessionFactory(DataSource datsource)
	{
		LocalSessionFactoryBuilder builder=new LocalSessionFactoryBuilder(datsource);
		builder.addProperties(getProperty());		
		builder.addAnnotatedClass(Student.class);
		builder.scanPackages("com.hibernate.model");
		return builder.buildSessionFactory();
	}
	
	@Bean
	public Properties getProperty()
	{
		Properties property=new Properties();
		property.put("hibernate.dialect", DATABASE_DIALECT);
		property.put("hibernate.hbm2ddl.auto", "update");
		return property;
	}
	
	@Bean
	public HibernateTransactionManager getHibernateTransactionmanager(SessionFactory session)
	{
		HibernateTransactionManager transaction=new HibernateTransactionManager(session);
		System.out.println("Transaction Succeeded");
		return transaction;
	}
}
