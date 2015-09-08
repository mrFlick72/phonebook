package it.valeriovaudi.config.integration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

/**
 * Created by Valerio on 16/03/2015.
 */
@Configuration
@EnableIntegration
@IntegrationComponentScan(basePackages = "it.valeriovaudi.service")
@ComponentScan(basePackages = "it.valeriovaudi.integration")
public class IntegrationContext {
}