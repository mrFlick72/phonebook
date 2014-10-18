package it.valeriovaudi.resetpassword;

import it.valeriovaudi.security.nonce.NonceFactoryImpl;
import org.junit.Test;

/**
 * Created by Valerio on 17/10/2014.
 */
public class ResetPasswordTest {

    @Test
    public void test(){

        NonceFactoryImpl createNonce = new NonceFactoryImpl();
        createNonce.setHashIteration(1000);
        for(int i = 0 ; i < 10000L ; i++){
            String nunce = createNonce.getNonce("Valerio", "Vaudi", "valerio.vaudi", "valerio.vaudi@gmail.com");
            System.out.println(nunce);
        }

    }
}
