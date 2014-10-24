package it.valeriovaudi.web.rest;

import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.service.PasswordService;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @RequestMapping(value = "/phoneBoockUser/{userName}/{mail}/password", method = RequestMethod.PUT)
    public HttpEntity<Void> resetPassword(@PathVariable(value = "userName") String userName,@PathVariable(value = "mail") String mail){
        passwordService.createNonce(userName,mail);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Secured(value = "IS_AUTHENTICATED_FULLY")
    @RequestMapping(value = "/phoneBoockUser/{userName}/{mail}", method = RequestMethod.PUT)
    public HttpEntity<Void> resetMail(@PathVariable(value = "userName") String userName, @PathVariable(value = "mail") String mail){
        PhoneBookUser phoneBookUser = phonBookUserRepository.findByUserName(userName);
        phoneBookUser.setMail(mail);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
