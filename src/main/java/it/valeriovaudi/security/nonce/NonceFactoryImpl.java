package it.valeriovaudi.security.nonce;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;
import java.util.Random;

/**
 * Created by Valerio on 17/10/2014.
 */
public class NonceFactoryImpl implements NonceFactory {

    private Integer hashIteration;

    /**@param args <p>Are the argument that the algorithm use for generate a nonce. For this implementation i use firstName, lastName, userName and e-mail i use this for create an hash512 </p>*/
    @Override
    public String getNonce(String... args) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (String arg : args) {
            stringBuilder = stringBuilder.append(arg);
        }
        Date toDay = new Date();
        stringBuilder = stringBuilder.append(random.nextLong()).append(toDay.getTime());

        String noncePreHashed = stringBuilder.toString();

        String nonce = DigestUtils.sha512Hex(noncePreHashed);
        for(int i = 0  ; i < hashIteration ; i++){
            nonce  = DigestUtils.sha512Hex(nonce);
        }
        return nonce;
    }

    public void setHashIteration(Integer hashIteration) {
        this.hashIteration = hashIteration;
    }
}
