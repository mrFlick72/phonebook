package it.valeriovaudi.factory;

import it.valeriovaudi.web.model.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by Valerio on 26/07/2014.
 */
public interface SecurityUserFactory {

    Authentication getAutenticatedUser(UserDTO userDTO);

    UserDetails createUser(UserDTO userDTO);

}
