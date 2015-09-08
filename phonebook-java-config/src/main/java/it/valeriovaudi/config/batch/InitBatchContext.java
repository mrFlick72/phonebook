package it.valeriovaudi.config.batch;

import it.valeriovaudi.batch.ContactFieldSetMapper;
import it.valeriovaudi.batch.PhoneBookUserFieldSetMapper;
import it.valeriovaudi.batch.UserItemProcessor;
import it.valeriovaudi.factory.SecurityUserFactory;
import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.web.model.Contact;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManagerFactory;

/**
 * Created by Valerio on 30/03/2015.
 *
 *
     <batch:job id="init">
         <batch:step id="userInit" next="contactInit">
             <batch:tasklet>
                 <batch:chunk reader="userReader"
                             processor="userPostProcessor"
                             writer="writer"
                             commit-interval="10"/>
             </batch:tasklet>
         </batch:step>

         <batch:step id="contactInit">
             <batch:tasklet>
                 <batch:chunk reader="contactReader"
                             writer="writer"
                             commit-interval="10"/>
             </batch:tasklet>
         </batch:step>
     </batch:job>


     <bean id="userReader"
         class="org.springframework.batch.item.file.FlatFileItemReader"
         p:resource="classpath:batch/user.csv">
         <property name="lineMapper">
             <bean class="org.springframework.batch.item.file.mapping.DefaultLineMapper">
             <property  name="lineTokenizer">
             <bean  class="org.springframework.batch.item.file.transform.DelimitedLineTokenizer"
             p:delimiter=","
             p:names="userName, password, firstName, lastName, mail, securityRole"/>
             </property>
             <property name="fieldSetMapper">
             <bean id="bookUserFieldSetMapper"
             class="it.valeriovaudi.batch.PhoneBookUserFieldSetMapper"/>
             </property>
             </bean>
         </property>
     </bean>

     <bean id="contactReader"
         class="org.springframework.batch.item.file.FlatFileItemReader"
         p:resource="classpath:batch/contacts.csv">
         <property name="lineMapper">
            <bean class="org.springframework.batch.item.file.mapping.DefaultLineMapper">
             <property  name="lineTokenizer">
                 <bean  class="org.springframework.batch.item.file.transform.DelimitedLineTokenizer"
                         p:delimiter=","
                         p:names="firstName, lastName, telephoneNumber, birth, phoneBookUser"/>
                 </property>
                 <property name="fieldSetMapper">
                 <bean id="bookUserFieldSetMapper"
                         class="it.valeriovaudi.batch.ContactFieldSetMapper"
                         p:datePattern="yyyy-MM-dd"/>
             </property>
            </bean>
         </property>
     </bean>

     <bean id="userPostProcessor"
         class="it.valeriovaudi.batch.UserItemProcessor"
         p:phonBookUserSecurityUserFactory-ref="securityUserFactory"/>

     <bean id="writer"
         class="org.springframework.batch.item.database.JpaItemWriter"
         p:entityManagerFactory-ref="entityManagerFactory"/>

 */
@Configuration
public class InitBatchContext {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private SecurityUserFactory securityUserFactory;

    @Autowired
    private PhonBookUserRepository phonBookUserRepository;

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

//     Job definition
    @Bean
    public Job init(){
        return jobs.get("init")
                .start(userInit())
                .next(contactInit())
            .build();
    }


/*     <batch:step id="userInit" next="contactInit">
         <batch:tasklet>
             <batch:chunk reader="userReader"
                     processor="userPostProcessor"
                     writer="writer"
                     commit-interval="10"/>
         </batch:tasklet>
     </batch:step>*/

    @Bean
    public Step userInit(){
        return steps.get("userInit")
                    .chunk(10)
                    .reader(userReader())
                    .processor(userPostProcessor())
                    .writer(writer())
                .build();
    }

    @Bean
    public Step contactInit() {
        return steps.get("contactInit")
                    .chunk(10)
                    .reader(contactReader())
                    .writer(writer())
                .build();
    }

//     Item reader, wirter and processor configuration
    @Bean
    public FlatFileItemReader userReader(){
        FlatFileItemReader userReader = new FlatFileItemReader();
        userReader.setResource(new ClassPathResource("batch/user.csv"));
        DefaultLineMapper<PhoneBookUser> userDefaultLineMapper = new DefaultLineMapper<>();
        userDefaultLineMapper.setFieldSetMapper(new PhoneBookUserFieldSetMapper());
        userDefaultLineMapper.setLineTokenizer(new DelimitedLineTokenizer());
        userDefaultLineMapper.afterPropertiesSet();
        userReader.setLineMapper(userDefaultLineMapper);

        return userReader;
    }


    @Bean
    public FlatFileItemReader contactReader(){
        FlatFileItemReader contactReader = new FlatFileItemReader();
        contactReader.setResource(new ClassPathResource("batch/contacts.csv"));
        DefaultLineMapper<Contact> contactDefaultLineMapper = new DefaultLineMapper<>();
        contactDefaultLineMapper.setFieldSetMapper(contactFieldSetMapper());
        contactDefaultLineMapper.setLineTokenizer(new DelimitedLineTokenizer());
        contactDefaultLineMapper.afterPropertiesSet();
        contactReader.setLineMapper(contactDefaultLineMapper);
        return contactReader;
    }

    @Bean
    public JpaItemWriter writer(){
        JpaItemWriter jpaItemWriter = new JpaItemWriter();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

    @Bean
    public ItemProcessor<PhoneBookUser,PhoneBookUser> userPostProcessor(){
        UserItemProcessor userItemProcessor = new UserItemProcessor();
        userItemProcessor.setPhonBookUserSecurityUserFactory(securityUserFactory);
        return userItemProcessor;
    }

    @Bean
    public ContactFieldSetMapper contactFieldSetMapper(){
        ContactFieldSetMapper contactFieldSetMapper = new ContactFieldSetMapper();
        contactFieldSetMapper.setDatePattern("yyyy-MM-dd");
        contactFieldSetMapper.setPhonBookUserRepository(phonBookUserRepository);
        return contactFieldSetMapper;
    }

}
