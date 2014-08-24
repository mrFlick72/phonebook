package it.valeriovaudi.batch;

import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.web.model.Contact;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;

/**
 * Created by Valerio on 24/08/2014.
 */
public class ContactFieldSetMapper implements FieldSetMapper<Contact> {

    private PhonBookUserRepository phonBookUserRepository;

    @Autowired
    public void setPhonBookUserRepository(PhonBookUserRepository phonBookUserRepository) {
        this.phonBookUserRepository = phonBookUserRepository;
    }

    @Override
    public Contact mapFieldSet(FieldSet fieldSet) throws BindException {
        System.out.println(fieldSet);
        PhoneBookUser phoneBookUser = phonBookUserRepository.findOne(Long.valueOf(fieldSet.readString(4)));

        Contact contact = new Contact();

        contact.setFirstName(fieldSet.readString(0));
        contact.setLastName(fieldSet.readString(1));
        contact.setTelephoneNumber(fieldSet.readString(2));

        contact.setPhoneBookUser(phoneBookUser);
        return contact;
    }
}
