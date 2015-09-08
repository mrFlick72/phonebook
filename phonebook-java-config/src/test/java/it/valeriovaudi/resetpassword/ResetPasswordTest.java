package it.valeriovaudi.resetpassword;

import it.valeriovaudi.controller.AbstractTest;
import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.service.PasswordService;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Valerio on 17/10/2014.
 */
public class ResetPasswordTest extends AbstractTest {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private PhonBookUserRepository phonBookUserRepository;

    @Test
    public void test() throws InterruptedException {
        String nonce = passwordService.createNonce("admin", "admin@localhost");
        Assert.assertNotNull(nonce);
        Assert.assertNotEquals(nonce.trim(),"");
        logger.info(nonce);
        PhoneBookUser phoneBookUser = phonBookUserRepository.findByUserName("admin");
        logger.info(phoneBookUser);

        PhoneBookUser phoneBookUser1 = passwordService.resetPassword("admin123", nonce);
        logger.info(phoneBookUser1);

    }

    @Test
    public void createNonceTest() throws InterruptedException {
        String nonce = passwordService.createNonce("admin", "admin@localhost");
        logger.info(nonce);
    }
}
