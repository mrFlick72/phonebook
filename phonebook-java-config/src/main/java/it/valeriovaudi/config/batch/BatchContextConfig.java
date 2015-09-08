package it.valeriovaudi.config.batch;

/**
 * Created by Valerio on 29/03/2015.
 *
 *     <batch:job-repository data-source="initBatchDataSource"
         transaction-manager="transactionManager"/>

         <jdbc:embedded-database id="initBatchDataSource" type="H2">
             <jdbc:script location="classpath:org/springframework/batch/core/schema-drop-h2.sql" />
             <jdbc:script location="classpath:org/springframework/batch/core/schema-h2.sql" />
         </jdbc:embedded-database>

         <bean id="simpleJobLauncher"
             class="org.springframework.batch.core.launch.support.SimpleJobLauncher"
             p:jobRepository-ref="jobRepository"/>

 */

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
@EnableBatchProcessing
public class BatchContextConfig {

    @Autowired
    @Qualifier("transactionManager")
    private PlatformTransactionManager transactionManager;

    @Bean
    public PhoneBookBatchConfigurer phoneBookBatchConfigurer(JobRepository jobRepository){
        PhoneBookBatchConfigurer phoneBookBatchConfigurer = new PhoneBookBatchConfigurer();
        phoneBookBatchConfigurer.setInitBatchDataSource(initBatchDataSource());
        phoneBookBatchConfigurer.setTransactionManager(transactionManager);
        phoneBookBatchConfigurer.setJobLauncher(jobLauncher(jobRepository));
        return phoneBookBatchConfigurer;
    }

    @Bean
    public JobBuilderFactory jobs(JobRepository jobRepository){
        return new JobBuilderFactory(jobRepository);
    }

    @Bean
    public StepBuilderFactory steps(JobRepository jobRepository){
        return new StepBuilderFactory(jobRepository,transactionManager);
    }

    @Bean
    public DataSource initBatchDataSource(){
        return new EmbeddedDatabaseBuilder()
                        .addScripts("classpath:org/springframework/batch/core/schema-drop-h2.sql",
                                    "classpath:org/springframework/batch/core/schema-h2.sql")
                        .setType(EmbeddedDatabaseType.H2)
                    .build();
    }

    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository){
        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(jobRepository);
        return simpleJobLauncher;
    }
}
