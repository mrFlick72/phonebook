package it.valeriovaudi.config.integration;

import it.valeriovaudi.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.mail.Mail;
import org.springframework.integration.jms.ChannelPublishingJmsMessageListener;
import org.springframework.integration.jms.JmsMessageDrivenEndpoint;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

/**
 * Created by Valerio on 14/02/2015.
 */

@Configuration
public class CreatePhoneBookDocIntegrationFlowConfiguration {

    @Autowired
    private Environment environment;

 /*   @Autowired
    private ContactRepository contactRepository;*/

/*    @Autowired
    private ConnectionFactory connectionFactory;*/

    @Autowired
    @Qualifier("sendQueue")
    private Destination destination;

    /*
    <int-jms:message-driven-channel-adapter
        destination="sendQueue"
        channel="boockPrintServiceChain"/>

        equivalent Java Config configuration of namespace int-jms:message-driven-channel-adapter
     */
    @Bean
    public DefaultMessageListenerContainer defaultMessageListenerContainer(ConnectionFactory connectionFactory){
        DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
        defaultMessageListenerContainer.setDestination(destination);
        defaultMessageListenerContainer.setConnectionFactory(connectionFactory);

        return defaultMessageListenerContainer;
    }

    @Bean
    public ChannelPublishingJmsMessageListener messageDrivenChannelAdapter4CreatePhoneBookDocIntegrationFlow(){
        ChannelPublishingJmsMessageListener channelPublishingJmsMessageListener = new ChannelPublishingJmsMessageListener();

        channelPublishingJmsMessageListener.setDestinationResolver(new DynamicDestinationResolver());
        channelPublishingJmsMessageListener.setRequestChannelName("boockPrintServiceChain");
        return channelPublishingJmsMessageListener;
    }

    @Bean
    public JmsMessageDrivenEndpoint jmsMessageDrivenEndpoint4CreatePhoneBookDocIntegrationFlow(ConnectionFactory connectionFactory){
        JmsMessageDrivenEndpoint jmsMessageDrivenEndpoint = new JmsMessageDrivenEndpoint(defaultMessageListenerContainer(connectionFactory),messageDrivenChannelAdapter4CreatePhoneBookDocIntegrationFlow());
        return jmsMessageDrivenEndpoint;
    }

/*
    <chain input-channel="boockPrintServiceChain"
           output-channel="boockPrintServiceDocumentTypeRouter">
        <header-enricher>
            <header name="user" expression="@phonBookUserRepository.findByUserName(payload['userName'])"/>
            <header name="contentType" expression="payload['contentType']"/>
        </header-enricher>
        <transformer expression="payload['userName']"/>
        <service-activator expression="@contactRepository.findAllContactByUser(payload).iterator()"/>
        <transformer expression="T(org.apache.commons.collections.IteratorUtils).toList(payload)"/>
    </chain>

    <router input-channel="boockPrintServiceDocumentTypeRouter"
            expression="headers['contentType']">
        <mapping value="pdf" channel="boockPrintServiceDocumentHowPdf"/>
        <mapping value="docx" channel="boockPrintServiceDocumentHowWord"/>
    </router>

    <service-activator input-channel="boockPrintServiceDocumentHowPdf"
                       output-channel="boockPrintServiceSendMailChain"
                       expression="@pdfCreatePhoneBoockDoc.createPhoneBoockDoc(payload)"/>

    <service-activator input-channel="boockPrintServiceDocumentHowWord"
                       output-channel="boockPrintServiceSendMailChain"
                       expression="@wordCreatePhoneBoockDoc.createPhoneBoockDoc(payload)"/>

    <chain input-channel="boockPrintServiceSendMailChain">
        <mail:header-enricher>
            <mail:from value="${mail.admin}"/>
            <mail:to expression="headers['user'].mail"/>
            <mail:subject value="${mail.createphoneBookDocument.subject}"/>
        </mail:header-enricher>
        <transformer ref="mailMessageTrasformer"/>
        <mail:outbound-channel-adapter mail-sender="mailSender" />
    </chain>


    equivalent java-dsl configuration of the Spring Integration pipeline
    in this example i have used the java 8 capability with lambada expressions
    */

    @Bean
    public IntegrationFlow createPhoneBookDocIntegrationFlow(ContactRepository contactRepository) {
        return f -> f.channel("boockPrintServiceChain")
                    .enrichHeaders(headerEnricherSpec -> {
                        headerEnricherSpec.headerExpression("user", "@phonBookUserRepository.findByUserName(payload['userName'])");
                        headerEnricherSpec.headerExpression("contentType", "payload['contentType']");
                    })
                    .transform("payload['userName']")
                    .handle((payload, headers) -> contactRepository.findAllContactByUser((String) payload).iterator())
                    .transform("T(org.apache.commons.collections.IteratorUtils).toList(payload)")
                    .route("headers['contentType']", stringRouterSpec -> {
                        stringRouterSpec
                                .subFlowMapping("pdf", subFlow -> subFlow.handle("pdfCreatePhoneBoockDoc", "createPhoneBoockDoc"))
                                .subFlowMapping("docx", subFlow -> subFlow.handle("wordCreatePhoneBoockDoc", "createPhoneBoockDoc"));
                    })
                    .transform("@mailMessageTrasformer.trasformMessage(headers['contentType'],headers['user'],payload)")
                    .enrichHeaders(Mail.headers()
                            .toExpression("headers['user'].mail")
                            .from(environment.getProperty("mail.admin"))
                            .subject(environment.getProperty("mail.createphoneBookDocument.subject")))
                .handle(Mail.outboundAdapter("localhost"));
    }
}
