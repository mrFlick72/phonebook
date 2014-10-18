package it.valeriovaudi.security.nonce;

import it.valeriovaudi.web.model.PhoneBookUser;
import it.valeriovaudi.web.model.security.Nonce;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by Valerio on 18/10/2014.
 */
public class NonceFactoryByUserImpl implements NonceFactoryByUser {

    private NonceFactory nonceFactory;

    @Override
    public Nonce getNonce(PhoneBookUser phoneBookUser){
        Nonce nonce = new Nonce();

        Date startDate = new Date();
        Date stopDate = new Date();

        nonce.setStart(startDate);
        nonce.setStop(stopDate);
        nonce.setUserName(phoneBookUser.getUserName());
        nonce.setUsed(false);

        String hashNonce = nonceFactory.getNonce(phoneBookUser.getFirstName(), phoneBookUser.getLastName(), phoneBookUser.getUserName(), phoneBookUser.getMail());
        nonce.setNonce(hashNonce);

        return nonce;
    }

    @Autowired
    public void setNonceFactory(NonceFactory nonceFactory) {
        this.nonceFactory = nonceFactory;
    }
}
