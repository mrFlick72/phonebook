package it.valeriovaudi.service.document;

import it.valeriovaudi.web.model.Contact;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Valerio on 22/02/2015.
 */
public class PhoneBookDocBulderDocxImpl implements PhoneBookDocBulder{
    private XWPFDocument document;
    private ByteArrayOutputStream outputStream;

    @Override
    public PhoneBookDocBulder createNewPhoneBookDoc(OutputStream outputStream) throws Exception {
        this.outputStream = (ByteArrayOutputStream) outputStream;
        this.document = new XWPFDocument();
        return this;
    }

    @Override
    public PhoneBookDocBulder addPhoneBookTitle(String text) throws Exception {
        document.createParagraph().createRun().setText(text);
        return this;
    }

    @Override
    public PhoneBookDocBulder addPhoneBookTable(List<Contact> contactList) throws Exception {
        int i = 0;
        XWPFTable table = document.createTable(contactList.size(),3);

        for (Contact contact : contactList) {
            table.getRow(i).getCell(0).setText(contact.getFirstName());
            table.getRow(i).getCell(1).setText(contact.getLastName());
            table.getRow(i).getCell(2).setText(contact.getTelephoneNumber());
            i++;
        }
        return this;
    }

    @Override
    public byte[] buildDocument() throws IOException {
        document.write(outputStream);
        return outputStream.toByteArray();
    }


}
