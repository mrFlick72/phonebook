package it.valeriovaudi.repository;

import it.valeriovaudi.web.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Valerio on 16/07/2014.
 */
public interface ContactRepository extends CrudRepository<Contact,Long> {
    public Iterable<Contact> findAllContactByUser(@Param("userName")String userName);
}
