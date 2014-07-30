package it.valeriovaudi.controller;

import it.valeriovaudi.web.model.PhonBookUser;
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
        PhonBookUser phonBookUser = new PhonBookUser();

        phonBookUser.setFirstName("Valerio");
        phonBookUser.setLastName("Vaudi");

        phonBookUser.setUserName("valerio.vaudi");
        phonBookUser.setPassword("admin");

        mockMvc.perform(post("/signup").
                        param("userName",phonBookUser.getUserName()).
                        param("password",phonBookUser.getPassword()).
                        param("firstName",phonBookUser.getFirstName()).
                        param("lastName",phonBookUser.getLastName())).
                andExpect(status().isFound()).
                andExpect(redirectedUrl("/index"));
    }
}
