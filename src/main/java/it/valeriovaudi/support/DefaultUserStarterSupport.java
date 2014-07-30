package it.valeriovaudi.support;

import it.valeriovaudi.factory.PhonBookUserFactory;
import it.valeriovaudi.factory.SecurityUserFactory;
import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.web.model.PhonBookUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by Valerio on 26/07/2014.
 */
public class DefaultUserStarterSupport {
    private PhonBookUserFactory phonBookUserFactory;
    private PhonBookUserRepository phonBookUserRepository;
    private SecurityUserFactory<PhonBookUser> phonBookUserSecurityUserFactory;

    @PostConstruct
    private void initUsers(){
        PhonBookUser phonBookUser = new PhonBookUser();

        phonBookUser.setFirstName("Administrator");
        phonBookUser.setLastName("");

        phonBookUser.setUserName("admin");
        phonBookUser.setPassword("admin");

        PhonBookUser phonBookUserWithSecurityConstraint = phonBookUserFactory.getPhonBookUserWithSecurityConstraint(phonBookUser);
        phonBookUserRepository.save(phonBookUserSecurityUserFactory.securityAccontWithPasswordEncoded(phonBookUserWithSecurityConstraint));
    }

    @Autowired
    public void setPhonBookUserFactory(PhonBookUserFactory phonBookUserFactory) {
        this.phonBookUserFactory = phonBookUserFactory;
    }

    @Autowired
    public void setPhonBookUserRepository(PhonBookUserRepository phonBookUserRepository) {
        this.phonBookUserRepository = phonBookUserRepository;
    }

    @Autowired
    public void setPhonBookUserSecurityUserFactory(SecurityUserFactory<PhonBookUser> phonBookUserSecurityUserFactory) {
        this.phonBookUserSecurityUserFactory = phonBookUserSecurityUserFactory;
    }
}
