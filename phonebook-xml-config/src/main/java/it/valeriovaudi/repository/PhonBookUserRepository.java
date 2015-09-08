package it.valeriovaudi.repository;

import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Valerio on 30/07/2014.
 */
public interface PhonBookUserRepository extends CrudRepository<PhoneBookUser,Long> {
    @Query
    public List<PhoneBookUser> findAllUser();
    public PhoneBookUser findByUserName(String userName);
}
