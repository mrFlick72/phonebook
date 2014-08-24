package it.valeriovaudi.batch;

import it.valeriovaudi.factory.SecurityUserFactory;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Valerio on 22/08/2014.
 */
public class UserItemProcessor implements ItemProcessor<PhoneBookUser,PhoneBookUser> {

    private SecurityUserFactory<PhoneBookUser> phonBookUserSecurityUserFactory;

    @Autowired
    public void setPhonBookUserSecurityUserFactory(SecurityUserFactory<PhoneBookUser> phonBookUserSecurityUserFactory) {
        this.phonBookUserSecurityUserFactory = phonBookUserSecurityUserFactory;
    }

    @Override
    public PhoneBookUser process(PhoneBookUser phoneBookUser) throws Exception {
        PhoneBookUser phoneBookUser1 = phonBookUserSecurityUserFactory.securityAccontWithPasswordEncoded(phoneBookUser);
        return phoneBookUser1;
    }
}
