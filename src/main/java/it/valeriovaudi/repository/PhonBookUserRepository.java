package it.valeriovaudi.repository;

import it.valeriovaudi.web.model.PhonBookUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Valerio on 30/07/2014.
 */
@Transactional
public interface PhonBookUserRepository extends CrudRepository<PhonBookUser,String> {
}
