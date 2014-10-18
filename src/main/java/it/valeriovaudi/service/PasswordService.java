package it.valeriovaudi.service;

import org.springframework.integration.annotation.Payload;
import org.springframework.messaging.handler.annotation.Header;

/**
 * Created by Valerio on 17/10/2014.
 */

public interface PasswordService {
    boolean resetPassword(@Payload String userName, @Header(value = "mail") String mail);
}
