package it.valeriovaudi.controller;

import it.valeriovaudi.web.model.Contact;
import it.valeriovaudi.web.rest.ContactRestService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.annotation.DirtiesContext;
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
public class ContactRestServiceTest extends AbstractTestWithSecurityContext {

    @Test
    public void getAllpersoneTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/contact").
                                    sessionAttr(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,principal)).
                                    andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        logger.info(contentAsString);

        Contact[] contacts = objectMapper.readValue(contentAsString, Contact[].class);
        for (Contact contact : contacts) {
            logger.info(contact);
        }

        Assert.assertEquals(contacts.length,3);
    }

    @Test
    public void getPersonaTest() throws Exception {
        URI uri = UriComponentsBuilder.fromPath("/contact/{contactId}").buildAndExpand(1).toUri();
        MvcResult mvcResult = mockMvc.perform(get(uri).
                                sessionAttr(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,principal)).
                                    andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        logger.info(contentAsString);


        Contact contact = objectMapper.readValue(contentAsString, Contact.class);
        logger.info(contact);
        Assert.assertNotNull(contact);
    }

    @Test
    @DirtiesContext
    public void addPersonaTest() throws Exception {
        Contact contact = new Contact();

        contact.setFirstName("Valerio Secondo");
        contact.setLastName("Vaudi");
        contact.setTelephoneNumber("3392381584");
        contact.setBirth(new Date());

        String location = mockMvc.perform(post("/contact").
                            accept(MediaType.APPLICATION_JSON).
                            contentType(MediaType.APPLICATION_JSON).
                            sessionAttr(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, principal).
                            content(objectMapper.writeValueAsBytes(contact))).
                            andExpect(status().isCreated()).
                            andReturn().
                            getResponse().
                            getHeader("Location");

        logger.info(location);

        MvcResult mvcResult = mockMvc.perform(get(location).
                        sessionAttr(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, principal)).
                                andExpect(status().isOk()).
                                andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Contact contactOut = objectMapper.readValue(contentAsString, Contact.class);

        logger.info(contentAsString);
        logger.info(contactOut);

        Assert.assertEquals(contact.getFirstName(), contactOut.getFirstName());
        Assert.assertEquals(contact.getLastName(), contactOut.getLastName());
        Assert.assertEquals(contact.getBirth(), contactOut.getBirth());
        Assert.assertEquals(contact.getTelephoneNumber(), contactOut.getTelephoneNumber());
    }

    @Override
    protected Object principalInit() throws Exception {return authenticate();}
}
