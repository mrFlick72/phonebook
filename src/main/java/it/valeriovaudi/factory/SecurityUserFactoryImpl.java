package it.valeriovaudi.factory;

import it.valeriovaudi.web.model.UserDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

/**
 * Created by Valerio on 26/07/2014.
 */
public class SecurityUserFactoryImpl implements SecurityUserFactory {
    @Override
    public Authentication getAutenticatedUser(UserDTO userDTO) {
        return new UsernamePasswordAuthenticationToken(createUser(userDTO), userDTO.getPassword(), Collections.singleton(createAuthority()));
    }

    @Override
    public UserDetails createUser(UserDTO userDTO) {
        return new User(userDTO.getUserName(), userDTO.getPassword(), Collections.singleton(createAuthority()));
    }

    private GrantedAuthority createAuthority() {
        return new SimpleGrantedAuthority("ROLE_ADMIN");
    }

}
