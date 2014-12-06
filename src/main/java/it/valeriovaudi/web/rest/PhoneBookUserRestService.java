package it.valeriovaudi.web.rest;

import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.security.PhoneBookSecurityRole;
import it.valeriovaudi.service.PasswordService;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Created by Valerio on 19/09/2014.
 */
@Controller
@Transactional
public class PhoneBookUserRestService {

    private PhonBookUserRepository phonBookUserRepository;
    private PasswordService passwordService;

    private PasswordEncoder passwordEncoder;

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

    @Transactional(readOnly = true)
    @Secured(value = "IS_AUTHENTICATED_FULLY")
    @RequestMapping(value = "/phoneBoockUser/{userName}/data", method = RequestMethod.GET)
    public @ResponseBody PhoneBookUser getPhoneBookUser(@PathVariable(value = "userName") String userName){
        return phonBookUserRepository.findByUserName(userName);
    }

    @Transactional(readOnly = true)
    @Secured(value = "IS_AUTHENTICATED_FULLY")
    @RequestMapping(value = "/phoneBoockUser", method = RequestMethod.GET)
    public @ResponseBody List<PhoneBookUser> getPhoneBookUsers(){
        List<PhoneBookUser> phoneBookUsers;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getAuthorities().stream().filter((grantedAuthority) -> grantedAuthority.getAuthority().equals(PhoneBookSecurityRole.ADMIN.getRole())).count()!=0){
            phoneBookUsers = (List<PhoneBookUser>) phonBookUserRepository.findAll();
        } else {
            phoneBookUsers =  phonBookUserRepository.findAllUser();
        }

        return phoneBookUsers;
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
    @Secured(value = "IS_AUTHENTICATED_FULLY")
    @RequestMapping(value = "/phoneBoockUser/{userName}/data", method = RequestMethod.PUT)
    public HttpEntity<Void> updatePhonBoockUser(@PathVariable(value = "userName") String userName, @RequestBody PhoneBookUser phoneBookUser){
        PhoneBookUser phoneBookUserAux = phonBookUserRepository.findByUserName(userName);

        phoneBookUserAux.setFirstName(phoneBookUser.getFirstName());
        phoneBookUserAux.setLastName(phoneBookUser.getLastName());
        phoneBookUserAux.setMail(phoneBookUser.getMail());
        if(phoneBookUser.getPassword()!=null && !phoneBookUser.getPassword().trim().equals("")){
            phoneBookUserAux.setPassword(passwordEncoder.encode(phoneBookUser.getPassword()));
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
