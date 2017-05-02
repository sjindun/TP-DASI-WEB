package metier.modele;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.modele.Activite;
import metier.modele.Demande;
import metier.modele.Lieu;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-10T14:43:32")
@StaticMetamodel(Evenement.class)
public class Evenement_ { 

    public static volatile SingularAttribute<Evenement, Activite> activite;
    public static volatile SingularAttribute<Evenement, Date> date_evenement;
    public static volatile ListAttribute<Evenement, Demande> listeDemande;
    public static volatile SingularAttribute<Evenement, Long> id;
    public static volatile SingularAttribute<Evenement, String> momentJournee;
    public static volatile SingularAttribute<Evenement, Lieu> lieu;

}