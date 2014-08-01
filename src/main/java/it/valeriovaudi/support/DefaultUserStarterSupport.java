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
        initUser("Administrator","","admin","admin");
        initUser("Valerio","Vaudi","admin1","admin");
    }

    private void initUser(String ... data){
        PhonBookUser phonBookUser = new PhonBookUser();

        phonBookUser.setFirstName(data[0]);
        phonBookUser.setLastName(data[1]);

        phonBookUser.setUserName(data[2]);
        phonBookUser.setPassword(data[3]);

        PhonBookUser phonBookUserWithSecurityConstraint = phonBookUserSecurityUserFactory.securityAccontWithPasswordEncoded(phonBookUser);
        phonBookUserRepository.save(phonBookUserWithSecurityConstraint);
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
