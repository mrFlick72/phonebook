package it.valeriovaudi.integration;

import it.valeriovaudi.web.model.security.Nonce;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;

import java.util.Date;

/**
 * Created by Valerio on 25/10/2014.
 */
@MessageEndpoint
public class AcceptableNonceRouter {

    @Router
    public boolean accept(Nonce nonce){
        boolean accept = false;
        if(nonce!= null){
            accept = !(nonce.isUsed() || nonce.getStart().before(new Date()) || nonce.getStop().after(new Date()));
        }
        return accept;
    }
}
