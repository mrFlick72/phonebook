package it.valeriovaudi.service;

import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Payload;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Valerio on 17/10/2014.
 */

public interface PasswordService {

    @Gateway(requestChannel = "createNonceServiceMainRequestChannel")
    void createNonce(@Payload String userName, @Header("mail") String mail);

    @Gateway(requestChannel = "restetPasswordServiceMainRequestChannel",replyChannel = "singUpPhoneBoockUserMainResponseChannel")
    PhoneBookUser resetPassword(@Header(value = "password")String resetPassword, @Payload String nonce);
}
