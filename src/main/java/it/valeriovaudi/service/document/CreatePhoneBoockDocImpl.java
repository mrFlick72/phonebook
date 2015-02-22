package it.valeriovaudi.service.document;

import it.valeriovaudi.web.model.Contact;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by Valerio on 22/02/2015.
 */
public class CreatePhoneBoockDocImpl implements CreatePhoneBoockDoc {
    private PhoneBookDocBulder phoneBookDocBulder;
    private static final Logger LOGGER = Logger.getLogger(CreatePhoneBoockDocImpl.class);

    @Override
    public byte[] createPhoneBoockDoc(List<Contact> contactList) {
        byte[] document = new byte[0];
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {
            // create the generic phonebook doc;
            document = phoneBookDocBulder.createNewPhoneBookDoc(byteArrayOutputStream)
                                .addPhoneBookTitle("My Phone Book")
                                .addPhoneBookTable(contactList)
                                .buildDocument();
        } catch (Exception e) {
            LOGGER.error(e);
        }
        // return the binary content;
        return document;
    }

    public void setPhoneBookDocBulder(PhoneBookDocBulder phoneBookDocBulder) {
        this.phoneBookDocBulder = phoneBookDocBulder;
    }
}
