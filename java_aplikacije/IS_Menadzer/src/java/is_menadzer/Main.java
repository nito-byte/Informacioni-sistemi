package is_menadzer;

import artikal.Artikal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
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

    /**
     * @param args the command line arguments
     */   
    @Resource(lookup = "jms/ConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "jms/ConnectionFactory1")
    private static ConnectionFactory connectionFactory1;
    
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

    @Resource(lookup = "jms/myTopic")
    private static Topic topic;
    
    @Resource(lookup = "jms/myTopic1")
    private static Topic topic1;
    
    @Resource(lookup = "jms/Topic2")
    private static Topic topic2;
    
    @Resource(lookup = "jms/Topic3")
    private static Topic topic3;
    
    
    public static void main(String[] args) throws JMSException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_MenadzerPU");
        EntityManager em = emf.createEntityManager();
        
        System.out.println(args[0]);
        
        
        
        int idC = Integer.parseInt(args[0]);
        
        
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(topic);
        
        JMSContext context1 = connectionFactory1.createContext();
        JMSConsumer consumer1 = context1.createConsumer(topic1);
        
        JMSContext context2 = connectionFactory2.createContext();
        JMSConsumer consumer2 = context1.createConsumer(topic2);
        
        JMSContext context3 = connectionFactory3.createContext();
        JMSProducer producer3 = context3.createProducer();
        
        
        
        /*BITNO!!!!!!!!!!!!!!*/
        /* proveri da li mogu istovremeno da budem prijavljena na dva Topica*/
        /* pa da ovde koristim ta dva istovremeno*/
        
      while(true){  
        
    //(1)   provera stanja artikla
        Message message2 = consumer2.receive();
         int s2 = message2.getIntProperty("idC");
         System.out.println("Ovo je idC " + s2);
         String s1 = null;
        if(idC == s2){
            boolean rand = Math.random() < 0.7;
            
            if(rand){
                s1= "Zapakovan";
            }else {
                s1 = "Otpakovan"; 
            }
        }
        
         TextMessage textMessage1 = context3.createTextMessage();
         textMessage1.setIntProperty("idC", idC);
         textMessage1.setText(s1);
 //           textMessage.setText(String.valueOf(idC));
         producer3.send(topic3,textMessage1);
         System.out.println("Poslato je stanje!");
        
        
        
        
        
    //(2)   izvrsavanje rezervacije
        
        
        
    //(3)   primanje robe
         Message message1 = consumer1.receive();
         int s = message1.getIntProperty("idM");
         System.out.println("Ovo je idC " + s);
          
          if(idC == s){
      //    Message message1 = consumer1.receive();
          if(message1 instanceof ObjectMessage){
              try {
                  ObjectMessage objectMessage = (ObjectMessage) message1;
                  Artikal artikal = (Artikal) objectMessage.getObject();
                  String sifra = artikal.getSifra();
                  int kolicina = artikal.getKolicina();
                  
                  System.out.println("Menadzer prihvata robu"
                            + "sifra: " + sifra + " kolicina: " + kolicina);
                  
                  
                    // SELECT nad Artikal, da dohvatim za datu sifru SifA

                    // UPDATE tabele Nalazi_Se sa SifA, kolicina, idC
                  
                  
                  
                  
                  
                  
                    
                  
                  // SELECT nad Artikal, da dohvatim za datu sifru SifA
                Query query = em.createQuery("   SELECT s.sifA "
                                       + " FROM Artikal s "
                                       + " WHERE s.sifra = :sifra");
                query.setParameter("sifra", sifra);
                int si = (Integer)query.getSingleResult();
                System.out.println("Dohvaceni artikal sifA: "+ si);
                
                
                Query query2 = em.createQuery("   SELECT s.kolicina "
                                       + " FROM Nalazi s "
                                       + "  WHERE s.sifPr = :sifPr "
                                       + "  AND   s.sifAr = :sifAr");
                query2.setParameter("sifPr", idC);
                query2.setParameter("sifAr", si);
                int sa = (Integer)query.getSingleResult();
                System.out.println("Dohvaceni artikal sifA: "+ sa);

                
                // UPDATE tabele Nalazi_Se sa SifA, kolicina, idC
                em.getTransaction().begin();
                Query query1 = em.createQuery(" UPDATE Nalazi s "
                    + "  SET s.kolicina = :kolicina "
                    + "  WHERE s.sifPr = :sifPr "
                    + "  AND   s.sifAr = :sifAr");
            
            
            
                int updateCount = query1.setParameter("kolicina", kolicina+sa)
                                        .setParameter("sifPr", idC)
                                        .setParameter("sifAr", si)
                                        .executeUpdate();
            
                em.getTransaction().commit();
                System.out.println("updateovano"); 
                  
                  
                  
                  
                  
                  
                
                  
                  
              } catch (JMSException ex) {
                  Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
              }
              
          }
          }
          
          
        
        
        
    //(4)   primanje informacije o promeni cene

            Message message = consumer.receive();
            if(message instanceof TextMessage){
                try {
                    TextMessage textMessage = (TextMessage) message;
                    String sifra = textMessage.getText();
                    String naziv = textMessage.getStringProperty("naziv");
                    double cena = textMessage.getDoubleProperty("cena");
                    
                    
                EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("IS_MenadzerPU");
                EntityManager em1 = emf1.createEntityManager(); 
                    
                    
                    
                    // mozda treba da odu do baze i da promene cenu
                    //UPDATE nad tabelom Artikal
                em1.getTransaction().begin();
                Query query1 = em1.createQuery(" UPDATE Artikal a "
                    + "  SET a.cena = :cena "
                    + "  WHERE a.sifra = :sifra"
                    + "  AND   s.naziv = :naziv");
            
            
            
                int updateCount = query1.setParameter("cena", cena)
                                        .setParameter("sifra", sifra)
                                        .setParameter("naziv", naziv)
                                        .executeUpdate();
            
                em1.getTransaction().commit();
                System.out.println("updateovano"); 
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    System.out.println("Primljena je poruka o promeni cene"
                            + "naziv: " + naziv + " sifra: " + sifra + " cena: " +cena);
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        

    }
    
}