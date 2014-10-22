package it.valeriovaudi.integration;

import it.valeriovaudi.builder.PhoneBookUserBuilder;
import it.valeriovaudi.service.SignUpService;
import it.valeriovaudi.web.model.PhoneBookUser;
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

@ContextConfiguration(locations = {"classpath:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SingUpPhoneBoockUserTests {


    @Autowired
    private SignUpService signUpService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void singUpPhoneBoockUserTest(){
        PhoneBookUserBuilder phoneBookUserBuilder = PhoneBookUserBuilder.newPhoneBookUserBuilder();
        phoneBookUserBuilder.setPasswordEncoder(passwordEncoder);

        PhoneBookUser phoneBookUser = phoneBookUserBuilder.buildFirstName("Valerio").buildLastName("Vaudi").buildMail("valerio.vaudi@gmail.com").buildPassword("val").buildUserName("val").buildPhoneBookUser();

        signUpService.phoneBookUserSingIn(phoneBookUser);
    }
}
