package it.valeriovaudi.integration.serviceactivator;

import it.valeriovaudi.repository.security.NonceRepository;
import it.valeriovaudi.web.model.security.Nonce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

/**
 * Created by Valerio on 08/11/2014.
 */
@MessageEndpoint
public class InvalidateNonce {

    private NonceRepository nonceRepository;

    @Autowired
    public void setNonceRepository(NonceRepository nonceRepository) {
        this.nonceRepository = nonceRepository;
    }

    @ServiceActivator
    public Nonce invalidateNonce(Nonce nonce){
        nonce.setUsed(true);
        nonceRepository.save(nonce);
        return nonce;
    }
}
