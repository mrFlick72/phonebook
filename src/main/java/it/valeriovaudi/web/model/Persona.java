package it.valeriovaudi.web.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Valerio-user on 15/07/2014.
 */
@Entity
@Table
public class Persona {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String nome;

    @Column
    private String cognome;

    @Column
    private String telefono;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date nascita;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getNascita() {
        return nascita;
    }

    public void setNascita(Date nascita) {
        this.nascita = nascita;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", telefono='" + telefono + '\'' +
                ", nascita='" + nascita + '\'' +
                '}';
    }
}
