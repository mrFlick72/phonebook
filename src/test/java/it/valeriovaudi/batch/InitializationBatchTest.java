package it.valeriovaudi.batch;

import it.valeriovaudi.repository.ContactRepository;
import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.web.model.Contact;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by Valerio on 22/08/2014.
 */
@ContextConfiguration(locations = {"classpath:batch/application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class InitializationBatchTest {
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    PhonBookUserRepository phonBookUserRepository;

    @Test
    @Ignore
    public void initTest() throws JobParametersInvalidException,
                                  JobExecutionAlreadyRunningException,
                                  JobRestartException,
                                  JobInstanceAlreadyCompleteException {
        JobParameters toDay = new JobParametersBuilder().addDate("toDay", new Date()).toJobParameters();
        JobExecution run = jobLauncher.run(job, toDay);
        Assert.assertEquals(run.getExitStatus(),ExitStatus.COMPLETED);

        List<PhoneBookUser> phoneBookUsers = (List<PhoneBookUser>) phonBookUserRepository.findAll();
        List<Contact> contacts = (List<Contact>) contactRepository.findAll();

        Assert.assertEquals(4,phoneBookUsers.size());
        Assert.assertEquals(10,contacts.size());
    }
}
