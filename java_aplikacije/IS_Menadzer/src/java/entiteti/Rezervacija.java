/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author tijana
 */
@Entity
@Table(name = "Rezervacija")
@NamedQueries({
    @NamedQuery(name = "Rezervacija.findAll", query = "SELECT r FROM Rezervacija r"),
    @NamedQuery(name = "Rezervacija.findBySifRez", query = "SELECT r FROM Rezervacija r WHERE r.sifRez = :sifRez"),
    @NamedQuery(name = "Rezervacija.findByIme", query = "SELECT r FROM Rezervacija r WHERE r.ime = :ime"),
    @NamedQuery(name = "Rezervacija.findByPrezime", query = "SELECT r FROM Rezervacija r WHERE r.prezime = :prezime"),
    @NamedQuery(name = "Rezervacija.findByStatus", query = "SELECT r FROM Rezervacija r WHERE r.status = :status"),
    @NamedQuery(name = "Rezervacija.findByVremePrispeca", query = "SELECT r FROM Rezervacija r WHERE r.vremePrispeca = :vremePrispeca"),
    @NamedQuery(name = "Rezervacija.findBySifA", query = "SELECT r FROM Rezervacija r WHERE r.sifA = :sifA"),
    @NamedQuery(name = "Rezervacija.findBySifP", query = "SELECT r FROM Rezervacija r WHERE r.sifP = :sifP")})
public class Rezervacija implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SifRez")
    private Integer sifRez;
    @Size(max = 45)
    @Column(name = "Ime")
    private String ime;
    @Size(max = 45)
    @Column(name = "Prezime")
    private String prezime;
    @Size(max = 45)
    @Column(name = "Status")
    private String status;
    @Column(name = "VremePrispeca")
    @Temporal(TemporalType.DATE)
    private Date vremePrispeca;
    @Column(name = "SifA")
    private Integer sifA;
    @Column(name = "SifP")
    private Integer sifP;

    public Rezervacija() {
    }

    public Rezervacija(Integer sifRez) {
        this.sifRez = sifRez;
    }

    public Integer getSifRez() {
        return sifRez;
    }

    public void setSifRez(Integer sifRez) {
        this.sifRez = sifRez;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getVremePrispeca() {
        return vremePrispeca;
    }

    public void setVremePrispeca(Date vremePrispeca) {
        this.vremePrispeca = vremePrispeca;
    }

    public Integer getSifA() {
        return sifA;
    }

    public void setSifA(Integer sifA) {
        this.sifA = sifA;
    }

    public Integer getSifP() {
        return sifP;
    }

    public void setSifP(Integer sifP) {
        this.sifP = sifP;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sifRez != null ? sifRez.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rezervacija)) {
            return false;
        }
        Rezervacija other = (Rezervacija) object;
        if ((this.sifRez == null && other.sifRez != null) || (this.sifRez != null && !this.sifRez.equals(other.sifRez))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Rezervacija[ sifRez=" + sifRez + " ]";
    }
    
}
