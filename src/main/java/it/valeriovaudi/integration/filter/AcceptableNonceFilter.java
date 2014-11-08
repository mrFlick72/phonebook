package it.valeriovaudi.integration.filter;

import it.valeriovaudi.web.model.security.Nonce;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;

import java.util.Date;

/**
 * Created by Valerio on 25/10/2014.
 */
@MessageEndpoint
public class AcceptableNonceFilter {

    @Filter
    public boolean accept(Nonce nonce){
        boolean accept = false;
        if(nonce!= null){
            Date toDay = new Date();
            accept = !(nonce.isUsed() || nonce.getStart().after(toDay) || nonce.getStop().before(toDay));
        }
        return accept;
    }
}
