package it.valeriovaudi.service;

import it.valeriovaudi.factory.SecurityUserFactory;
import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.security.PhoneBookSecurityRole;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Valerio on 11/10/2014.
 */
@Service
public class CreateNewUserService {
    private PhonBookUserRepository phonBookUserRepository;
    private SecurityUserFactory<PhoneBookUser> securityUserFactory;

    public PhoneBookUser saveNewUser(PhoneBookUser phoneBookUser) {
        phoneBookUser.setSecurityRole(PhoneBookSecurityRole.USER);
        phoneBookUser = securityUserFactory.securityAccontWithPasswordEncoded(phoneBookUser);
        PhoneBookUser save = phonBookUserRepository.save(phoneBookUser);
        return save;
    }

    @Autowired
    public void setPhonBookUserRepository(PhonBookUserRepository phonBookUserRepository) {
        this.phonBookUserRepository = phonBookUserRepository;
    }

    @Autowired
    public void setSecurityUserFactory(SecurityUserFactory securityUserFactory) {
        this.securityUserFactory = securityUserFactory;
    }
}
