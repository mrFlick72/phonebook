package it.valeriovaudi.security;

import it.valeriovaudi.factory.SecurityUserFactory;
import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.web.model.PhonBookUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by Valerio on 30/07/2014.
 */
public class PhoneBookUserDetailsService implements UserDetailsService {

    private PhonBookUserRepository phonBookUserRepository;
    private SecurityUserFactory securityUserFactory;


    public void setPhonBookUserRepository(PhonBookUserRepository phonBookUserRepository) {
        this.phonBookUserRepository = phonBookUserRepository;
    }

    @Autowired
    public void setSecurityUserFactory(SecurityUserFactory securityUserFactory) {
        this.securityUserFactory = securityUserFactory;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PhonBookUser phonBookUser = phonBookUserRepository.findByUserName(username);

        return securityUserFactory.createUser(phonBookUser);
    }
}
