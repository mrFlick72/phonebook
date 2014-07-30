package it.valeriovaudi.factory;

import it.valeriovaudi.web.model.PhonBookUser;

/**
 * Created by Valerio on 30/07/2014.
 */
public class PhonBookUserFactory {

   public PhonBookUser getPhonBookUserWithSecurityConstraint(PhonBookUser phonBookUser){
        phonBookUser.setEnabled(true);
        phonBookUser.setAutority("ROLE_ADMIN");
        return phonBookUser;
    }
}
