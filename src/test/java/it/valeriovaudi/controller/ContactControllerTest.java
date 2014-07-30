package it.valeriovaudi.controller;

import it.valeriovaudi.web.model.Contact;
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
public class ContactControllerTest extends AbstractTest {

    @Test
    public void getAllpersoneTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/contacts")).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        logger.info(contentAsString);

        Contact[] contacts = objectMapper.readValue(contentAsString, Contact[].class);
        for (Contact contact : contacts) {
            logger.info(contact);
        }

        Assert.assertEquals(contacts.length,4);
    }

    @Test
    public void getPersonaTest() throws Exception {
        URI uri = UriComponentsBuilder.fromPath("/contact/{contactId}").buildAndExpand(1).toUri();
        MvcResult mvcResult = mockMvc.perform(get(uri)).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        logger.info(contentAsString);


        Contact contact = objectMapper.readValue(contentAsString, Contact.class);
        logger.info(contact);
        Assert.assertNotNull(contact);
    }

    @Test
    public void addPersonaTest() throws Exception {
        Contact contact = new Contact();

        contact.setNome("Valerio Secondo");
        contact.setCognome("Vaudi");
        contact.setTelefono("3392381584");
        contact.setNascita(new Date());

        String location = mockMvc.perform(post("/contact").
                            accept(MediaType.APPLICATION_JSON).
                            contentType(MediaType.APPLICATION_JSON).
                            content(objectMapper.writeValueAsBytes(contact))).
                            andExpect(status().isCreated()).
                            andReturn().
                            getResponse().
                            getHeader("Location");

        logger.info(location);

        MvcResult mvcResult = mockMvc.perform(get(location)).
                                andExpect(status().isOk()).
                                andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Contact contactOut = objectMapper.readValue(contentAsString, Contact.class);

        logger.info(contentAsString);
        logger.info(contactOut);

        Assert.assertEquals(contact.getNome(), contactOut.getNome());
        Assert.assertEquals(contact.getCognome(), contactOut.getCognome());
        Assert.assertEquals(contact.getNascita(), contactOut.getNascita());
        Assert.assertEquals(contact.getTelefono(), contactOut.getTelefono());

    }

}
