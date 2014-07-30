package it.valeriovaudi.web.controller;

import it.valeriovaudi.repository.ContactRepository;
import it.valeriovaudi.web.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Created by Valerio on 24/07/2014.
 */
@Controller
public class ContactController {

    private ContactRepository contactRepository;

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public @ResponseBody List<Contact> getAllpersone(){
        List<Contact> contactList = (List<Contact>) contactRepository.findAll();
        return contactList;
    }

    @RequestMapping(value = "/contact/{contactId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPersona(@PathVariable("contactId") long contactId){
        Contact contact = contactRepository.findOne(contactId);
        return new ResponseEntity<Contact>(contact, HttpStatus.OK);
    }

    @RequestMapping(value = "/contact", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addPersona(@RequestBody Contact contact){
        Contact save = contactRepository.save(contact);
        URI uri = UriComponentsBuilder.fromPath("/contact/{contactId}").buildAndExpand(save.getId()).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);

        return new ResponseEntity<Void>(null, headers, HttpStatus.CREATED);
    }

    @Autowired
    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
}
