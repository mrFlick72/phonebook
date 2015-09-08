package it.valeriovaudi.config.appconfig;

import it.valeriovaudi.factory.SecurityUserFactory;
import it.valeriovaudi.factory.SecurityUserFactoryImpl;
import it.valeriovaudi.security.nonce.NonceFactory;
import it.valeriovaudi.security.nonce.NonceFactoryByUser;
import it.valeriovaudi.security.nonce.NonceFactoryByUserImpl;
import it.valeriovaudi.security.nonce.NonceFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Valerio on 29/03/2015.
 *
 *          <bean id="createNonce"
                 class="it.valeriovaudi.security.nonce.NonceFactoryImpl"
                 p:hashIteration="1000"/>

             <bean id="nonceFactoryByUser"
                 class="it.valeriovaudi.security.nonce.NonceFactoryByUserImpl"
                 p:nonceFactory-ref="createNonce"/>

            <bean id="securityUserFactory"
                 class="it.valeriovaudi.factory.SecurityUserFactoryImpl"/>
 */
@Configuration
public class FactoryContext {

    @Bean
    public NonceFactory createNonce(){
        NonceFactoryImpl nonceFactory = new NonceFactoryImpl();
        nonceFactory.setHashIteration(1000);

        return nonceFactory;
    }


    @Bean
    public NonceFactoryByUser nonceFactoryByUser(NonceFactory nonceFactory){
        NonceFactoryByUserImpl nonceFactoryByUser = new NonceFactoryByUserImpl();
        nonceFactoryByUser.setNonceFactory(nonceFactory);

        return nonceFactoryByUser;
    }

    @Bean
    public SecurityUserFactory securityUserFactory(){
        return new SecurityUserFactoryImpl();
    }
}
