package it.valeriovaudi.controller;

import org.junit.Test;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Valerio on 26/07/2014.
 */
public class IndexControllerTest extends AbstractTestWithSecurityContext {

    @Test
    public void getAllpersoneTest() throws Exception {
        mockMvc.perform(get("/index").
                sessionAttr(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,principal)).
                andExpect(status().isOk()).
                andReturn();
    }

    @Override
    protected Object principalInit() throws Exception {
        return authenticate();
    }
}
