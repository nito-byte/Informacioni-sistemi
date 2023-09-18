/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author tijana
 */
@Entity
@Table(name = "Spisak")
@NamedQueries({
    @NamedQuery(name = "Spisak.findAll", query = "SELECT s FROM Spisak s"),
    @NamedQuery(name = "Spisak.findByIdSp", query = "SELECT s FROM Spisak s WHERE s.idSp = :idSp"),
    @NamedQuery(name = "Spisak.findByNaziv", query = "SELECT s FROM Spisak s WHERE s.naziv = :naziv"),
    @NamedQuery(name = "Spisak.findBySifra", query = "SELECT s FROM Spisak s WHERE s.sifra = :sifra"),
    @NamedQuery(name = "Spisak.findByTip", query = "SELECT s FROM Spisak s WHERE s.tip = :tip"),
    @NamedQuery(name = "Spisak.findByCena", query = "SELECT s FROM Spisak s WHERE s.cena = :cena"),
    @NamedQuery(name = "Spisak.findByVremeProizvodnje", query = "SELECT s FROM Spisak s WHERE s.vremeProizvodnje = :vremeProizvodnje")})
public class Spisak implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idSp")
    private Integer idSp;
    @Size(max = 45)
    @Column(name = "Naziv")
    private String naziv;
    @Size(max = 45)
    @Column(name = "Sifra")
    private String sifra;
    @Size(max = 45)
    @Column(name = "Tip")
    private String tip;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Cena")
    private Double cena;
    @Size(max = 45)
    @Column(name = "VremeProizvodnje")
    private Integer vremeProizvodnje;

    public Spisak() {
    }

    public Spisak(Integer idSp) {
        this.idSp = idSp;
    }

    public Integer getIdSp() {
        return idSp;
    }

    public void setIdSp(Integer idSp) {
        this.idSp = idSp;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public Integer getVremeProizvodnje() {
        return vremeProizvodnje;
    }

    public void setVremeProizvodnje(Integer vremeProizvodnje) {
        this.vremeProizvodnje = vremeProizvodnje;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSp != null ? idSp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spisak)) {
            return false;
        }
        Spisak other = (Spisak) object;
        if ((this.idSp == null && other.idSp != null) || (this.idSp != null && !this.idSp.equals(other.idSp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Spisak[ idSp=" + idSp + " ]";
    }
    
}
