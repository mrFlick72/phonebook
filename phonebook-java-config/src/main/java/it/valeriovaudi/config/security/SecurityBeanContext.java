package it.valeriovaudi.config.security;

import it.valeriovaudi.security.PhoneBookUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * Created by Valerio on 29/03/2015.
 */
@Configuration
public class SecurityBeanContext {

    @Bean
    public StandardPasswordEncoder standardPasswordEncoder(){
        return new StandardPasswordEncoder();
    }

    @Bean
    public PhoneBookUserDetailsService phoneBookUserDetailsService(){
        return new PhoneBookUserDetailsService();
    }
}
