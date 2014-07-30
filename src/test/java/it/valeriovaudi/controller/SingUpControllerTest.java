package it.valeriovaudi.controller;

import it.valeriovaudi.web.model.PhonBookUser;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by Valerio on 30/07/2014.
 */
public class SingUpControllerTest {

//    @Test extends AbstractTest
    public void singUpTest() throws Exception {
        PhonBookUser phonBookUser = new PhonBookUser();

        phonBookUser.setFirstName("Valerio");
        phonBookUser.setLastName("Vaudi");

        phonBookUser.setUserName("valerio.vaudi");
        phonBookUser.setPassword("admin");

        // mockMvc.perform(post("/signup").sessionAttr("signupForm", phonBookUser));
    }
}
