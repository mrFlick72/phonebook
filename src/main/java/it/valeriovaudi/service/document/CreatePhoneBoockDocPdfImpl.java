package it.valeriovaudi.service.document;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import it.valeriovaudi.web.model.Contact;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Valerio on 14/02/2015.
 */
public class CreatePhoneBoockDocPdfImpl implements CreatePhoneBoockDoc {

    @Override
    public byte[] createPhoneBoockDoc(List<Contact> contactList) {
        byte[] bytes = new byte[0];
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();){
            // step 1
            Document document = new Document();
            // step 2
            PdfWriter.getInstance(document, byteArrayOutputStream);
            // step 3
            document.open();
            // step 4
            StringBuilder stringBuilder = new StringBuilder();

            /*String rowFromat = "First nam: %s, Last name: %s telephne: %s";
            for (Contact contact : contactList) {
                stringBuilder.append(String.format(rowFromat,contact.getFirstName(),contact.getLastName(),contact.getTelephoneNumber()));
                stringBuilder.append("\n");
            }*/

            PdfPTable pdfPTable = new PdfPTable(3);
            for (Contact contact : contactList) {
                pdfPTable.addCell(contact.getFirstName());
                pdfPTable.addCell(contact.getLastName());
                pdfPTable.addCell(contact.getTelephoneNumber());
                pdfPTable.completeRow();
            }
            document.add(pdfPTable);

            document.add(new Paragraph(stringBuilder.toString()));
            // step 5
            document.close();
            bytes = byteArrayOutputStream.toByteArray();

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
            System.out.println("Exception" + e);

        }

        return bytes;
    }
}
