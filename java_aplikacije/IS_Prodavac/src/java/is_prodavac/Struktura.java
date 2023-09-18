/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is_prodavac;

/**
 *
 * @author tijana
 */
public class Struktura {

    private int SifP;
    private int stanjeArtikla;

    public Struktura(int SifP, int stanjeArtikla) {
        this.SifP = SifP;
        this.stanjeArtikla = stanjeArtikla;
    }

    public int getSifP() {
        return SifP;
    }

    public void setSifP(int SifP) {
        this.SifP = SifP;
    }

    public int getStanjeArtikla() {
        return stanjeArtikla;
    }

    public void setStanjeArtikla(int stanjeArtikla) {
        this.stanjeArtikla = stanjeArtikla;
    }

    

}
