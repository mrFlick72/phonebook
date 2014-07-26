package it.valeriovaudi.web.controller;

import it.valeriovaudi.repository.PersonaRepository;
import it.valeriovaudi.web.model.Persona;
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
public class PersonaController {

    private PersonaRepository personaRepository;

    @RequestMapping(value = "/persone", method = RequestMethod.GET)
    public @ResponseBody List<Persona> getAllpersone(){
        List<Persona> personaList = (List<Persona>) personaRepository.findAll();
        return personaList;
    }

    @RequestMapping(value = "/persona/{personaId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPersona(@PathVariable("personaId") long personaId){
        Persona persona = personaRepository.findOne(personaId);
        return new ResponseEntity<Persona>(persona, HttpStatus.OK);
    }

    @RequestMapping(value = "/persona", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addPersona(@RequestBody Persona persona){
        Persona save = personaRepository.save(persona);
        URI uri = UriComponentsBuilder.fromPath("/persona/{personaId}").buildAndExpand(save.getId()).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);

        return new ResponseEntity<Void>(null, headers, HttpStatus.CREATED);
    }

    @Autowired
    public void setPersonaRepository(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }
}
