package it.valeriovaudi.service.document;

import it.valeriovaudi.web.model.Contact;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Valerio on 11/02/2015.
 */
public class CreatePhoneBoockDocWordImpl implements CreatePhoneBoockDoc {

    @Override
    public byte[] createPhoneBoockDoc(List<Contact> contactList) {

        byte[] document = new byte[0];
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {
            XWPFDocument doc = new XWPFDocument();
            doc.createParagraph().createRun().setText("Phone Book");

            createTable(doc,contactList);

            doc.write(byteArrayOutputStream);

            document = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return document;
    }

    private void createTable(XWPFDocument doc,List<Contact> contactList){
        int i = 0;
        XWPFTable table = doc.createTable(contactList.size(),3);

        for (Contact contact : contactList) {
            createTableRow(table.getRow(i),contact);
            i++;
        }
    }

    private void createTableRow(XWPFTableRow tableRow ,Contact contact){
        tableRow.getCell(0).setText(contact.getFirstName());
        tableRow.getCell(1).setText(contact.getLastName());
        tableRow.getCell(2).setText(contact.getTelephoneNumber());
    }
}
