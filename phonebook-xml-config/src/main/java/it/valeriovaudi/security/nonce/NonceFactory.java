package it.valeriovaudi.security.nonce;

/**
 * Created by Valerio on 17/10/2014.
 */
public interface NonceFactory {
    String getNonce(String ... args);
}
