package it.valeriovaudi.config.appconfig;

import it.valeriovaudi.service.document.CreatePhoneBoockDoc;
import it.valeriovaudi.service.document.CreatePhoneBoockDocImpl;
import it.valeriovaudi.service.document.PhoneBookDocBulderDocxImpl;
import it.valeriovaudi.service.document.PhoneBookDocBulderPdfImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Valerio on 29/03/2015.
 *     <bean id="pdfCreatePhoneBoockDoc"
             class="it.valeriovaudi.service.document.CreatePhoneBoockDocImpl"
             p:phoneBookDocBulder="#{new it.valeriovaudi.service.document.PhoneBookDocBulderPdfImpl()}"/>

        <bean id="wordCreatePhoneBoockDoc"
             class="it.valeriovaudi.service.document.CreatePhoneBoockDocImpl"
             p:phoneBookDocBulder="#{new it.valeriovaudi.service.document.PhoneBookDocBulderDocxImpl()}"/>
 */
@Configuration
public class ServiceContext {

    @Bean
    public CreatePhoneBoockDoc pdfCreatePhoneBoockDoc(){
        CreatePhoneBoockDocImpl createPhoneBoockDoc = new CreatePhoneBoockDocImpl();
        createPhoneBoockDoc.setPhoneBookDocBulder(new PhoneBookDocBulderPdfImpl());

        return createPhoneBoockDoc;
    }

    @Bean
    public CreatePhoneBoockDoc wordCreatePhoneBoockDoc(){
        CreatePhoneBoockDocImpl createPhoneBoockDoc = new CreatePhoneBoockDocImpl();
        createPhoneBoockDoc.setPhoneBookDocBulder(new PhoneBookDocBulderDocxImpl());

        return createPhoneBoockDoc;
    }
}
