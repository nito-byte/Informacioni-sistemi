/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is_fabrika;

import artikal.Artikal;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
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

    private static int ukupnoArtikala;
    private static int kolicina;
    private static int brojMenadzera;
    
    private static int idA;
    private static int VremeProizvodnje;
    private static double Cena;

    @Resource(lookup = "jms/ConnectionFactory")
    private static ConnectionFactory connectionFactory;

    @Resource(lookup = "jms/ConnectionFactory1")
    private static ConnectionFactory connectionFactory1;
    
    

    @Resource(lookup = "jms/myTopic")
    private static Topic topic;
    
    @Resource(lookup = "jms/myTopic1")
    private static Topic topic1;

    public static void main(String[] args) {
        
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_RadnikPU");
    EntityManager em = emf.createEntityManager();
        
        brojMenadzera = 3;   // ********************************za pocetak dok ne vidim kako
        
        

        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        JMSContext context1 = connectionFactory.createContext();
        JMSProducer producer1 = context1.createProducer();
        
        
        

        //izaberem koju od dve radnje radim:
        System.out.println("Radnikov izbor radnji: \n"
                + "  1) Proizvodnja artikla\n"
                + "  2) Promena cene artikla\n");

//        int pom = (int) (Math.round(Math.random()) + 1);
//        System.out.println(pom);
        
        int pom = 2;
        

        if (pom == 1) {
            try {
                //(1) Radnik izabere broj iz baze koji je u opsegu
                //    velicine tabele SpisakArtikalaFabrikaPravi
                //    to uradi na random. To predstavlja artikal.
                
                //SELECT da dohvati ukupan broj artikala u tabeli
                Query query = em.createQuery(" SELECT Count(s.idSp) "
                                           + " FROM Spisak s ");
                long ukupnoArtikala = (Long)query.getSingleResult();
                System.out.println("Ukupno ima: "+ ukupnoArtikala);
                

 
                
                
                //zatim izaberem proizvoljan artikal
                Random r = new Random();
                int Low = 1;
                int High = (int)ukupnoArtikala;
                int Result = r.nextInt(High - Low) + Low;
                
                idA = Result;
                System.out.println("ID artikla: "+ idA);

                
                //(2) Radnik izabere proizvoljnu kolicinu koriscenjem random.
                
                r = new Random();
                Low = 1;
                High = 10;
                Result = r.nextInt(High-Low) + Low;
                
                kolicina = Result;
                
                
                //(3) Ode se do baze i dohvati se element sa rednim
                //    brojem koji je gore dohvacen kao redni broj artikla
                //    i dohvati se njegovo vreme proizvodnje
                
                
                //SELECT da dohvatim VremeProizvodnje
                query = em.createQuery("   SELECT s.vremeProizvodnje "
                                       + " FROM Spisak s "
                                       + " WHERE s.idSp = :id");
                query.setParameter("id",idA);
                int vreme = (Integer)query.getSingleResult();
                System.out.println("Vreme proizvodnje: "+ vreme);
                
                
                //SELECT da dohvatim sifru artikla
                query = em.createQuery("   SELECT s.sifra "
                                       + " FROM Spisak s "
                                       + " WHERE s.idSp = :id");
                query.setParameter("id",idA);
                String sifra = (String)query.getSingleResult();
                System.out.println("Sifra artikla: "+ sifra);
                
        
                VremeProizvodnje = vreme;  
                
                Thread.sleep(VremeProizvodnje * 1000);                   /*(4) sleep dok se prave proizvodi*/
                
                
              
                
                
                
                
                
                /*(5) salje se proizvoljnom menadzeru objekat*/
//                r = new Random();
//                Low = 1;
//                High = (int)brojMenadzera;
//                Result = r.nextInt(High - Low) + Low;
//                
//                int idM = Result;
//                System.out.println("idM: "+ idM); 
                
                int idM = 2;
                
                Artikal artikal = new Artikal(sifra, kolicina);
                
                ObjectMessage objectMessage = context1.createObjectMessage();
                objectMessage.setObject(artikal);
                objectMessage.setIntProperty("idM", idM);               /*mislim da ovako posaljem odredjenom menadzeru*/
   //             objectMessage.setJMSMessageID(String.valueOf(idM));
                producer.send(topic1,objectMessage);
                System.out.println("Poruka je poslata za "+idM);
                
                
                
            } catch (InterruptedException | JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        } else {
        
        try {
            //(1) Radnik izabere broj iz baze koji je u opsegu
            //    velicine tabele SpisakArtikalaFabrikaPravi
            //    to uradi na random. To predstavlja artikal.
            
            //SELECT da dohvati ukupan broj artikala u tabeli
            Query query = em.createQuery(" SELECT Count(s.idSp) "
                    + " FROM Spisak s ");
            long ukupnoArtikala = (Long)query.getSingleResult();
            System.out.println("Ukupno ima: "+ ukupnoArtikala);
            
            
            
            
            
            //zatim izaberem proizvoljan artikal
            Random r = new Random();
            int Low = 1;
            int High = (int)ukupnoArtikala;
            int Result = r.nextInt(High - Low) + Low;
            
            idA = Result;
            System.out.println("Sifra artikla: "+ idA);
            
            
            
            //(6) Radnik menja cenu artikla. Na random ce da generise
            //    novu cenu artikla.
            
            double start = 50;
            double end = 200;
            double random = new Random().nextDouble();
            double result = start + (random * (end - start));
            
            Cena = result;
            
            
            // UPDATE tabelu sa ovom Cenom
            em.getTransaction().begin();
            Query query1 = em.createQuery(" UPDATE Spisak s "
                    + "  SET s.cena = :cena "
                    + "  WHERE s.idSp = :id ");
            
            int updateCount = query1.setParameter("cena", Cena).setParameter("id", idA).executeUpdate();
            
            em.getTransaction().commit();
            System.out.println("Cena: "+ Cena);
            
            
//        //(7) Posalje Topic svim menadzerima
//            
//          //ovde cu samo da saljem poruku tekstualnu sa novom cenom
            //prvo dohvatim sve informacije o poruci
            Query query3 = em.createQuery("   SELECT s.naziv, s.sifra "
                                       + " FROM Spisak s "
                                       + " WHERE s.idSp = :id");
            query3.setParameter("id",idA);
            Object[] s = (Object[]) query3.getSingleResult();
            String naziv = (String) s[0];
            String sifra = (String) s[1];
            System.out.println(sifra+ " / "+naziv);
            
//            long n = (Long)query.getSingleResult();
//            String naziv = Long.toString(n);
//            System.out.println("Naziv "+naziv);
//            
//            Query query4 = em.createQuery("   SELECT s.sifra"
//                                       + " FROM Spisak s "
//                                       + " WHERE s.idSp = :id");
//            query4.setParameter("id",idA);
//            String s = (String)query.getSingleResult();
//            System.out.println("Sifra:"+s);
  //          String sifra = Long.toString(s);
            
            
//RADI: 
//svi dobijaju poruku da je promenjena cena
            
            TextMessage textMessage = context.createTextMessage();
            textMessage.setDoubleProperty("cena", Cena);
            textMessage.setStringProperty("naziv", naziv);
            textMessage.setText(sifra);
            producer.send(topic, textMessage);
            
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        } 
        
        }

}
