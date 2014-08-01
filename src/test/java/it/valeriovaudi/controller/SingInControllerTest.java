package it.valeriovaudi.controller;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.servlet.http.HttpSession;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

/**
 * Created by Valerio on 01/08/2014.
 */
public class SingInControllerTest extends AbstractTestWithSecurityContext {

    @Test
    public void userAuthenticates() throws Exception {
        Assert.assertNotNull(authenticate());
    }

    @Test
    public void userAuthenticationFails() throws Exception {
        Assert.assertNull(authenticate("admin","admin123"));
    }

    @Override
    protected Object principalInit() throws Exception {
        return null;
    }
}
