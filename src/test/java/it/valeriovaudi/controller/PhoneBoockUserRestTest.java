package it.valeriovaudi.controller;

import it.valeriovaudi.repository.PhonBookUserRepository;
import it.valeriovaudi.web.model.PhoneBookUser;
import it.valeriovaudi.web.rest.PhoneBookUserRestService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Valerio on 19/11/2014.
 */
public class PhoneBoockUserRestTest extends AbstractTestWithSecurityContext {


    private static final String LAST_NAME = "Di Mauro";
    private static final String TEST_USER_NAME = "jhoan.maggio";
    private static final String TEST_PASSWORD = "jhoan";

    @Autowired
    private PhonBookUserRepository phonBookUserRepository;
    @Autowired
    private PhoneBookUserRestService phoneBookUserRestService;

    @Test
    public void updatePhonBoockUserTest() throws Exception {

        PhoneBookUser mrFlickete = phonBookUserRepository.findByUserName("jhoan.maggio");
        mrFlickete.setLastName(LAST_NAME);

        UriComponents uriComponents = UriComponentsBuilder.fromPath("/phoneBoockUser/{userName}/data").buildAndExpand(TEST_USER_NAME).encode();

        mockMvc.perform(put(uriComponents.toUri()).
                sessionAttr(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, principal).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsBytes(mrFlickete))).
                andExpect(status().isNoContent());

        MvcResult mvcResult = mockMvc.perform(get(uriComponents.toUri()).
                                        sessionAttr(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, principal)).
                                        andReturn();


        String contentAsString = mvcResult.getResponse().getContentAsString();
        PhoneBookUser phoneBookUser = objectMapper.readValue(contentAsString, PhoneBookUser.class);

        logger.info(phoneBookUser);
        Assert.assertEquals(LAST_NAME,phoneBookUser.getLastName());
    }

    @Test
    @DirtiesContext
    public void updatePhonBoockUserTestByControllerClass() throws Exception {
        authenticateForMethod();

        PhoneBookUser mrFlickete = phonBookUserRepository.findByUserName("jhoan.maggio");
        mrFlickete.setLastName(LAST_NAME);

        phoneBookUserRestService.updatePhonBoockUser("jhoan.maggio",mrFlickete);

        logger.info(phonBookUserRepository.findByUserName("jhoan.maggio"));
    }

    @Override
    protected Object principalInit() throws Exception {
        return authenticate(TEST_USER_NAME,TEST_PASSWORD);
    }
}
