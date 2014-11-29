package it.valeriovaudi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.valeriovaudi.factory.SecurityUserFactory;
import it.valeriovaudi.security.PhoneBookSecurityRole;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Valerio on 01/08/2014.
 */
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:application-context.xml","file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractTestWithSecurityContext {

    protected  static String SEC_CONTEXT_ATTR = HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

    protected static Logger logger = Logger.getLogger(ContactRestServiceTest.class);
    protected Object principal;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;
    @Autowired
    private SecurityUserFactory<PhoneBookUser> securityUserFactory;

    @Autowired
    protected WebApplicationContext wac;
    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper;

    @Before
    public void before() throws Exception {
        objectMapper =  new ObjectMapper();
        this.mockMvc = webAppContextSetup(this.wac)
                .addFilters(this.springSecurityFilterChain).build();
        principal = principalInit();
    }

    protected abstract Object principalInit() throws Exception;


    protected Object authenticate(String userName,String password) throws Exception {
        Object principal = mockMvc.perform(post("/j_spring_security_check").param("j_username", userName).param("j_password", password))
                .andReturn().getRequest().getSession().getAttribute(SEC_CONTEXT_ATTR);
        return principal;
    }

    protected Authentication authenticateForMethod(){
        PhoneBookUser phoneBookUser = new PhoneBookUser();
        phoneBookUser.setPassword("valerio");
        phoneBookUser.setUserName("mrFlickete");
        phoneBookUser.setSecurityRole(PhoneBookSecurityRole.USER);
        Authentication authenticate = authenticationManager.authenticate(securityUserFactory.getAutenticatedUser(phoneBookUser));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return authenticate;
    }

    protected Object authenticate() throws Exception {
        return authenticate("mrFlickete","valerio");
    }
}
