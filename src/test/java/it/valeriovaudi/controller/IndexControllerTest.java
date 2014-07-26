package it.valeriovaudi.controller;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Valerio on 26/07/2014.
 */
public class IndexControllerTest extends AbstractTest {

    @Test
    public void getAllpersoneTest() throws Exception {
        mockMvc.perform(get("/index")).
                andExpect(status().isOk()).
                andReturn();
    }

}
