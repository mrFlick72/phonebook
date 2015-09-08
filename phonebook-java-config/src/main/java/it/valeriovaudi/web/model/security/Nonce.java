package it.valeriovaudi.web.model.security;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Valerio on 18/10/2014.
 */
@Entity
@Table
public class Nonce {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(unique = true)
    private String nonce;

    @Column
    private String userName;

    @Column
    private Date start;

    @Column
    private Date stop;

    @Column
    private boolean used;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStop() {
        return stop;
    }

    public void setStop(Date stop) {
        this.stop = stop;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @Override
    public String toString() {
        return "Nonce{" +
                "id=" + id +
                ", nonce='" + nonce + '\'' +
                ", userName='" + userName + '\'' +
                ", start=" + start +
                ", stop=" + stop +
                ", used=" + used +
                '}';
    }
}
