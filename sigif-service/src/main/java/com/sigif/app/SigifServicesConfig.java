package com.sigif.app;

import java.util.Properties;

import javax.sql.DataSource;

import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sigif.dao.DaoConfiguration;
import com.sigif.service.ServiceConfiguration;

/**
 * Classe de configuration des services Sigif (config hibernate et Spring).
 *
 */
@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@PropertySource(value = { "classpath:application.properties" }, ignoreResourceNotFound = true)
@PropertySource(value = { "file:${catalina.home}/conf/sigif-formulaires.properties" }, ignoreResourceNotFound = true)
@Import({ DaoConfiguration.class, ServiceConfiguration.class })
public class SigifServicesConfig {

    /**
     * Environnement Spring.
     */
    @Autowired
    protected Environment environment;

    /**
     * Obtient le session factory d'hibernate.
     * 
     * @return le session factory d'hibernate
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.sigif.modele" });
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    /**
     * Obtient la datasource (à partir du jndi configuré dans l'environnement).
     * 
     * @return la datasource hibernate
     */
    @Bean
    public DataSource dataSource() {
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        DataSource dataSource = dsLookup.getDataSource(environment.getRequiredProperty(SigifProprietes.PROP_DB_JNDI));
        return dataSource;
    }

    /**
     * Obtient les propriétés Hibernate.
     * 
     * @return les propriétés Hibernate.
     */
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put(SigifProprietes.PROP_HIBERNATE_DIALECT,
                environment.getRequiredProperty(SigifProprietes.PROP_HIBERNATE_DIALECT));
        properties.put(SigifProprietes.PROP_HIBERNATE_SHOW_SQL,
                environment.getRequiredProperty(SigifProprietes.PROP_HIBERNATE_SHOW_SQL));
        properties.put(SigifProprietes.PROP_HIBERNATE_FORMAT_SQL,
                environment.getRequiredProperty(SigifProprietes.PROP_HIBERNATE_FORMAT_SQL));
        properties.put(SigifProprietes.PROP_HIBERNATE_HBM2DDL_AUTO,
                environment.getRequiredProperty(SigifProprietes.PROP_HIBERNATE_HBM2DDL_AUTO));
        // ALPHA
//        properties.put(SigifProprietes.PROP_SESSION,
//                environment.getRequiredProperty(SigifProprietes.PROP_SESSION));
        return properties;
    }

    /**
     * Obtient le gestionnaire de transaction Hibernate.
     * 
     * @param s
     *            le session factory Hibernate
     * @return le gestionnaire de transaction Hibernate
     */
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }

    /**
     * Obtient l'intercepteur de log (pour logguer automatiquement en debug
     * l'entrée et la sortie de chaque méthode).
     * 
     * @return l'intercepteur de log
     */
    @Bean
    public LoggingInterceptor loggingInterceptor() {
        return new LoggingInterceptor();
    }

    /**
     * Obtient le factoryBean Dozer permettant la conversion des DTO en DAO.
     * 
     * @param mappings
     *            Liste des mappings Dozer
     * @return factoryBean Dozer permettant la conversion des DTO en DAO
     */
    @Bean
    public DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean(@Value("classpath:/dozer/*.xml") Resource[] mappings) {
        DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean = new DozerBeanMapperFactoryBean();
        dozerBeanMapperFactoryBean.setMappingFiles(mappings);
        return dozerBeanMapperFactoryBean;
    }

    /**
     * Obtient le provider de contexte Spring de l'application.
     * 
     * @return le provider de contexte Spring de l'application
     */
    @Bean
    public ApplicationContextProvider applicationContextProvider() {
        return new ApplicationContextProvider();
    }
}
