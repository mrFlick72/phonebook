package it.valeriovaudi.batch;

import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.util.GreenMail;
import it.valeriovaudi.factory.SecurityUserFactory;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Valerio on 22/08/2014.
 */
public class UserItemProcessor implements ItemProcessor<PhoneBookUser,PhoneBookUser> {

    private SecurityUserFactory<PhoneBookUser> phonBookUserSecurityUserFactory;
    private GreenMail greenMail;

    @Value("#{embeddedMailServerStarter.greenMail}")
    public void setGreenMail(GreenMail greenMail) {
        this.greenMail = greenMail;
    }

    @Autowired
    public void setPhonBookUserSecurityUserFactory(SecurityUserFactory<PhoneBookUser> phonBookUserSecurityUserFactory) {
        this.phonBookUserSecurityUserFactory = phonBookUserSecurityUserFactory;
    }

    @Override
    public PhoneBookUser process(PhoneBookUser phoneBookUser) throws Exception {
        greenMail.setUser(phoneBookUser.getMail(), phoneBookUser.getUserName(), phoneBookUser.getPassword());
        PhoneBookUser phoneBookUserAux = phonBookUserSecurityUserFactory.securityAccontWithPasswordEncoded(phoneBookUser);

        return phoneBookUserAux;
    }
}
