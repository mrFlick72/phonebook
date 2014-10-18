package it.valeriovaudi.service;

import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.Payload;

/**
 * Created by Valerio on 17/10/2014.
 */

public interface PasswordService {
    void resetPassword(@Payload String userName, @Header("mail") String mail);
}
