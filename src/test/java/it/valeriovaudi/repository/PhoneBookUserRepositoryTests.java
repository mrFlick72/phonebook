package it.valeriovaudi.repository;

import it.valeriovaudi.controller.AbstractTest;
import it.valeriovaudi.security.PhoneBookSecurityRole;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Valerio on 22/08/2014.
 */

public class PhoneBookUserRepositoryTests extends AbstractTest {

    @Autowired
    private PhonBookUserRepository phonBookUserRepository;

    @Test
    public void insertTest(){

        PhoneBookUser phoneBookUser = new PhoneBookUser();

        phoneBookUser.setFirstName("Mike");
        phoneBookUser.setLastName("De Maggio");
        phoneBookUser.setSecurityRole(PhoneBookSecurityRole.USER);
        phoneBookUser.setMail("mike.diMaggio@localhost");
        phoneBookUser.setUserName("mike72");
        phoneBookUser.setPassword("mike72");

        phonBookUserRepository.save(phoneBookUser);

        PhoneBookUser mike72 = phonBookUserRepository.findByUserName("mike72");

        System.out.println(mike72);
        Assert.assertNotNull(mike72);
    }
}
