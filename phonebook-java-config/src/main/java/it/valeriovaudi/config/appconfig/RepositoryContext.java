package it.valeriovaudi.config.appconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.support.PersistenceExceptionTranslationInterceptor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created by Valerio on 28/03/2015.
 *
 <tx:annotation-driven />
 <jpa:repositories base-package="it.valeriovaudi.repository"
                     entity-manager-factory-ref="entityManagerFactory"
                     transaction-manager-ref="transactionManager"/>

 <bean id="transactionManager"
     class="org.springframework.orm.jpa.JpaTransactionManager"
     p:entityManagerFactory-ref="entityManagerFactory"/>

 <jdbc:embedded-database id="dataSource" type="H2">
    <jdbc:script location="classpath:sql/create-db.sql" />
 </jdbc:embedded-database>

 <bean id="entityManagerFactory"
     class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean"
     p:dataSource-ref="dataSource"
     p:jpaVendorAdapter="#{new org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter()}">
</bean>

 <bean id="exceptionTranslationInterceptor"
    class="org.springframework.dao.support.PersistenceExceptionTranslationInterceptor">
 </bean>
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "it.valeriovaudi.repository")
public class RepositoryContext {

    @Bean
    public DataSource dataSource(){
        return new EmbeddedDatabaseBuilder()
                .addScripts("classpath:sql/create-db.sql")
                .setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public PersistenceExceptionTranslationInterceptor exceptionTranslationInterceptor(){
        return new PersistenceExceptionTranslationInterceptor();
    }
}
