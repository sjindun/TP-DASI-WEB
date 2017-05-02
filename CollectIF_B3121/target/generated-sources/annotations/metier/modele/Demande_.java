package metier.modele;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.modele.Evenement;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-10T14:43:32")
@StaticMetamodel(Demande.class)
public class Demande_ { 

    public static volatile SingularAttribute<Demande, Date> date_demande;
    public static volatile SingularAttribute<Demande, Activite> activite;
    public static volatile SingularAttribute<Demande, Adherent> adherent;
    public static volatile SingularAttribute<Demande, Long> id;
    public static volatile SingularAttribute<Demande, String> momentJournee;
    public static volatile SingularAttribute<Demande, Integer> version;
    public static volatile SingularAttribute<Demande, Evenement> evenement;

}