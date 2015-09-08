package it.valeriovaudi.config.web;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.util.Collections;

/**
     <context:annotation-config/>

     <tx:annotation-driven/>

     <mvc:annotation-driven conversion-service="mvcConversionService" />
     <mvc:resources mapping="/resources/**" location="web-resources/, classpath:/META-INF/resources/webjars/" />

     <mvc:resources mapping="/favicon.ico" location="web-resources/img/favicon.jpg" />

     <mvc:interceptors>
         <bean id="localeChangeInterceptor"
         class="org.springframework.web.servlet.i18n.LocaleChangeInterceptor"
         p:paramName="lang"/>
     </mvc:interceptors>

     <bean id="templateResolver"
         class="org.thymeleaf.templateresolver.ServletContextTemplateResolver"
         p:prefix="/WEB-INF/templates/"
         p:templateMode="HTML5"
         p:suffix=".html"/>

     <bean id="templateEngine"
         class="org.thymeleaf.spring4.SpringTemplateEngine"
         p:templateResolver-ref="templateResolver">
         <property name="additionalDialects">
         <set>
         <bean class="org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect"/>
         </set>
         </property>
     </bean>

     <bean class="org.thymeleaf.spring4.view.ThymeleafViewResolver"
            p:templateEngine-ref="templateEngine"/>

      Message .properties sources
     <bean id="messageSource"
         class="org.springframework.context.support.ReloadableResourceBundleMessageSource"
         p:basenames="WEB-INF/messages/messages"/>


      Locale resolver for cookies
     <bean id="localeResolver"
         class="org.springframework.web.servlet.i18n.CookieLocaleResolver"
         p:cookieName="lang"
         p:defaultLocale="it"/>

     <bean id="mvcConversionService"
         class="org.springframework.format.support.FormattingConversionServiceFactoryBean"/>

     import the controller configuration
     <import resource="classpath:controller-config.xml"/>
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"it.valeriovaudi.web.controller","it.valeriovaudi.web.rest"})
@EnableWebMvc
public class PhoneBookMvcContext extends WebMvcConfigurerAdapter {

//    Thymeleaf Config
/*
*  <bean id="templateResolver"
         class="org.thymeleaf.templateresolver.ServletContextTemplateResolver"
         p:prefix="/WEB-INF/templates/"
         p:templateMode="HTML5"
         p:suffix=".html"/>

     <bean id="templateEngine"
         class="org.thymeleaf.spring4.SpringTemplateEngine"
         p:templateResolver-ref="templateResolver">
         <property name="additionalDialects">
         <set>
         <bean class="org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect"/>
         </set>
         </property>
     </bean>

     <bean class="org.thymeleaf.spring4.view.ThymeleafViewResolver"
            p:templateEngine-ref="templateEngine"/>
    */
    @Bean
    public ServletContextTemplateResolver templateResolver(){
        ServletContextTemplateResolver servletContextTemplateResolver = new ServletContextTemplateResolver();
        servletContextTemplateResolver.setPrefix("/WEB-INF/templates/");
        servletContextTemplateResolver.setSuffix(".html");
        servletContextTemplateResolver.setTemplateMode("HTML5");

        return servletContextTemplateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setTemplateResolver(templateResolver());
        springTemplateEngine.setAdditionalDialects(Collections.singleton(new SpringSecurityDialect()));
        return springTemplateEngine;
    }

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver(){
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(templateEngine());

        return thymeleafViewResolver;
    }

    /*
    Message .properties sources
    <bean id="messageSource"
    class="org.springframework.context.support.ReloadableResourceBundleMessageSource"
    p:basenames="WEB-INF/messages/messages"/>
    */

    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasenames("WEB-INF/messages/messages");

        return reloadableResourceBundleMessageSource;
    }
   /*
    <mvc:resources mapping="/resources*//**" location="web-resources/, classpath:/META-INF/resources/webjars/" />
    <mvc:resources mapping="/favicon.ico" location="web-resources/img/favicon.jpg" />
     */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);

        registry.addResourceHandler("/resources/**").addResourceLocations("web-resources/").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("web-resources/img/favicon.jpg");
    }

}
