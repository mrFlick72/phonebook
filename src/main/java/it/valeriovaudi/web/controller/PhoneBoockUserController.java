package it.valeriovaudi.web.controller;

import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Valerio on 19/09/2014.
 */
@Controller
public class PhoneBoockUserController {

    private PhonBookUserRepository phonBookUserRepository;

    @Autowired
    public void setPhonBookUserRepository(PhonBookUserRepository phonBookUserRepository) {
        this.phonBookUserRepository = phonBookUserRepository;
    }

    @RequestMapping(value = "/phoneBoockUser/{userName}", method = RequestMethod.GET)
    @Secured(value = "IS_AUTHENTICATED_FULLY")
    public @ResponseBody PhoneBookUser getPhoneBookUser(@PathVariable(value = "userName") String userName){
        PhoneBookUser byUserName = phonBookUserRepository.findByUserName(userName);
        return byUserName;
    }

}
