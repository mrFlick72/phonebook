package it.valeriovaudi.factory;

import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by Valerio on 26/07/2014.
 */
public interface SecurityUserFactory<T> {

    Authentication getAutenticatedUser(PhoneBookUser phoneBookUserDTO);

    UserDetails createUser(PhoneBookUser phoneBookUserDTO);

    T securityAccontWithPasswordEncoded(T t);
}
