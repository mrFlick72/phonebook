package it.valeriovaudi.config.appconfig;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

/**
 * Created by Valerio on 29/03/2015.
 *
 *     <bean id="activeMQConnectionFactory"
             class="org.apache.activemq.spring.ActiveMQConnectionFactory"
             p:brokerURL="vm://localhost"/>

         <bean id="connectionFactory"
             class="org.springframework.jms.connection.CachingConnectionFactory"
             p:targetConnectionFactory-ref="activeMQConnectionFactory"
             p:sessionCacheSize="10"/>

         <bean id="jmsTemplate"
             class="org.springframework.jms.core.JmsTemplate"
             p:connectionFactory-ref="connectionFactory"/>


         <bean id="sendQueue" class="org.apache.activemq.command.ActiveMQQueue">
             <constructor-arg value="sendDestination"/>
         </bean>

        <bean id="reciveQueue" class="org.apache.activemq.command.ActiveMQQueue">
            <constructor-arg value="reciveDestination"/>
        </bean>
 */
@Configuration
public class MessaggingContext {

    private static final String ACTIVE_MQ_BROKER_URL                = "vm:localhost";
    private static final String ACTIVE_MQ_SENDER_DESTINATION_NAME   = "sendDestination";
    private static final String ACTIVE_MQ_RECIVER_DESTINATION_NAME  = "reciveDestination";


    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory(){
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(ACTIVE_MQ_BROKER_URL);
        return activeMQConnectionFactory;
    }

    @Bean
    public CachingConnectionFactory connectionFactory(ConnectionFactory connectionFactory){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setSessionCacheSize(10);
        cachingConnectionFactory.setTargetConnectionFactory(connectionFactory);
        return cachingConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(CachingConnectionFactory connectionFactory){
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }

    @Bean
    public ActiveMQQueue sendQueue(){
        return new ActiveMQQueue(ACTIVE_MQ_SENDER_DESTINATION_NAME);
    }

    @Bean
    public ActiveMQQueue reciveQueue(){
        return new ActiveMQQueue(ACTIVE_MQ_RECIVER_DESTINATION_NAME);
    }
}
