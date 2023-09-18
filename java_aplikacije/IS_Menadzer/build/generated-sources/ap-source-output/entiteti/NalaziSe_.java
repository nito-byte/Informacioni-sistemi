package entiteti;

import entiteti.Artikal;
import entiteti.NalaziSePK;
import entiteti.Prodavnica;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-07T22:35:41")
@StaticMetamodel(NalaziSe.class)
public class NalaziSe_ { 

    public static volatile SingularAttribute<NalaziSe, Prodavnica> prodavnica;
    public static volatile SingularAttribute<NalaziSe, NalaziSePK> nalaziSePK;
    public static volatile SingularAttribute<NalaziSe, Artikal> artikal;
    public static volatile SingularAttribute<NalaziSe, Integer> kolicina;

}