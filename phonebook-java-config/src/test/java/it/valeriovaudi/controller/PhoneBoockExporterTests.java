package it.valeriovaudi.controller;

import com.icegreen.greenmail.util.GreenMail;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.UriComponentsBuilder;

import javax.mail.internet.MimeMultipart;
import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Created by Valerio on 08/02/2015.
 */
public class PhoneBoockExporterTests extends AbstractTestWithSecurityContext {

    @Value("#{embeddedMailServerStarter.greenMail}")
    GreenMail greenMail;

    @Test
    @DirtiesContext
    public void createPhoneBookWordDocumentByMailTest() throws Exception {
        URI uri = UriComponentsBuilder.fromPath("/createPhoneBoocDocumentByMail/{userName}/{contentType}/service").buildAndExpand("mrFlickete","docx").toUri();

        mockMvc.perform(get(uri).
                sessionAttr(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, principal));

        greenMail.waitForIncomingEmail(1);
        boolean partSize = ((MimeMultipart) greenMail.getReceivedMessages()[0].getContent()).getBodyPart(0).getSize() > 0;
        Assert.assertTrue(partSize);

    }

    @Test
    @DirtiesContext
    public void createPhoneBookPdfDocumentByMailTest() throws Exception {
        URI uri = UriComponentsBuilder.fromPath("/createPhoneBoocDocumentByMail/{userName}/{contentType}/service").buildAndExpand("mrFlickete","pdf").toUri();

        mockMvc.perform(get(uri).
                sessionAttr(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, principal));

        greenMail.waitForIncomingEmail(1);
        boolean partSize = ((MimeMultipart) greenMail.getReceivedMessages()[0].getContent()).getBodyPart(0).getSize() > 0;
        Assert.assertTrue(partSize);
    }

    @Override
    protected Object principalInit() throws Exception {
        return authenticate();
    }
}
