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
@Table(name = "Nalazi")
@NamedQueries({
    @NamedQuery(name = "Nalazi.findAll", query = "SELECT n FROM Nalazi n"),
    @NamedQuery(name = "Nalazi.findByIdN", query = "SELECT n FROM Nalazi n WHERE n.idN = :idN"),
    @NamedQuery(name = "Nalazi.findBySifAr", query = "SELECT n FROM Nalazi n WHERE n.sifAr = :sifAr"),
    @NamedQuery(name = "Nalazi.findBySifPr", query = "SELECT n FROM Nalazi n WHERE n.sifPr = :sifPr"),
    @NamedQuery(name = "Nalazi.findByKolicina", query = "SELECT n FROM Nalazi n WHERE n.kolicina = :kolicina")})
public class Nalazi implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idN")
    private Integer idN;
    @Column(name = "SifAr")
    private Integer sifAr;
    @Column(name = "SifPr")
    private Integer sifPr;
    @Column(name = "Kolicina")
    private Integer kolicina;

    public Nalazi() {
    }

    public Nalazi(Integer idN) {
        this.idN = idN;
    }

    public Integer getIdN() {
        return idN;
    }

    public void setIdN(Integer idN) {
        this.idN = idN;
    }

    public Integer getSifAr() {
        return sifAr;
    }

    public void setSifAr(Integer sifAr) {
        this.sifAr = sifAr;
    }

    public Integer getSifPr() {
        return sifPr;
    }

    public void setSifPr(Integer sifPr) {
        this.sifPr = sifPr;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idN != null ? idN.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nalazi)) {
            return false;
        }
        Nalazi other = (Nalazi) object;
        if ((this.idN == null && other.idN != null) || (this.idN != null && !this.idN.equals(other.idN))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Nalazi[ idN=" + idN + " ]";
    }
    
}
