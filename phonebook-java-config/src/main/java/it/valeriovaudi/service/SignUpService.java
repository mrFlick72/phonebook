package it.valeriovaudi.service;

import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

/**
 * Created by Valerio on 11/10/2014.
 */
@MessagingGateway(name = "signUpService")
public interface SignUpService {

    @Gateway(requestChannel = "singUpPhoneBoockUserMainRequestChannel",
             replyChannel = "singUpPhoneBoockUserMainResponseChannel")
    PhoneBookUser phoneBookUserSingIn(PhoneBookUser phoneBookUser);
}
