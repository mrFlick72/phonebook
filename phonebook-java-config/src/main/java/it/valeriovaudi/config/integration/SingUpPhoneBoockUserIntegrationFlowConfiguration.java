package it.valeriovaudi.config.integration;

import it.valeriovaudi.builder.PhoneBookUserBuilder;
import it.valeriovaudi.factory.SecurityUserFactory;
import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.security.PhoneBookSecurityRole;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by Valerio on 12/04/2015.
 *
     <channel id="singUpPhoneBoockUserMainRequestChannel"/>
     <gateway id="signUpService"
             service-interface="it.valeriovaudi.service.SignUpService"
             default-request-channel="singUpPhoneBoockUserMainRequestChannel"
             default-reply-channel="singUpPhoneBoockUserMainResponseChannel"/>

     <chain id="signUpChain"
         input-channel="singUpPhoneBoockUserMainRequestChannel"
         output-channel="singUpPhoneBoockUserMainResponseChannel">
         <service-activator expression="@phoneBookUserBuilder.buildByPhoneBookUserBuilder(payload)
                                         .buildPassword(payload.password)
                                         .buildSecurityRole(T(it.valeriovaudi.security.PhoneBookSecurityRole).USER)
                                         .buildPhoneBookUser()"  requires-reply="true"/>
         <service-activator expression="@phonBookUserRepository.save(payload)" requires-reply="true"/>
     </chain>

     <publish-subscribe-channel id="singUpPhoneBoockUserMainResponseChannel"/>

     <service-activator id="logInServiceActivator" input-channel="singUpPhoneBoockUserMainResponseChannel">
         <int-groovy:script>
             import org.springframework.security.core.context.SecurityContextHolder
             SecurityContextHolder.getContext().setAuthentication(securityUserFactory.getAutenticatedUser(payload));
         </int-groovy:script>
     </service-activator>
 */
@Configuration
public class SingUpPhoneBoockUserIntegrationFlowConfiguration {

    @Bean
    public DirectChannel singUpPhoneBoockUserMainRequestChannel() {
        return new DirectChannel();
    }

    @Bean
    public SubscribableChannel singUpPhoneBoockUserMainResponseChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public IntegrationFlow signUpChain(PhonBookUserRepository phonBookUserRepository) {
        return flow -> flow.channel("singUpPhoneBoockUserMainRequestChannel")
                            .<PhoneBookUser>handle((payload, headers) -> PhoneBookUserBuilder
                                    .newPhoneBookUserBuilderByPhoneBookUser(payload)
                                    .buildPassword(payload.getPassword())
                                    .buildSecurityRole(PhoneBookSecurityRole.USER)
                                    .buildPhoneBookUser()
                                    , serviceActivatingHandlerGenericEndpointSpec -> serviceActivatingHandlerGenericEndpointSpec.requiresReply(true))
                            .<PhoneBookUser>handle((payload1, headers1) ->
                                            phonBookUserRepository.save(payload1),
                                    serviceActivatingHandlerGenericEndpointSpec1 -> serviceActivatingHandlerGenericEndpointSpec1.requiresReply(true))
                            .channel("singUpPhoneBoockUserMainResponseChannel");

    }

    @Bean
    @ServiceActivator(inputChannel = "singUpPhoneBoockUserMainResponseChannel")
    public MessageHandler logInServiceActivator(SecurityUserFactory<PhoneBookUser> securityUserFactory) {
        return message -> SecurityContextHolder.getContext().setAuthentication(securityUserFactory.getAutenticatedUser((PhoneBookUser) message.getPayload()));
    }
}
