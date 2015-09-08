package it.valeriovaudi.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 <security:global-method-security secured-annotations="enabled">

 </security:global-method-security>

 <security:http auto-config="true" disable-url-rewriting="true" use-expressions="true">
 <security:form-login login-page="/signin"  default-target-url="/index" authentication-failure-url="/signin?error=1"/>
 <security:logout logout-url="/logout" logout-success-url="/signin" invalidate-session="true"/>

 <security:intercept-url pattern="/" access="permitAll" />
 <security:intercept-url pattern="/resources/**" access="permitAll" />

 <security:intercept-url pattern="/signin" access="permitAll" />
 <security:intercept-url pattern="/signup" access="permitAll" />
 <security:intercept-url pattern="/resetPassword/resetFormDataCollect" access="permitAll" />
 <security:intercept-url pattern="/resetPassword/reset" access="permitAll" />

 <security:intercept-url pattern="/**" access="isAuthenticated()" />
 </security:http>

 <security:authentication-manager erase-credentials="false" alias="authenticationManager">
     <security:authentication-provider user-service-ref="bookUserDetailsService">
        <security:password-encoder ref="passwordEncoder"/>
     </security:authentication-provider>
 </security:authentication-manager>

 <bean id="passwordEncoder"
 class="org.springframework.security.crypto.password.StandardPasswordEncoder"/>

 <bean id="bookUserDetailsService"
 class="it.valeriovaudi.security.PhoneBookUserDetailsService"/>
 */

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class WebSecurityContext extends WebSecurityConfigurerAdapter {

    /*******************************************Log-In properties******************************************************/
    private static final String LOG_IN_URL_PAGE = "/signin";
    private static final String DEFAULT_TARGET_URL_PAGE = "/index";
    private static final String AUTHENTICATION_FAILURE_URL_PAGE = "/signin?error=1";

    /*******************************************Log-Out properties*****************************************************/
    private static final String LOG_OUT_URL_PAGE = "/logout";
    private static final String LOG_OUT_SUCCESS_URL = "/signin";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .headers().disable()
            .sessionManagement()
                .enableSessionUrlRewriting(true)
            .and()
                .formLogin()
                    .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                    .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                    .loginProcessingUrl("/j_spring_security_check")
                    .loginPage(LOG_IN_URL_PAGE)
                    .defaultSuccessUrl(DEFAULT_TARGET_URL_PAGE)
                    .failureUrl(AUTHENTICATION_FAILURE_URL_PAGE)
            .and()
                .logout()
                .logoutUrl(LOG_OUT_URL_PAGE)
                    .logoutSuccessUrl(LOG_OUT_SUCCESS_URL)
                    .invalidateHttpSession(true)
            .and()
                .authorizeRequests()
                .antMatchers("/resources/**",
                             "/favicon.ico",
                             "/signup",
                             LOG_IN_URL_PAGE,
                             LOG_OUT_URL_PAGE,
                             "/resetPassword/*").permitAll()
                             .antMatchers("/**").fullyAuthenticated()
                             .anyRequest().authenticated();
     }

    @Autowired
    public void configureAuthenticationManagerBuilder(AuthenticationManagerBuilder auth) throws Exception {
                 auth
                 .eraseCredentials(false)
                 .userDetailsService(userDetailsService)
                 .passwordEncoder(passwordEncoder);
    }

}
