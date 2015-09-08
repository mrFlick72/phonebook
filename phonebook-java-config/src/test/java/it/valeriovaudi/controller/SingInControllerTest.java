package it.valeriovaudi.controller;

import org.junit.Assert;
import org.junit.Test;

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
