package it.valeriovaudi.integration.trasformer;

import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Transformer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by Valerio on 13/02/2015.
 */

/*
Just another mode for implement a message trasformer in spring integration

    public Message<MimeMessage> trasformMessage(Message message) throws MessagingException {
            MessageHeaders headers = message.getHeaders();
            Object payload = message.getPayload();
            PhoneBookUser phoneBookUser = (PhoneBookUser) headers.get("user");
            String contentType = (String) headers.get("contentType");

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMultipart mimeMultipart = new MimeMultipart();

            mimeMultipart.addBodyPart(getText(phoneBookUser));
            mimeMultipart.addBodyPart(getFile(payload,contentType));

            mimeMessage.setContent(mimeMultipart);
            Message<MimeMessage> build = MessageBuilder.
                    withPayload(mimeMessage).
                    copyHeadersIfAbsent(message.getHeaders()).
                    build();

            return build;
        }
    */
@MessageEndpoint
public class MailMessageTrasformer {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${mail.createphoneBookDocument.mwessageSkeleton}")
    private String mailTesxtSkeleton;
    @Value("${mail.fileName}")
    private String fimeName;

    public void setFimeName(String fimeName) {
        this.fimeName = fimeName;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void setMailTesxtSkeleton(String mailTesxtSkeleton) {
        this.mailTesxtSkeleton = mailTesxtSkeleton;
    }

    @Transformer
    public MimeMessage trasformMessage(@Header("contentType") String contentType,
                                       @Header("user") PhoneBookUser phoneBookUser,
                                       @Payload Object payload) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMultipart mimeMultipart = new MimeMultipart();

        mimeMultipart.addBodyPart(getText(phoneBookUser));
        mimeMultipart.addBodyPart(getFile(payload,contentType));

        mimeMessage.setContent(mimeMultipart);
        return mimeMessage;
    }

    private MimeBodyPart getText(PhoneBookUser phoneBookUser) throws MessagingException {
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setText(String.format(mailTesxtSkeleton,phoneBookUser.getFirstName()));

        return mimeBodyPart;
    }

    private MimeBodyPart getFile(Object payload,String contentType) throws MessagingException {
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(payload,"application/"+contentType);
        mimeBodyPart.setFileName(fimeName + contentType);

        return mimeBodyPart;
    }
}
