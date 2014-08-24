package it.valeriovaudi.batch;

import org.junit.Assert;
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

/**
 * Created by Valerio on 22/08/2014.
 */
@ContextConfiguration(locations = {"classpath:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class InitializationBatchTest {
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @Test
    public void initTest() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        JobParameters toDay = new JobParametersBuilder().addDate("toDay", new Date()).toJobParameters();
        JobExecution run = jobLauncher.run(job, toDay);
        for (Throwable throwable : run.getAllFailureExceptions()) {
            System.out.println(throwable.getCause());
        }
        ;
        Assert.assertEquals(run.getExitStatus(),ExitStatus.COMPLETED);
    }
}
