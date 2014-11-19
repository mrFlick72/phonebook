package it.valeriovaudi.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import it.valeriovaudi.builder.PhoneBookUserBuilder;
import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.web.model.Contact;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by Valerio on 19/11/2014.
 */
public class PhoneBoockUserRestTest extends AbstractTestWithSecurityContext {

    @Autowired
    PhoneBookUserBuilder phoneBookUserBuilder;

    @Autowired
    PhonBookUserRepository phonBookUserRepository;

    @Test
    public void updatePhonBoockUserTest() throws Exception {

        PhoneBookUser mrFlickete = phonBookUserRepository.findByUserName("mrFlickete");
        mrFlickete.setLastName("Di Mauro");

        mockMvc.perform(put("/phoneBoockUser/mrFlickete").
                sessionAttr(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, principal).
                contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsBytes(mrFlickete))).
                            andExpect(status().
                                    isNoContent());

        MvcResult mvcResult = mockMvc.perform(get("/phoneBoockUser/mrFlickete").
                                        sessionAttr(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, principal)).
                                        andReturn();


        String contentAsString = mvcResult.getResponse().getContentAsString();
        PhoneBookUser phoneBookUser = objectMapper.readValue(contentAsString, PhoneBookUser.class);
        logger.info(phoneBookUser);
    }

    @Override
    protected Object principalInit() throws Exception {
        return authenticate();
    }
}
