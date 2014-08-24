package it.valeriovaudi.controller;

import it.valeriovaudi.web.model.PhoneBookUser;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Valerio on 30/07/2014.
 */
public class SingUpControllerTest extends AbstractTest{

    @Test
    public void singUpTest() throws Exception {
        PhoneBookUser phoneBookUser = new PhoneBookUser();

        phoneBookUser.setFirstName("Valerio");
        phoneBookUser.setLastName("Vaudi");

        phoneBookUser.setUserName("valerio.vaudi");
        phoneBookUser.setPassword("admin");

        mockMvc.perform(post("/signup").
                        param("userName", phoneBookUser.getUserName()).
                        param("password", phoneBookUser.getPassword()).
                        param("firstName", phoneBookUser.getFirstName()).
                        param("lastName", phoneBookUser.getLastName())).
                andExpect(status().isFound()).
                andExpect(redirectedUrl("/index"));
    }
}
