package com.buswe.base.config.annotation;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.buswe.base.dao.springdata.BaseRepositoryFactoryBean;

@Configuration
@EnableJpaRepositories(basePackages={"com.buswe"}, entityManagerFactoryRef="entityManagerFactory", transactionManagerRef="jpaTransaction", repositoryFactoryBeanClass=BaseRepositoryFactoryBean.class, excludeFilters={@org.springframework.context.annotation.ComponentScan.Filter({org.springframework.stereotype.Controller.class})})
public class DaoConfig
{
  @Autowired
  private Environment env;
  
  @Bean (name="stat-filter")
 public  StatFilter statFilter()
 {
	  StatFilter statFilter=new StatFilter();
	  statFilter.setLogSlowSql(true);
	  statFilter.setSlowSqlMillis(10000);
	  return statFilter;
 }
  @Bean (name="log4j-filter")
  public Log4jFilter log4jFilter()
  {
	  Log4jFilter log4jFilter=new Log4jFilter();
	  log4jFilter.setStatementExecutableSqlLogEnable(true);
	  return log4jFilter;
  }
  @Bean(name={"dataSource"}, initMethod="init", destroyMethod="close")
  public DataSource dataSource()
  {
    DruidDataSource ds = new DruidDataSource();
    ds.setUsername(this.env.getProperty("database.username"));
    ds.setPassword(this.env.getProperty("database.password"));
    ds.setUrl(this.env.getProperty("database.url"));
    ds.setInitialSize(1);
    ds.setMaxActive(20);
    try
    {
      ds.setFilters("stat,log4j");
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return ds;
  }
  
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(JpaVendorAdapter jpaVendorAdapter,DataSource dataSource)
  {
    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    Properties jpaProperties = new Properties();
    jpaProperties.setProperty("hibernate.dialect", this.env.getProperty("hibernate.dialect"));
    jpaProperties.setProperty("hibernate.show_sql", this.env.getProperty("hibernate.show_sql"));
    jpaProperties.setProperty("hibernate.format_sql", this.env.getProperty("hibernate.format_sql"));
    jpaProperties.setProperty("hibernate.generate_statistics", this.env.getProperty("hibernate.generate_statistics"));
    jpaProperties.setProperty("hibernate.use_sql_comments", this.env.getProperty("hibernate.use_sql_comments"));
    jpaProperties.setProperty("hibernate.jdbc.fetch_size", this.env.getProperty("hibernate.jdbc.fetch_size"));
    jpaProperties.setProperty("hibernate.jdbc.batch_size", this.env.getProperty("hibernate.jdbc.batch_size"));
    if (this.env.getProperty("hibernate.hbm2ddl.auto") != null) {
      jpaProperties.setProperty("hibernate.hbm2ddl.auto", this.env.getProperty("hibernate.hbm2ddl.auto"));
    }
    jpaProperties.setProperty("hibernate.cache.use_structured_entries", this.env.getProperty("hibernate.cache.use_structured_entries"));
    factory.setJpaProperties(jpaProperties);
    factory.setPackagesToScan(new String[] { "com.buswe" });
    factory.setJpaVendorAdapter(jpaVendorAdapter);
    factory.setDataSource(dataSource);
    factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
    return factory;
  }
  
  @Bean
  public JpaVendorAdapter jpaVendorAdapter()
  {
    HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
    return jpaVendorAdapter;
  }
  
  @Bean
  public PersistenceExceptionTranslator persistenceExceptionTranslator()
  {
    return new HibernateExceptionTranslator();
  }
  
  @Bean
  public JpaTransactionManager jpaTransaction(LocalContainerEntityManagerFactoryBean entityManagerFactory )
  {
    JpaTransactionManager manager = new JpaTransactionManager();
    manager.setEntityManagerFactory(entityManagerFactory.getObject());
    return manager;
  }
}
