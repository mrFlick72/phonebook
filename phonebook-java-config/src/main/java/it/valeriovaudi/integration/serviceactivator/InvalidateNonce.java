package it.valeriovaudi.integration.serviceactivator;

import it.valeriovaudi.repository.security.NonceRepository;
import it.valeriovaudi.web.model.security.Nonce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Valerio on 08/11/2014.
 */
@Component
public class InvalidateNonce {

    private NonceRepository nonceRepository;

    @Autowired
    public void setNonceRepository(NonceRepository nonceRepository) {
        this.nonceRepository = nonceRepository;
    }

    public Nonce invalidateNonce(Nonce nonce){
        nonce.setUsed(true);
        nonceRepository.save(nonce);
        return nonce;
    }
}
