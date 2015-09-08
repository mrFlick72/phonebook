package it.valeriovaudi.service;

import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * Created by Valerio on 17/10/2014.
 */

/*
<gateway id="resetPasswordService"
        service-interface="it.valeriovaudi.service.PasswordService">
    <method name="createNonce"
            request-channel="createNonceServiceMainRequestChannel"
            reply-channel="createNonceServiceMainResponseChannel"
            payload-expression="#args[0]" >
    <header name="mail" expression="#args[1]"/>
    </method>

    <method name="resetPassword"
            request-channel="restetPasswordServiceMainRequestChannel"
            reply-channel="singUpPhoneBoockUserMainResponseChannel"
            payload-expression="#args[1]">
    <header name="password" expression="#args[0]"/>
    </method>
</gateway>
*/
@MessagingGateway
public interface PasswordService {

    @Gateway(requestChannel = "createNonceServiceMainRequestChannel", replyChannel = "createNonceServiceMainResponseChannel")
    String createNonce(@Payload String userName, @Header(value = "mail") String mail);

    @Gateway(requestChannel = "restetPasswordServiceMainRequestChannel", replyChannel = "singUpPhoneBoockUserMainResponseChannel")
    PhoneBookUser resetPassword(@Header(value = "password") String resetPassword, @Payload String nonce);
}