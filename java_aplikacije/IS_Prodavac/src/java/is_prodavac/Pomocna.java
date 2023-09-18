/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is_prodavac;

import java.util.Scanner;

/**
 *
 * @author tijana
 */
public class Pomocna {
    
    
     private static String unosPodatka(Scanner input, int a) {
        while (true) {
            System.out.println("UNOS:");
            try {
                String vr;
                if(a==1){vr = input.nextLine();return vr;}
                if(a==2){int pom = input.nextInt(); vr = Integer.toString(pom); return vr;}
            } catch (NumberFormatException e) {
                System.out.println("Greska pri unosu!");
            }
        }
        
        
    }
    
}
