package it.valeriovaudi.config.appconfig;

import it.valeriovaudi.support.DefaultUserStarterSupport;
import it.valeriovaudi.support.EmbeddedMailServerStarter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Created by Valerio on 29/03/2015.
 *
 *     <bean id="mailSender"
             class="org.springframework.mail.javamail.JavaMailSenderImpl"
             p:host="#{T(it.valeriovaudi.support.EmbeddedMailServerStarter).LOCAL_HOST_IP}"
             p:port="#{T(it.valeriovaudi.support.EmbeddedMailServerStarter).SMTP_PORT}"
             p:username="#{T(it.valeriovaudi.support.EmbeddedMailServerStarter).ADMIN_USER}"
             p:password="#{T(it.valeriovaudi.support.EmbeddedMailServerStarter).ADMIN_PASSWORD}"/>

        <bean id="embeddedMailServerStarter"
             class="it.valeriovaudi.support.EmbeddedMailServerStarter"/>

        <bean id="starterSupport"
            class="it.valeriovaudi.support.DefaultUserStarterSupport"/>
 */
@Configuration
public class SupportContext {

    @Bean
    public JavaMailSenderImpl mailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(EmbeddedMailServerStarter.LOCAL_HOST_IP);
        javaMailSender.setPort(EmbeddedMailServerStarter.SMTP_PORT);
        javaMailSender.setUsername(EmbeddedMailServerStarter.ADMIN_USER);
        javaMailSender.setPassword(EmbeddedMailServerStarter.ADMIN_PASSWORD);

        return javaMailSender;
    }

    @Bean
    public EmbeddedMailServerStarter embeddedMailServerStarter(){
        return new EmbeddedMailServerStarter();
    }

    @Bean
    public DefaultUserStarterSupport defaultUserStarterSupport(JobLauncher jobLauncher,Job job){
        DefaultUserStarterSupport defaultUserStarterSupport = new DefaultUserStarterSupport();
        defaultUserStarterSupport.setJobLauncher(jobLauncher);
        defaultUserStarterSupport.setJob(job);
        return defaultUserStarterSupport;
    }

}
