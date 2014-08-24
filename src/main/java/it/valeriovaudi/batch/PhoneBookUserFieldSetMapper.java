package it.valeriovaudi.batch;

import it.valeriovaudi.security.PhoneBookSecurityRole;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * Created by Valerio on 24/08/2014.
 */
public class PhoneBookUserFieldSetMapper implements FieldSetMapper<PhoneBookUser>{
    @Override
    public PhoneBookUser mapFieldSet(FieldSet fieldSet) throws BindException {
        PhoneBookUser phoneBookUser = new PhoneBookUser();

        phoneBookUser.setFirstName(fieldSet.readString(0));
        phoneBookUser.setLastName(fieldSet.readString(1));

        phoneBookUser.setUserName(fieldSet.readString(2));
        phoneBookUser.setPassword(fieldSet.readString(3));
        phoneBookUser.setSecurityRole(PhoneBookSecurityRole.valueOf(fieldSet.readString(4)));

        return phoneBookUser;
    }
}
