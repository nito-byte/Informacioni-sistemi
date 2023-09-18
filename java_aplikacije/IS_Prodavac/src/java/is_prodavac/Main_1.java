/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is_prodavac;

import java.util.Scanner;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 *
 * @author tijana
 */
public class Main_1 {
    
    private static String artikal;
    private static int kolicina;
    
    private static String ime;
    private static String prezime;

    /**
     * @param args the command line arguments
     */
    @Resource(lookup = "jms/ConnectionFactory")
    private static ConnectionFactory connectionFactory;

    /*  kasnije odluciti sta cu tacno korisiti, da li cu da koristim Topic
     ili cu da koristim Queue. To je jos uvek rano da sada odredim, zato 
     cu to da ostavim za kasnije.
     */
    @Resource(lookup = "jms/Queue")
    private static Queue queue;

    @Resource(lookup = "jms/Topic")
    private static Topic topic;



   

    public static void main(String[] args) {

        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();       // jer ce slati poruke menadzeru
        JMSConsumer consumer = context.createConsumer(queue);  // jer ce dohvatati poruke od menadzera

        /*  treba da imam na ulazu samo dva koraka, a tek onda 
            kad udjem po kupovina, onda mogu da imam jos dva dodatna koraka
        */
        System.out.println("Molimo Vas da unesete zeljeni izbor: \n"
                + "  1) Kupovina artikla\n"
                + "  2) Rezervacija artikla\n");

        int izbor = 0;
        Scanner input = new Scanner(System.in);
        izbor = input(input);

        switch (izbor) {
            case 1:
                System.out.println("Koji artikal zelite (tip/naziv): ");
                artikal = input.nextLine();
                System.out.println("Kolicina: ");
                kolicina = input.nextInt();
                
                kupovina(input, artikal, kolicina);   //ovaj metod treba da implementiram
                
                break;
            case 2:
                System.out.println("Informacije o rezervaciji");
                System.out.println("Unesite ime: ");
                ime = input.nextLine();
                System.out.println("Unesite prezime: ");
                prezime = input.nextLine();
                break;
            
        }
        input.close();

    }
    
    
    
        private static int input(Scanner input) {
        while (true) {
            // Scanner input = new Scanner(System.in);
            System.out.println("IZBOR:");
            try {
                String vr = input.nextLine();
                int vrednost = Integer.parseInt(vr);
                if (vrednost > 3) {
                    throw new NumberFormatException();
                }
                //input.close();
                return vrednost;
            } catch (NumberFormatException e) {
                System.out.println("Greska pri unosu!");
                //input.nextLine();
            }
        }
    }
    
    
    
    
    /*  ovo je funkcija u kojoj treba da za odgovarajuci artikal 
        i za odgovarajucu kolicinu izlistam iz baze podataka sve 
        mogucnosti koje postoje
    */
    private static void kupovina(Scanner input, String artikal, int kolicina) {
        
      
        // Pretraga artikla
            System.out.println("PRETRAGA ARTIKLA");
            System.out.println("****************\n");

                /*  ovde sada treba da se prikaze lista svih artikala koji 
                 zadovoljavaju zadati kriterijum pretrage
                 */
            
               
            
            
                /* ako nema ovde nikakvih rezultata onda treba da idem na metod 
                da li zelite pretragu ostalih prodavnica. Za ovo mogu da napravim
                i neki poseban metod.
                */
        
        
            System.out.println("Provera stanja (DA/NE)?");
            String provera = input.nextLine();
            
            if(provera.equals("DA")){
                /*ako se zeli provera stanja pakovanja, onda se salje 
                poruka menadzeru. To ce da mi bude jedna objektna poruka
                koja ce da mi se posalje menazderu. On ce onda nakon
                provere da mi posalje ovde poruku. To znaci da ce ovo da 
                bude i Producer i Consumer.
                */
                
                
            }if(provera.equals("NE")){
                
                
            }
            
            System.out.println("Trenutno stanje artikla je: ");
            
            /*ovde sada treba da uradim potvrdu kupovine. Ako kupac potvrdi sada
            kupovinu, onda ja kao prodavac  treba da uzmem i da izmenim trenutno 
            stanje artikla u prodavnici. Znaci tu ce da mi bude neki rad sa bazom.
            */
        
    }
    
    

}
