package it.valeriovaudi.service.document;

import com.itextpdf.text.DocumentException;
import it.valeriovaudi.web.model.Contact;

import javax.jms.Message;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Valerio on 08/02/2015.
 *
 * todo beter template for pdf and docx
 */
public interface CreatePhoneBoockDoc {
    byte[] createPhoneBoockDoc(List<Contact> contactList);
}
