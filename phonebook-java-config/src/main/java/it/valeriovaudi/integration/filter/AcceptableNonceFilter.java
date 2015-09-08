package it.valeriovaudi.integration.filter;

import it.valeriovaudi.web.model.security.Nonce;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Valerio on 25/10/2014.
 */
@Component
public class AcceptableNonceFilter {

    public boolean accept(Nonce nonce){
        boolean accept = false;
        if(nonce!= null){
            Date toDay = new Date();
            accept = !(nonce.isUsed() || nonce.getStart().after(toDay) || nonce.getStop().before(toDay));
        }
        return accept;
    }

}
