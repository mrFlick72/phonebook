package it.valeriovaudi.builder;

import it.valeriovaudi.security.PhoneBookSecurityRole;
import it.valeriovaudi.web.model.PhoneBookUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by Valerio on 22/10/2014.
 * todo da migrare alla parte in xml
 */
@Configurable
public class PhoneBookUserBuilder {
    protected PhoneBookUser phoneBookUser;
    private PasswordEncoder passwordEncoder;

    public static PhoneBookUserBuilder newPhoneBookUserBuilder(){
        PhoneBookUserBuilder phoneBookUserBuilder = new PhoneBookUserBuilder();
        phoneBookUserBuilder.setPhoneBookUser(new PhoneBookUser());
        return phoneBookUserBuilder;
    }

    public static PhoneBookUserBuilder newPhoneBookUserBuilderByPhoneBookUser(PhoneBookUser phoneBookUser){
        PhoneBookUserBuilder phoneBookUserBuilder = new PhoneBookUserBuilder();
        phoneBookUserBuilder.setPhoneBookUser(phoneBookUser);
        return phoneBookUserBuilder;
    }

    @Autowired
    private void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private void setPhoneBookUser(PhoneBookUser phoneBookUser) {
        this.phoneBookUser = phoneBookUser;
    }

    public PhoneBookUserBuilder buildId(Long id){
        this.phoneBookUser.setId(id);
        return this;
    }

    public PhoneBookUserBuilder buildUserName(String userName){
        this.phoneBookUser.setUserName(userName);
        return this;
    }

    public PhoneBookUserBuilder buildPassword(String password){
        this.phoneBookUser.setPassword(passwordEncoder.encode(password));
        return this;
    }

    public PhoneBookUserBuilder buildFirstName(String firstName){
        this.phoneBookUser.setFirstName(firstName);
        return this;
    }

    public PhoneBookUserBuilder buildLastName(String lastName){
        this.phoneBookUser.setLastName(lastName);
        return this;
    }

    public PhoneBookUserBuilder buildMail(String mail){
        this.phoneBookUser.setMail(mail);
        return this;
    }

    public PhoneBookUserBuilder buildSecurityRole(PhoneBookSecurityRole securityRole){
        this.phoneBookUser.setSecurityRole(securityRole);
        return this;
    }

    public PhoneBookUser buildPhoneBookUser(){
        return phoneBookUser;
    }
}
