package it.valeriovaudi.web.model;

import it.valeriovaudi.security.PhoneBookSecurityRole;

import javax.persistence.*;

/**
 * Created by Valerio on 26/07/2014.
 */
@Entity
@Table
public class PhoneBookUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String userName;

    @Column
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String mail;

    @Column
    @Enumerated
    private PhoneBookSecurityRole securityRole;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public PhoneBookSecurityRole getSecurityRole() {
        return securityRole;
    }

    public void setSecurityRole(PhoneBookSecurityRole securityRole) {
        this.securityRole = securityRole;
    }

    @Override
    public String toString() {
        return "PhoneBookUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + mail + '\'' +
                ", securityRole=" + securityRole +
                '}';
    }
}
