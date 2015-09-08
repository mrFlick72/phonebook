package it.valeriovaudi.service.document;

import it.valeriovaudi.web.model.Contact;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Valerio on 22/02/2015.
 */
public interface PhoneBookDocBulder {
    PhoneBookDocBulder createNewPhoneBookDoc(OutputStream outputStream) throws Exception;
    PhoneBookDocBulder addPhoneBookTitle(String text) throws Exception;
    PhoneBookDocBulder addPhoneBookTable(List<Contact> contactList) throws Exception;
    byte[] buildDocument() throws IOException;
}
