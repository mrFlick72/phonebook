package it.valeriovaudi.security.nonce;

import it.valeriovaudi.web.model.PhoneBookUser;
import it.valeriovaudi.web.model.security.Nonce;

/**
 * Created by Valerio on 17/10/2014.
 */
public interface NonceFactoryByUser{
    Nonce getNonce(PhoneBookUser phoneBookUser);
}
