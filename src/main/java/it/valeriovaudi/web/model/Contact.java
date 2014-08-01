package it.valeriovaudi.web.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Valerio-user on 15/07/2014.
 */
@Entity
@Table
@NamedQueries(value = {@NamedQuery(name = "Contact.findAllContactByUser",query = "select c from Contact as c where c.phonBookUser.userName=:userName")})
public class Contact {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String telephoneNumber;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date birth;

    @JoinColumn(name = "phonBookUser")
    @OneToOne
    private PhonBookUser phonBookUser;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public PhonBookUser getPhonBookUser() {
        return phonBookUser;
    }

    public void setPhonBookUser(PhonBookUser phonBookUser) {
        this.phonBookUser = phonBookUser;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", birth=" + birth +
                ", phonBookUser=" + phonBookUser +
                '}';
    }
}
