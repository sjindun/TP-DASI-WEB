package metier.modele;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.modele.Demande;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-10T14:43:32")
@StaticMetamodel(Adherent.class)
public class Adherent_ { 

    public static volatile SingularAttribute<Adherent, String> mail;
    public static volatile SingularAttribute<Adherent, Double> latitude;
    public static volatile SingularAttribute<Adherent, String> adresse;
    public static volatile SingularAttribute<Adherent, Long> id;
    public static volatile ListAttribute<Adherent, Demande> demandes;
    public static volatile SingularAttribute<Adherent, String> nom;
    public static volatile SingularAttribute<Adherent, String> prenom;
    public static volatile SingularAttribute<Adherent, Double> longitude;

}