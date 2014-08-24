package it.valeriovaudi.support;

import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * Created by Valerio on 26/07/2014.
 */

public class DefaultUserStarterSupport{
    private static Logger logger = Logger.getLogger(DefaultUserStarterSupport.class);

    private JobLauncher jobLauncher;
    private Job job;

    @Autowired
    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    @Autowired
    public void setJob(Job job) {
        this.job = job;
    }

    @PostConstruct
    public void init() {
        try {
            jobLauncher.run(job, new JobParametersBuilder().addDate("toDay",new Date()).toJobParameters());
        } catch (JobExecutionAlreadyRunningException |
                JobRestartException |
                JobInstanceAlreadyCompleteException |
                JobParametersInvalidException e) {
            logger.error(e);
        }

    }
}
