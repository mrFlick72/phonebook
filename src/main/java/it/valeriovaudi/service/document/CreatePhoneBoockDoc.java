package it.valeriovaudi.service.document;

import it.valeriovaudi.web.model.Contact;

import java.util.List;

/**
 * Created by Valerio on 08/02/2015.
 **/
public interface CreatePhoneBoockDoc {
    byte[] createPhoneBoockDoc(List<Contact> contactList);
}
