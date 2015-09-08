package it.valeriovaudi.factory;

import it.valeriovaudi.security.PhoneBookSecurityRole;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Created by Valerio on 26/07/2014.
 */
@Component
public class SecurityUserFactoryImpl implements SecurityUserFactory<PhoneBookUser> {

    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication getAutenticatedUser(PhoneBookUser user) {
        return new UsernamePasswordAuthenticationToken(createUser(user), user.getPassword() , Collections.singleton(createAuthority(user.getSecurityRole())));
    }

    @Override
    public UserDetails createUser(PhoneBookUser user) {
        return new User(user.getUserName(), user.getPassword(), Collections.singleton(createAuthority(user.getSecurityRole())));
}

    @Override
    public PhoneBookUser securityAccontWithPasswordEncoded(PhoneBookUser phoneBookUser) {
        phoneBookUser.setPassword(passwordEncoder.encode(phoneBookUser.getPassword()));
        return phoneBookUser;
    }

    private GrantedAuthority createAuthority(PhoneBookSecurityRole role) {
        return new SimpleGrantedAuthority(role.getRole());
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
