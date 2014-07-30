package it.valeriovaudi.support;

import it.valeriovaudi.factory.SecurityUserFactory;
import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.web.model.PhonBookUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by Valerio on 26/07/2014.
 */
public class DefaultUserStarterSupport {
    private PhonBookUserRepository phonBookUserRepository;
    private SecurityUserFactory<PhonBookUser> phonBookUserSecurityUserFactory;

    @PostConstruct
    private void initUsers(){
        PhonBookUser phonBookUser = new PhonBookUser();

        phonBookUser.setFirstName("Administrator");
        phonBookUser.setLastName("");

        phonBookUser.setUserName("admin");
        phonBookUser.setPassword("admin");

        PhonBookUser phonBookUserWithSecurityConstraint = phonBookUserSecurityUserFactory.securityAccontWithPasswordEncoded(phonBookUser);
        PhonBookUser save = phonBookUserRepository.save(phonBookUserWithSecurityConstraint);
        System.out.println(save);
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
