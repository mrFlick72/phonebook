package it.valeriovaudi.support;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by seminario on 9/13/14.
 */
public class EmbeddedMailServerStarter {

    public static final int SMTP_PORT = 25;
    public static final int IMAP_PORT = 143;
    public static final int POP3_PORT = 110;

    public static final String LOCAL_HOST_IP = "127.0.0.1";

    private GreenMail greenMail;

    @PostConstruct
    public void init(){
        ServerSetup smtpSetup = new ServerSetup(SMTP_PORT,LOCAL_HOST_IP,ServerSetup.PROTOCOL_SMTP);
        ServerSetup imapSetup = new ServerSetup(IMAP_PORT,LOCAL_HOST_IP,ServerSetup.PROTOCOL_IMAP);
        ServerSetup pop3Setup = new ServerSetup(POP3_PORT,LOCAL_HOST_IP,ServerSetup.PROTOCOL_POP3);

        greenMail = new GreenMail(new ServerSetup[] {smtpSetup,imapSetup,pop3Setup});

        greenMail.start();
    }

    public GreenMail getGreenMail() {
        return greenMail;
    }

    @PreDestroy
    public void stop(){
        greenMail.stop();
    }

}
