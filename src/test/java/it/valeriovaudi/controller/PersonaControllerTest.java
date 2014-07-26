package it.valeriovaudi.controller;

import it.valeriovaudi.web.model.Persona;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Valerio on 24/07/2014.
 */
public class PersonaControllerTest extends AbstractTest {

    @Test
    public void getAllpersoneTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/persone")).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        logger.info(contentAsString);

        Persona[] personas = objectMapper.readValue(contentAsString, Persona[].class);
        for (Persona persona : personas) {
            logger.info(persona);
        }

        Assert.assertEquals(personas.length,4);
    }

    @Test
    public void getPersonaTest() throws Exception {
        URI uri = UriComponentsBuilder.fromPath("/persona/{personaId}").buildAndExpand(1).toUri();
        MvcResult mvcResult = mockMvc.perform(get(uri)).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        logger.info(contentAsString);


        Persona persona = objectMapper.readValue(contentAsString, Persona.class);
        logger.info(persona);
        Assert.assertNotNull(persona);
    }

    @Test
    public void addPersonaTest() throws Exception {
        Persona persona = new Persona();

        persona.setNome("Valerio Secondo");
        persona.setCognome("Vaudi");
        persona.setTelefono("3392381584");
        persona.setNascita(new Date());

        String location = mockMvc.perform(post("/persona").
                            accept(MediaType.APPLICATION_JSON).
                            contentType(MediaType.APPLICATION_JSON).
                            content(objectMapper.writeValueAsBytes(persona))).
                            andExpect(status().isCreated()).
                            andReturn().
                            getResponse().
                            getHeader("Location");

        logger.info(location);

        MvcResult mvcResult = mockMvc.perform(get(location)).
                                andExpect(status().isOk()).
                                andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Persona personaOut = objectMapper.readValue(contentAsString, Persona.class);

        logger.info(contentAsString);
        logger.info(personaOut);

        Assert.assertEquals(persona.getNome(), personaOut.getNome());
        Assert.assertEquals(persona.getCognome(), personaOut.getCognome());
        Assert.assertEquals(persona.getNascita(), personaOut.getNascita());
        Assert.assertEquals(persona.getTelefono(), personaOut.getTelefono());

    }

}
