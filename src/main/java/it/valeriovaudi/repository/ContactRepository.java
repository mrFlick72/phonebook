package it.valeriovaudi.repository;

import it.valeriovaudi.web.model.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

/**
 * Created by Valerio on 16/07/2014.
 */
@Transactional
public interface ContactRepository extends CrudRepository<Contact,Long> {
    @Transactional(readOnly = true)
    public Iterable<Contact> findAllContactByUser(@Param("userName")String userName);
}
