package it.valeriovaudi.repository;

import it.valeriovaudi.controller.AbstractTest;
import it.valeriovaudi.repository.security.NonceRepository;
import it.valeriovaudi.security.nonce.NonceFactory;
import it.valeriovaudi.security.nonce.NonceFactoryByUser;
import it.valeriovaudi.web.model.PhoneBookUser;
import it.valeriovaudi.web.model.security.Nonce;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by Valerio on 18/10/2014.
 */

public class NonceRepositoryTests extends AbstractTest {

    @Autowired
    private NonceRepository nonceRepository;

    @Autowired
    private NonceFactoryByUser nonceFactoryByUser;

    @Autowired
    private PhonBookUserRepository phonBookUserRepository;

    @Test
    public void insertTest(){
        Nonce nonce;
        for(int i = 0 ; i < 1000L ; i++){
            nonce = nonceRepository.save(getNonce(true));
            logger.info(nonce);
        }

        try{
            nonce = nonceRepository.save(getNonce(false));
            logger.info(nonce);

            Nonce byNonce = nonceRepository.findByNonce(nonce.getNonce());
            logger.info(byNonce);


            nonce = nonceRepository.save(getNonce(false));
            logger.info(nonce);

        } catch (Exception e){
            logger.info(e.getMessage());
        }


    }


    private Nonce getNonce(boolean random){
        PhoneBookUser byUserName = phonBookUserRepository.findByUserName("mrFlickete");
        Nonce nonce = nonceFactoryByUser.getNonce(byUserName);

        if(!random){
            nonce.setNonce("ghghghghg9wqhdbiucgwequighc9");
        }

        return nonce;
    }
}
