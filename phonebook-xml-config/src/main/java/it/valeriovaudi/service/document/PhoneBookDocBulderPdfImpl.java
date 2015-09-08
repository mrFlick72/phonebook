package it.valeriovaudi.service.document;

import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import it.valeriovaudi.web.model.Contact;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Valerio on 22/02/2015.
 */
public class PhoneBookDocBulderPdfImpl implements PhoneBookDocBulder {
    private Document document;
    private ByteArrayOutputStream outputStream;

    @Override
    public PhoneBookDocBulder createNewPhoneBookDoc(OutputStream outputStream) throws Exception {
        // step 1
        document = new Document();
        this.outputStream = (ByteArrayOutputStream) outputStream;
        // step 2
        PdfWriter.getInstance(document, outputStream);
        document.open();

        return this;
    }


    @Override
    public PhoneBookDocBulder addPhoneBookTitle(String text) throws Exception{
        Phrase phrase = new Phrase(text);
        document.add(phrase);

        return this;
    }

    @Override
    public PhoneBookDocBulder addPhoneBookTable(List<Contact> contactList) throws Exception {
        PdfPTable pdfPTable = new PdfPTable(3);

        for (Contact contact : contactList) {
            pdfPTable.addCell(contact.getFirstName());
            pdfPTable.addCell(contact.getLastName());
            pdfPTable.addCell(contact.getTelephoneNumber());
            pdfPTable.completeRow();
        }

        document.add(pdfPTable);
        return this;
    }

    @Override
    public byte[] buildDocument() throws IOException {
        document.close();
        return outputStream.toByteArray();
    }


}
