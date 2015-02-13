package it.valeriovaudi.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Valerio on 08/02/2015.
 */
@RestController
public class PhoneBoockExporter {

    private JmsTemplate jmsTemplate;
    private Destination sendQueue;

    @RequestMapping("/createPhoneBoocDocumentByMail/{userName}/{contentType}/service")
    public ResponseEntity createPhoneBoocDocumentByMail(@PathVariable String userName,@PathVariable String contentType){
        Map<String,String> message = new HashMap<>();

        message.put("contentType",contentType);
        message.put("userName",userName);

        jmsTemplate.convertAndSend(sendQueue,message);

        return ResponseEntity.ok().build();
    }

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Autowired
    @Qualifier("sendQueue")
    public void setSendQueue(Destination sendQueue) {
        this.sendQueue = sendQueue;
    }
}
