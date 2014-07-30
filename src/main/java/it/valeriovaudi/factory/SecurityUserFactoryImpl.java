package it.valeriovaudi.factory;

import it.valeriovaudi.web.model.PhonBookUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

/**
 * Created by Valerio on 26/07/2014.
 */
public class SecurityUserFactoryImpl implements SecurityUserFactory<PhonBookUser> {

    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication getAutenticatedUser(PhonBookUser user) {
        return new UsernamePasswordAuthenticationToken(createUser(user), user.getPassword() , Collections.singleton(createAuthority()));
    }

    @Override
    public UserDetails createUser(PhonBookUser user) {
        return new User(user.getUserName(), user.getPassword(), Collections.singleton(createAuthority()));
}

    @Override
    public PhonBookUser securityAccontWithPasswordEncoded(PhonBookUser phonBookUser) {
        phonBookUser.setEnabled(true);
        phonBookUser.setAutority("ROLE_ADMIN");
        phonBookUser.setPassword(passwordEncoder.encode(phonBookUser.getPassword()));
        return phonBookUser;
    }

    private GrantedAuthority createAuthority() {
        return new SimpleGrantedAuthority("ROLE_ADMIN");
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
