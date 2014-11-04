package it.valeriovaudi.web.rest;

import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.service.PasswordService;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Created by Valerio on 19/09/2014.
 */
@Controller
public class PhoneBookUserRestService {

    private PhonBookUserRepository phonBookUserRepository;
    private PasswordService passwordService;

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
    @RequestMapping(value = "/phoneBoockUser/{userName}/{mail}/password", method = RequestMethod.POST)
    public HttpEntity<Void> startResetPasswordProcedure(@PathVariable(value = "userName") String userName,@PathVariable(value = "mail") String mail){
        String nonce = passwordService.createNonce(userName, mail);

        URI uri = UriComponentsBuilder.
                        fromPath("resetPassword/reset").
                        scheme("http").
                        host("localhost").
                        port(8080).
                        queryParam("nonce",nonce).
                        build().
                        toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);

        return new ResponseEntity<>(null, headers, HttpStatus.CREATED);
    }

//    update methods

    @Secured(value = "IS_AUTHENTICATED_FULLY")
    @RequestMapping(value = "/phoneBoockUser/{userName}/{firstName}", method = RequestMethod.PUT)
    public HttpEntity<Void> changeFirstName(@PathVariable(value = "userName") String userName, @PathVariable(value = "firstName") String firstName){
        PhoneBookUser phoneBookUser = phonBookUserRepository.findByUserName(userName);
        phoneBookUser.setFirstName(firstName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Secured(value = "IS_AUTHENTICATED_FULLY")
    @RequestMapping(value = "/phoneBoockUser/{userName}/{lastName}", method = RequestMethod.PUT)
    public HttpEntity<Void> changeLastName(@PathVariable(value = "userName") String userName, @PathVariable(value = "lastName") String lastName){
        PhoneBookUser phoneBookUser = phonBookUserRepository.findByUserName(userName);
        phoneBookUser.setLastName(lastName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Secured(value = "IS_AUTHENTICATED_FULLY")
    @RequestMapping(value = "/phoneBoockUser/{userName}/{mail}", method = RequestMethod.PUT)
    public HttpEntity<Void> changeMail(@PathVariable(value = "userName") String userName, @PathVariable(value = "mail") String mail){
        PhoneBookUser phoneBookUser = phonBookUserRepository.findByUserName(userName);
        phoneBookUser.setMail(mail);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Secured(value = "IS_AUTHENTICATED_FULLY")
    @RequestMapping(value = "/phoneBoockUser/{userName}/{password}", method = RequestMethod.PUT)
    public HttpEntity<Void> changePassword(@PathVariable(value = "userName") String userName,@PathVariable(value = "password") String password){
        PhoneBookUser phoneBookUser = phonBookUserRepository.findByUserName(userName);
        phoneBookUser.setPassword(password);
        phonBookUserRepository.save(phoneBookUser);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
