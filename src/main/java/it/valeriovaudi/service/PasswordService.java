package it.valeriovaudi.service;

import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.Payload;

/**
 * Created by Valerio on 17/10/2014.
 */

public interface PasswordService {
    String createNonce(String userName, String mail);
    PhoneBookUser resetPassword(String resetPassword, String nonce);
}