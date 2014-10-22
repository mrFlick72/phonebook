package it.valeriovaudi.resetpassword;

import it.valeriovaudi.security.nonce.NonceFactoryImpl;
import it.valeriovaudi.service.PasswordService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Valerio on 17/10/2014.
 */
@ContextConfiguration(locations = {"classpath:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ResetPasswordTest {

    @Autowired
    private PasswordService passwordService;

    @Test
    @Ignore
    public void test() throws InterruptedException {
        passwordService.resetPassword("admin","admin@localhost");
    }

    @Test
    public void createNonceTest() throws InterruptedException {
        passwordService.createNonce("admin","admin@localhost");

        Thread.sleep(10000);
    }
}
