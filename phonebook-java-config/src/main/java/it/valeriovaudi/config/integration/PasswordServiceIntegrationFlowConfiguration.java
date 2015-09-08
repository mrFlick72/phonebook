package it.valeriovaudi.config.integration;

import it.valeriovaudi.builder.PhoneBookUserBuilder;
import it.valeriovaudi.integration.filter.AcceptableNonceFilter;
import it.valeriovaudi.integration.serviceactivator.InvalidateNonce;
import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.repository.security.NonceRepository;
import it.valeriovaudi.security.nonce.NonceFactoryByUser;
import it.valeriovaudi.support.EmbeddedMailServerStarter;
import it.valeriovaudi.web.model.PhoneBookUser;
import it.valeriovaudi.web.model.security.Nonce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.mail.Mail;

/**
 * Created by Valerio on 12/04/2015.
 *
 *
 */
    /*
    <channel id="createNonceServiceMainRequestChannel"/>
    <channel id="restetPasswordServiceMainRequestChannel"/>

    <!--create nonce pipeline-->
    <publish-subscribe-channel id="createNonceServiceMainResponseChannel"/>

    <chain id="createNonceChain"
            input-channel="createNonceServiceMainRequestChannel"
            output-channel="createNonceServiceMainResponseChannel">
        <service-activator expression="@phonBookUserRepository.findByUserName(payload)" requires-reply="true"/>
        <service-activator expression="@nonceFactoryByUser.getNonce(payload)" requires-reply="true"/>
        <service-activator expression="@nonceRepository.save(payload).nonce" requires-reply="true"/>
    </chain>


    <chain id="createNonceSendMailChain"
            input-channel="createNonceServiceMainResponseChannel">
        <service-activator expression="T(java.lang.String).format('${mail.resetpassword.messageformat}' ,payload)" requires-reply="true"/>
        <mail:header-enricher>
        <mail:from value="${mail.admin}"/>
        <mail:to expression="headers['mail']"/>
        <mail:subject value="${mail.resetpassword.subject}"/>
        </mail:header-enricher>
        <mail:outbound-channel-adapter mail-sender="mailSender"/>
    </chain>

    <!--reset password pipeline-->
    <!--<channel id="restetPasswordServiceMainRequestChannel"/>-->

    <chain id="resetPasswordChain"
            input-channel="restetPasswordServiceMainRequestChannel"
            output-channel="singUpPhoneBoockUserMainResponseChannel">
        <!--find the nonce in the repository-->
        <service-activator  expression="@nonceRepository.findByNonce(payload)" requires-reply="true"/>
        <!--the router if nonce is acceptable continue in the pipeline, otherwise  discards the message-->
        <filter ref="acceptableNonceFilter" throw-exception-on-rejection="true"/>

        <service-activator ref="invalidateNonce"  requires-reply="true"/>
        <service-activator expression="@phonBookUserRepository.findByUserName(payload.userName)" requires-reply="true"/>
        <service-activator expression="T(it.valeriovaudi.builder.PhoneBookUserBuilder).newPhoneBookUserBuilderByPhoneBookUser(payload).buildPassword(headers['password']).buildPhoneBookUser()" requires-reply="true"/>
        <service-activator expression="@phonBookUserRepository.save(payload)" requires-reply="true"/>
    </chain>*/


@Configuration
public class PasswordServiceIntegrationFlowConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public PublishSubscribeChannel createNonceServiceMainResponseChannel(){
        return new PublishSubscribeChannel();
    }

    @Bean
    public DirectChannel restetPasswordServiceMainRequestChannel(){
        return new DirectChannel();
    }

    @Bean
    public DirectChannel createNonceServiceMainRequestChannel(){
        return new DirectChannel();
    }

    /*
        <chain id="createNonceChain"
                input-channel="createNonceServiceMainRequestChannel"
                output-channel="createNonceServiceMainResponseChannel">
            <service-activator expression="@phonBookUserRepository.findByUserName(payload)" requires-reply="true"/>
            <service-activator expression="@nonceFactoryByUser.getNonce(payload)" requires-reply="true"/>
            <service-activator expression="@nonceRepository.save(payload).nonce" requires-reply="true"/>
        </chain>
    */
    @Bean
    public IntegrationFlow createNonceChain(PhonBookUserRepository phonBookUserRepository,
                                            NonceFactoryByUser nonceFactoryByUser,
                                            NonceRepository nonceRepository){
        return flow -> flow.channel("createNonceServiceMainRequestChannel")
                .<String>handle((payload, headers) -> phonBookUserRepository.findByUserName(payload),serviceActivatingHandlerGenericEndpointSpec -> serviceActivatingHandlerGenericEndpointSpec.requiresReply(true))
                .<PhoneBookUser>handle((payload1, headers1) -> nonceFactoryByUser.getNonce(payload1),serviceActivatingHandlerGenericEndpointSpec1 -> serviceActivatingHandlerGenericEndpointSpec1.requiresReply(true))
                .<Nonce>handle((payload2, headers2) -> nonceRepository.save(payload2).getNonce(), serviceActivatingHandlerGenericEndpointSpec2 -> serviceActivatingHandlerGenericEndpointSpec2.requiresReply(true))
                .channel("createNonceServiceMainResponseChannel");
    }

    /*
        <chain id="createNonceSendMailChain"
                input-channel="createNonceServiceMainResponseChannel">
            <service-activator expression="T(java.lang.String).format('${mail.resetpassword.messageformat}' ,payload)" requires-reply="true"/>
            <mail:header-enricher>
                <mail:from value="${mail.admin}"/>
                <mail:to expression="headers['mail']"/>
                <mail:subject value="${mail.resetpassword.subject}"/>
            </mail:header-enricher>
            <mail:outbound-channel-adapter mail-sender="mailSender"/>
        </chain>
    */
    @Bean
    public IntegrationFlow createNonceSendMailChain(){
        return folw -> folw.channel("createNonceServiceMainResponseChannel")
                .<String>handle((payload, headers) -> String.format(environment.getProperty("mail.resetpassword.messageformat"), payload), serviceActivatingHandlerGenericEndpointSpec -> serviceActivatingHandlerGenericEndpointSpec.requiresReply(true))
                .enrichHeaders(Mail.headers().subject(environment.getProperty("mail.resetpassword.subject"))
                        .from(environment.getProperty("mail.admin"))
                        .toExpression("headers['mail']"))
                .handle(Mail.outboundAdapter(EmbeddedMailServerStarter.LOCAL_HOST_IP)
                        .port(EmbeddedMailServerStarter.SMTP_PORT)
                        .protocol("smtp")
                        .credentials(EmbeddedMailServerStarter.ADMIN_USER, EmbeddedMailServerStarter.ADMIN_PASSWORD));
    }

   /* <chain id="resetPasswordChain"
            input-channel="restetPasswordServiceMainRequestChannel"
            output-channel="singUpPhoneBoockUserMainResponseChannel">
        <!--find the nonce in the repository-->
        <service-activator  expression="@nonceRepository.findByNonce(payload)" requires-reply="true"/>
        <!--the router if nonce is acceptable continue in the pipeline, otherwise  discards the message-->
        <filter ref="acceptableNonceFilter" throw-exception-on-rejection="true"/>

        <service-activator ref="invalidateNonce"  requires-reply="true"/>
        <service-activator expression="@phonBookUserRepository.findByUserName(payload.userName)" requires-reply="true"/>
        <service-activator expression="T(it.valeriovaudi.builder.PhoneBookUserBuilder).newPhoneBookUserBuilderByPhoneBookUser(payload).buildPassword(headers['password']).buildPhoneBookUser()" requires-reply="true"/>
        <service-activator expression="@phonBookUserRepository.save(payload)" requires-reply="true"/>
    </chain>*/

    @Bean
    public IntegrationFlow resetPasswordChain(NonceRepository nonceRepository,
                                              AcceptableNonceFilter acceptableNonceFilter,
                                              InvalidateNonce invalidateNonce,
                                              PhonBookUserRepository phonBookUserRepository){
        return flow -> flow.channel("restetPasswordServiceMainRequestChannel")
                .<String>handle((payload, headers) -> nonceRepository.findByNonce(payload), serviceActivatingHandlerGenericEndpointSpec -> serviceActivatingHandlerGenericEndpointSpec.requiresReply(true))
                .<Nonce>filter(source -> acceptableNonceFilter.accept(source), filterEndpointSpec -> filterEndpointSpec.throwExceptionOnRejection(true))
                .<Nonce>handle((payload1, headers1) -> invalidateNonce.invalidateNonce(payload1), serviceActivatingHandlerGenericEndpointSpec1 -> serviceActivatingHandlerGenericEndpointSpec1.requiresReply(true))
                .<Nonce>handle((payload2, headers2) -> phonBookUserRepository.findByUserName(payload2.getUserName()),serviceActivatingHandlerGenericEndpointSpec2 -> serviceActivatingHandlerGenericEndpointSpec2.requiresReply(true))
                .<PhoneBookUser>handle((payload3, headers3) -> PhoneBookUserBuilder.newPhoneBookUserBuilderByPhoneBookUser(payload3)
                                                                                    .buildFirstName(String.valueOf(headers3.get("password")))
                                                                                    .buildPhoneBookUser(),serviceActivatingHandlerGenericEndpointSpec3 -> serviceActivatingHandlerGenericEndpointSpec3.requiresReply(true))
                .<PhoneBookUser>handle((payload4, headers4) -> phonBookUserRepository.save(payload4),serviceActivatingHandlerGenericEndpointSpec4 -> serviceActivatingHandlerGenericEndpointSpec4.requiresReply(true))
                .channel("singUpPhoneBoockUserMainResponseChannel");
    }
}
