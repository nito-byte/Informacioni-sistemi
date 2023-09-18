package entiteti;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-08T01:27:06")
@StaticMetamodel(Rezervacija.class)
public class Rezervacija_ { 

    public static volatile SingularAttribute<Rezervacija, String> ime;
    public static volatile SingularAttribute<Rezervacija, String> prezime;
    public static volatile SingularAttribute<Rezervacija, Integer> sifRez;
    public static volatile SingularAttribute<Rezervacija, Integer> sifA;
    public static volatile SingularAttribute<Rezervacija, Integer> sifP;
    public static volatile SingularAttribute<Rezervacija, Date> vremePrispeca;
    public static volatile SingularAttribute<Rezervacija, String> status;

}