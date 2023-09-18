/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package artikal;

import java.io.Serializable;

/**
 *
 * @author tijana
 */
public class Artikal implements Serializable{
    
    private String sifra;
    private int kolicina;

    public Artikal(String sifra, int kolicina) {
        this.sifra = sifra;
        this.kolicina = kolicina;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

   
    
    
}
