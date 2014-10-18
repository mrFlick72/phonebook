package it.valeriovaudi.repository.security;

import it.valeriovaudi.web.model.security.Nonce;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Valerio on 18/10/2014.
 */
public interface NonceRepository extends CrudRepository<Nonce,Long> {}
