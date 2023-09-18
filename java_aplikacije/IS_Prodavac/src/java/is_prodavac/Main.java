/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is_prodavac;

import entiteti.Artikal;
import entiteti.Nalazi;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javax.persistence.Query;

/**
 *
 * @author tijana
 */
public class Main {

    private static String artikal;
    private static int kolicina;

    private static String ime;
    private static String prezime;
    private static String jmbg;
    private static int idC;
    private static String stanje;
    private static JMSConsumer consumer3;
    private static int idA;

    private static int izbor1;

    /**
     * @param args the command line arguments
     */
    @Resource(lookup = "jms/ConnectionFactory2")
    private static ConnectionFactory connectionFactory2;
    
    @Resource(lookup = "jms/ConnectionFactory3")
    private static ConnectionFactory connectionFactory3;

    /*  kasnije odluciti sta cu tacno korisiti, da li cu da koristim Topic
     ili cu da koristim Queue. To je jos uvek rano da sada odredim, zato 
     cu to da ostavim za kasnije.
     */
    @Resource(lookup = "jms/Queue")
    private static Queue queue;

    @Resource(lookup = "jms/Topic2")
    private static Topic topic2;
    
    @Resource(lookup = "jms/Topic3")
    private static Topic topic3;

    public static void main(String[] args) throws JMSException {

        JMSContext context = connectionFactory2.createContext();
        JMSProducer producer = context.createProducer();       // jer ce slati poruke menadzeru
        JMSConsumer consumer = context.createConsumer(queue);  // jer ce dohvatati poruke od menadzera

        JMSContext context3 = connectionFactory3.createContext();
        consumer3 = context3.createConsumer(topic3);
        
        
        
        
        
        
        
        idC = Integer.parseInt(args[0]);
        
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
                System.out.println("Unesite JMBG: ");
                jmbg = input.nextLine();
                
                podizanjeRezervacije(input,ime, prezime, jmbg); 
                
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
    private static void kupovina(Scanner input, String artikal, int kolicina) throws JMSException {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_ProdavacPU");
        EntityManager em = emf.createEntityManager(); 

        System.out.println("PRETRAGA ARTIKLA");
        System.out.println("****************\n");

        //(1)    // SELECT nad tabelom Artikal gde se vrsi pretraga
        // po Tipu i po Nazivu
        
         Query query = em.createQuery("   SELECT a.sifA, a.naziv, a.tip "
                                       + " FROM Artikal a "
                                       + " WHERE a.tip = :artikal"
                                        + " OR a.naziv = :artikal ");
            query.setParameter("artikal", artikal);
            List<Object> s =  query.getResultList();
            for(Object object : s){
                Object[] oArray = (Object[])object;
                System.out.println(oArray[0]+ " "+ oArray[1]+ " "+ oArray[2]);
            }
        
        
        
        
        
        
        
        
        
        
        
        //(2)    // ispis gornjeg printf-a 
        System.out.println("Izaberite artikal iz liste dostupnih: ");
        
       
        int option = input.nextInt();
        input.nextLine();  // Consume newline left-over
        String novi_artikal = input.nextLine();
        
        int option1 = input.nextInt();
        input.nextLine();  // Consume newline left-over
        

        System.out.println("Provera stanja (DA/NE)?");
        String provera = input.nextLine();
        


        
        idA = Integer.parseInt(novi_artikal);

        if (provera.equals("DA")) {
        //(3)   // Saljem jednu objektnu poruku menadzeru, ali 
            // samo menadzeru sa istim ID-jem.
            // koristim restrikciju
            // koristim Topic

            
            JMSContext context2 = connectionFactory2.createContext();
            JMSProducer producer2 = context2.createProducer();
            
            TextMessage textMessage = context2.createTextMessage();
            textMessage.setIntProperty("idC", idC);
 //           textMessage.setText(String.valueOf(idC));
            producer2.send(topic2,textMessage);
            System.out.println("Poslata je poruka!");
            
            
            
            
            
            
            
            
            
            
            //(4)   // Menadzer ce da mi posalje ovde svoj odgovor
            // menadzer mi vraca svoje stanje kroz Topic
        
            
        
        while(true){
         Message message2 = consumer3.receive();
         int s2 = message2.getIntProperty("idC");
         System.out.println("Ovo je idC " + s2);
        
         String s1 = null;
         if(idC == s2){
            if(message2 instanceof TextMessage){
                stanje = ((TextMessage)message2).getText();
            }
         }
         System.out.println("Stanje:" + stanje);
         break;
        }
         
        
        
         
         
         
         
        /*proveri da li ti odgovara stanje
         pozivam metodu koja ce da mi proveri da li postoji 
         u tabeli Nalazi_Se ono sto hocu da kupim*/
        int postoji = 0;
        postoji = NalaziSeMojaProdavnica(idA, idC, kolicina);

        if (postoji == 1) {
            System.out.println("Postoji u ovoj prodavnici!");
            //mislim da ce ovde da mi treba i ID prodavnice da se salje
            int pom = potvrdaKupovine(input, idA, kolicina);
            if (pom == 0) {
                return;
            }

        } else if (postoji == 0) {
            //da li zelite pretragu ostalih prodavnica 
            System.out.println("Da li zelite pretragu ostalih prodavnica: \n"
                    + "  1) DA\n"
                    + "  2) NE\n");

            int izb = 0;
            izb = input(input);

            if (izb == 1) {

                ArrayList<Integer> ostaleProd = NalaziSeOstaleProdavnice(novi_artikal, kolicina);

                System.out.println("Provera stanja (DA/NE)?");
                String pr = input.nextLine();

                if (pr.equals("DA")) {
        //(8)   // Saljem jednu objektnu poruku menadzeru, ali 
                    // svim ostalim menadzerima
                    // koristim restrikciju
                    // koristim Topic

        //(9)   // Menadzeri ce da mi lupaju stanja
                // menadzeri mi vracaju ovde svoje odgovore kroz Topic
                   
                    //ovde cu sada da na osnovu onoga sto sam dobila iz Topica
                    //da napravim ovaj niz u for petlji
                    Struktura[] arr = new Struktura[100]; 
                    
                    
                  //  arr[0] = new Employee("Peter", 100); 
                  //  arr[1] = new Employee("Mary", 90);

                }

            }
        }

    
        
        
        
        
        
        
        
        
        
        
        
        
         
        }else{
         
         
         
         
         
        /*proveri da li ti odgovara stanje
         pozivam metodu koja ce da mi proveri da li postoji 
         u tabeli Nalazi_Se ono sto hocu da kupim*/
        int postoji = 0;
        postoji = NalaziSeMojaProdavnica(idA, idC, kolicina);

        if (postoji == 1) {
             System.out.println("Postoji u ovoj prodavnici!");
            //mislim da ce ovde da mi treba i ID prodavnice da se salje
            int pom = potvrdaKupovine(input, idA, kolicina);
            if (pom == 0) {
                return;
            }

        } else if (postoji == 0) {
            //da li zelite pretragu ostalih prodavnica 
            System.out.println("Da li zelite pretragu ostalih prodavnica: \n"
                    + "  1) DA\n"
                    + "  2) NE\n");

            int izb = 0;
            izb = input(input);

            if (izb == 1) {

                ArrayList<Integer> ostaleProd = NalaziSeOstaleProdavnice(novi_artikal, kolicina);

                System.out.println("Provera stanja (DA/NE)?");
                String pr = input.nextLine();

                if (pr.equals("DA")) {
        //(8)   // Saljem jednu objektnu poruku menadzeru, ali 
                    // svim ostalim menadzerima
                    // koristim restrikciju
                    // koristim Topic

        //(9)   // Menadzeri ce da mi lupaju stanja
                // menadzeri mi vracaju ovde svoje odgovore kroz Topic
                   
                    //ovde cu sada da na osnovu onoga sto sam dobila iz Topica
                    //da napravim ovaj niz u for petlji
                    Struktura[] arr = new Struktura[100]; 
                    
                    
                  //  arr[0] = new Employee("Peter", 100); 
                  //  arr[1] = new Employee("Mary", 90);

                }

            }
        }

    }}

    private static int NalaziSeMojaProdavnica(int idA, int idC, int kolicina) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_ProdavacPU");
        EntityManager em = emf.createEntityManager(); 

        //(5)   //proverim da li se nalazi u mojoj prodavnici
        //SELECT nad tabelom NalaziSe
        //vracam int kao da/ne
        
        
        System.out.println("DA LI IMA U OVOJ PRODAVNICI");
        System.out.println("***************************\n");

        
        
         Query query = em.createQuery("   SELECT n "
                                       + " FROM Nalazi n "
                                       + " WHERE n.sifAr = :idA"
                                        +" AND n.sifPr = :idC"
                                        + "  AND n.kolicina > :kolicina");
            query.setParameter("idA", idA);
            query.setParameter("idC", idC);
            query.setParameter("kolicina", kolicina);
            Nalazi n =  (Nalazi)query.getSingleResult();
            
            if (n!= null){
                return 1;
            }else{
                return 0;
            }
        
        
    }

    private static int potvrdaKupovine(Scanner input, int idA, int kolicina) {

        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_ProdavacPU");
        EntityManager em = emf.createEntityManager(); 
        
        System.out.println("Potvrdjujete li kupovinu: \n"
                + "  1) DA\n"
                + "  2) NE\n");

        izbor1 = 0;
        izbor1 = input(input);

        if (izbor1 == 1) {
            System.out.println("idA: "+idA+ "idC: "+idC);
            Query query1 = em.createQuery("   SELECT n "
                                       + " FROM Nalazi n "
                                       + " WHERE n.sifAr = :idA"
                                        +" AND n.sifPr = :idC");
            query1.setParameter("idA", idA);
            query1.setParameter("idC", idC);
            Nalazi n =  (Nalazi)query1.getSingleResult();
            int kol = n.getKolicina();
            
            
            
           
            
            
            //(6)   // UPDATE tabelu NalaziSe
            // UPDATE tabelu Promet
            em.getTransaction().begin();
                Query query2 = em.createQuery(" UPDATE Nalazi s "
                    + "  SET s.kolicina = :kolicina "
                    + "  WHERE s.sifPr = :sifPr "
                    + "  AND   s.sifAr = :sifAr");
            
            
            
                int updateCount = query2.setParameter("kolicina", kol-kolicina)
                                        .setParameter("sifPr", idC)
                                        .setParameter("sifAr", idA)
                                        .executeUpdate();
            
                em.getTransaction().commit();
                System.out.println("updateovano"); 
            
       

            return 1;
        }

        return 0;
    }

    private static ArrayList<Integer> NalaziSeOstaleProdavnice(String novi_artikal, int kolicina) {
        ArrayList<Integer> sifreProdavnica = new ArrayList<Integer>();
        //(7)   //SELECT nad NalaziSe
                //dohvatam sve ostale prodavnice
                //(tj. sa drugim siframa
        return (sifreProdavnica);
    }

    private static void podizanjeRezervacije(Scanner input, String ime, String prezime, String jmbg) {
        //(10)   //DELETE iz tabele Rezervacija
                 //UPDATE kolicina artikla u Nalazi_Se (smanjim za to sto sam podigla)
                 //UPDATE BrojProdatih u tabeli Promet
    
    
    
    
    }

}
