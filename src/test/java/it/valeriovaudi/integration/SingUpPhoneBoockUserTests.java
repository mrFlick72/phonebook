package it.valeriovaudi.integration;

import it.valeriovaudi.builder.PhoneBookUserBuilder;
import it.valeriovaudi.controller.AbstractTest;
import it.valeriovaudi.service.SignUpService;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.security.util.Password;

/**
 * Created by Valerio on 22/10/2014.
 */

public class SingUpPhoneBoockUserTests extends AbstractTest{


    @Autowired
    private SignUpService signUpService;

    @Autowired
    PhoneBookUserBuilder phoneBookUserBuilder;

    @Test
    public void singUpPhoneBoockUserTest(){

        logger.info(phoneBookUserBuilder);
        PhoneBookUser phoneBookUser = phoneBookUserBuilder.buildFirstName("Valerio")
                                                           .buildLastName("Vaudi")
                                                           .buildMail("valerio.vaudi@gmail.com")
                                                           .buildPassword("val")
                                                           .buildUserName("val")
                                                           .buildPhoneBookUser();
        logger.info(phoneBookUser);
        Assert.assertNotNull(phoneBookUser);

        signUpService.phoneBookUserSingIn(phoneBookUser);
    }
}