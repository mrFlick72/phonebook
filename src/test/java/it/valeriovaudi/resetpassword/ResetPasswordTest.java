package it.valeriovaudi.resetpassword;

import it.valeriovaudi.controller.AbstractTest;
import it.valeriovaudi.security.nonce.NonceFactoryImpl;
import it.valeriovaudi.service.PasswordService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Valerio on 17/10/2014.
 */
public class ResetPasswordTest extends AbstractTest {

    @Autowired
    private PasswordService passwordService;

    @Test
    public void test() throws InterruptedException {
        String nonce = passwordService.createNonce("admin", "admin@localhost");
        logger.info(nonce);
        passwordService.resetPassword("admin123",nonce);
    }

    @Test
    public void createNonceTest() throws InterruptedException {
        String nonce = passwordService.createNonce("admin", "admin@localhost");
        logger.info(nonce);
    }
}
