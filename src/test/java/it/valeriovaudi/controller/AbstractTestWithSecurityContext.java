package it.valeriovaudi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Valerio on 01/08/2014.
 */
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:application-context.xml","file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AbstractTestWithSecurityContext {

    protected  static String SEC_CONTEXT_ATTR = HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

    protected static Logger logger = Logger.getLogger(ContactControllerTest.class);
    protected Object principal;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    protected WebApplicationContext wac;
    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper;

    @Before
    public void before() throws Exception {
        objectMapper =  new ObjectMapper();
        this.mockMvc = webAppContextSetup(this.wac)
                .addFilters(this.springSecurityFilterChain).build();
        principal = authenticate();
    }

    protected Object authenticate() throws Exception {
        Object principal = mockMvc.perform(post("/j_spring_security_check").param("j_username", "admin").param("j_password", "admin"))
                .andExpect(new ResultMatcher() {
                    public void match(MvcResult mvcResult) throws Exception {
                        HttpSession session = mvcResult.getRequest().getSession();
                        SecurityContext securityContext = (SecurityContext) session.getAttribute(SEC_CONTEXT_ATTR);
                        Assert.assertNotNull(securityContext);
                    }
                })
                .andReturn().getRequest().getSession().getAttribute(SEC_CONTEXT_ATTR);
        return principal;
    }
}
