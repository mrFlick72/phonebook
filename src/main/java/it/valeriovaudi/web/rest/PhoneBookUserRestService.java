package it.valeriovaudi.web.rest;

import it.valeriovaudi.builder.PhoneBookUserBuilder;
import it.valeriovaudi.factory.SecurityUserFactory;
import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.service.PasswordService;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import sun.security.util.Password;

import java.net.URI;
import java.util.List;

/**
 * Created by Valerio on 19/09/2014.
 */
@Controller
public class PhoneBookUserRestService {

    private PhonBookUserRepository phonBookUserRepository;
    private PasswordService passwordService;

    private PasswordEncoder passwordEncoder;

    private PhoneBookUserBuilder phoneBookUserBuilder;

    @Autowired
    public void setPhoneBookUserBuilder(PhoneBookUserBuilder phoneBookUserBuilder) {
        this.phoneBookUserBuilder = phoneBookUserBuilder;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setPhonBookUserRepository(PhonBookUserRepository phonBookUserRepository) {
        this.phonBookUserRepository = phonBookUserRepository;
    }

    @Autowired
    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Secured(value = "IS_AUTHENTICATED_FULLY")
    @RequestMapping(value = "/phoneBoockUser/{userName}", method = RequestMethod.GET)
    public @ResponseBody PhoneBookUser getPhoneBookUser(@PathVariable(value = "userName") String userName){
        return phonBookUserRepository.findByUserName(userName);
    }

    @Secured(value = "IS_AUTHENTICATED_FULLY")
    @RequestMapping(value = "/phoneBoockUser", method = RequestMethod.GET)
    public @ResponseBody List<PhoneBookUser> getPhoneBookUsers(){
        return (List<PhoneBookUser>) phonBookUserRepository.findAll();
    }

    @Secured(value = "IS_AUTHENTICATED_FULLY")
    @RequestMapping(value = "/phoneBoockUser/{userName}/password", method = RequestMethod.POST)
    public HttpEntity<Void> startResetPasswordProcedure(@PathVariable(value = "userName") String userName,@RequestBody String mail){
        String nonce = passwordService.createNonce(userName, mail);

        URI uri = UriComponentsBuilder.
                        fromPath("resetPassword/reset").
                        queryParam("nonce", nonce).
                        build().
                        toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);

        return new ResponseEntity<>(null, headers, HttpStatus.CREATED);
    }

//    update methods
    @Transactional
    @Secured(value = "IS_AUTHENTICATED_FULLY")
    @RequestMapping(value = "/phoneBoockUser/{userName}", method = RequestMethod.PUT)
    public HttpEntity<Void> updatePhonBoockUser(@PathVariable(value = "userName") String userName, @RequestBody PhoneBookUser phoneBookUser){
        PhoneBookUser phoneBookUserAux = phonBookUserRepository.findByUserName(userName);

        phoneBookUserAux.setFirstName(phoneBookUser.getFirstName());
        phoneBookUserAux.setLastName(phoneBookUser.getLastName());
        phoneBookUserAux.setMail(phoneBookUser.getMail());
        phoneBookUserAux.setPassword(passwordEncoder.encode(phoneBookUser.getPassword()));


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
