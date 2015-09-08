package it.valeriovaudi.config;

import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by Valerio on 16/03/2015.
 */
@Configuration
@ComponentScan(basePackages = {"it.valeriovaudi.config.integration",
                                "it.valeriovaudi.config.batch",
                                "it.valeriovaudi.config.appconfig",
                                "it.valeriovaudi.config.security"})
@PropertySource("classpath:mail.properties")
@EnableAspectJAutoProxy
@EnableSpringConfigured
@EnableLoadTimeWeaving
public class PhonBookApplicationContext {

    @Bean
    public static PlaceholderConfigurerSupport propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
