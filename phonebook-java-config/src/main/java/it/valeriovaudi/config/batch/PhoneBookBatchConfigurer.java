package it.valeriovaudi.config.batch;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Created by Valerio on 01/04/2015.
 */

public class PhoneBookBatchConfigurer implements BatchConfigurer {

    private PlatformTransactionManager transactionManager;
    private DataSource initBatchDataSource;
    private JobLauncher jobLauncher;

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setInitBatchDataSource(DataSource initBatchDataSource) {
        this.initBatchDataSource = initBatchDataSource;
    }

    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    @Override
    public JobRepository getJobRepository() throws Exception {
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setDataSource(initBatchDataSource);
        jobRepositoryFactoryBean.setDatabaseType("H2");
        jobRepositoryFactoryBean.setTransactionManager(getTransactionManager());
        jobRepositoryFactoryBean.afterPropertiesSet();
        return jobRepositoryFactoryBean.getObject();
    }

    @Override
    public PlatformTransactionManager getTransactionManager() throws Exception {
        return transactionManager;
    }

    @Override
    public JobLauncher getJobLauncher() throws Exception {
        return jobLauncher;
    }

    @Override
    public JobExplorer getJobExplorer() throws Exception {
        JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
        jobExplorerFactoryBean.setDataSource(initBatchDataSource);
        jobExplorerFactoryBean.afterPropertiesSet();
        return jobExplorerFactoryBean.getObject();
    }
}
