package it.valeriovaudi.repository;

import it.valeriovaudi.web.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Valerio on 16/07/2014.
 */
@Transactional
public interface ContactRepository extends CrudRepository<Contact,Long> {
}
