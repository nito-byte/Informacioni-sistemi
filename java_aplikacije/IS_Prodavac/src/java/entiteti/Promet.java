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

/**
 *
 * @author tijana
 */
@Entity
@Table(name = "Promet")
@NamedQueries({
    @NamedQuery(name = "Promet.findAll", query = "SELECT p FROM Promet p"),
    @NamedQuery(name = "Promet.findBySifPromet", query = "SELECT p FROM Promet p WHERE p.sifPromet = :sifPromet"),
    @NamedQuery(name = "Promet.findByBrojProdatih", query = "SELECT p FROM Promet p WHERE p.brojProdatih = :brojProdatih"),
    @NamedQuery(name = "Promet.findByUkupniIznos", query = "SELECT p FROM Promet p WHERE p.ukupniIznos = :ukupniIznos"),
    @NamedQuery(name = "Promet.findBySifP", query = "SELECT p FROM Promet p WHERE p.sifP = :sifP")})
public class Promet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SifPromet")
    private Integer sifPromet;
    @Column(name = "BrojProdatih")
    private Integer brojProdatih;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "UkupniIznos")
    private Double ukupniIznos;
    @Column(name = "SifP")
    private Integer sifP;

    public Promet() {
    }

    public Promet(Integer sifPromet) {
        this.sifPromet = sifPromet;
    }

    public Integer getSifPromet() {
        return sifPromet;
    }

    public void setSifPromet(Integer sifPromet) {
        this.sifPromet = sifPromet;
    }

    public Integer getBrojProdatih() {
        return brojProdatih;
    }

    public void setBrojProdatih(Integer brojProdatih) {
        this.brojProdatih = brojProdatih;
    }

    public Double getUkupniIznos() {
        return ukupniIznos;
    }

    public void setUkupniIznos(Double ukupniIznos) {
        this.ukupniIznos = ukupniIznos;
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
        hash += (sifPromet != null ? sifPromet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promet)) {
            return false;
        }
        Promet other = (Promet) object;
        if ((this.sifPromet == null && other.sifPromet != null) || (this.sifPromet != null && !this.sifPromet.equals(other.sifPromet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Promet[ sifPromet=" + sifPromet + " ]";
    }
    
}
