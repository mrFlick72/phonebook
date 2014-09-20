package it.valeriovaudi.web.controller;

import it.valeriovaudi.repository.ContactRepository;
import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.web.model.Contact;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

/**
 * Created by Valerio on 24/07/2014.
 */
@Controller
public class ContactController {
    private ContactRepository contactRepository;
    private PhonBookUserRepository phonBookUserRepository;

    @Secured(value = "IS_AUTHENTICATED_FULLY")
    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public @ResponseBody List<Contact> getContacts(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Contact> contactList = (List<Contact>) contactRepository.findAllContactByUser(authentication.getName());
        return contactList;
    }

    @Secured(value = "IS_AUTHENTICATED_FULLY")
    @RequestMapping(value = "/contact/{contactId}", method = RequestMethod.GET)
    public ResponseEntity<?> getContact(@PathVariable("contactId") long contactId){
        Contact contact = contactRepository.findOne(contactId);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @Secured(value = "IS_AUTHENTICATED_FULLY")
    @RequestMapping(value = "/contact", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addContact(@RequestBody Contact contact,Principal principal){
        PhoneBookUser phoneBookUser = phonBookUserRepository.findByUserName(principal.getName());
        contact.setPhoneBookUser(phoneBookUser);
        Contact save = contactRepository.save(contact);
        URI uri = UriComponentsBuilder.fromPath("/contact/{contactId}").buildAndExpand(save.getId()).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);

        return new ResponseEntity<>(null, headers, HttpStatus.CREATED);
    }

    @Autowired
    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Autowired
    public void setPhonBookUserRepository(PhonBookUserRepository phonBookUserRepository) {
        this.phonBookUserRepository = phonBookUserRepository;
    }
}
