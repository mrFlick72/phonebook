package it.valeriovaudi.batch;

import it.valeriovaudi.controller.AbstractTest;
import it.valeriovaudi.repository.ContactRepository;
import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.web.model.Contact;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by Valerio on 22/08/2014.
 */
public class InitializationBatchTest extends AbstractTest {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    PhonBookUserRepository phonBookUserRepository;

    @Test
    @DirtiesContext
    public void initTest() {
        List<PhoneBookUser> phoneBookUsers = (List<PhoneBookUser>) phonBookUserRepository.findAll();
        List<Contact> contacts = (List<Contact>) contactRepository.findAll();

        Assert.assertEquals(4,phoneBookUsers.size());
        Assert.assertEquals(10,contacts.size());
    }
}
