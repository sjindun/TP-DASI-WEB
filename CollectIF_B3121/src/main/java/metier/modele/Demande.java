package metier.modele;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.Version;

@Entity
public class Demande implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(javax.persistence.TemporalType.DATE)
    Date date_demande;
    @ManyToOne
    private Adherent adherent;

    @ManyToOne
    private Activite activite;

    @Version
    private int version;

    @ManyToOne
    private Evenement evenement;

    String momentJournee;

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    protected Demande() {
    }

    public Demande(Date date, Adherent A, Activite activite, String moment) {
        this.date_demande = date;
        this.adherent = A;
        this.activite = activite;
        this.momentJournee = moment;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date_demande;
    }

    public String getMomentJournee() {
        return momentJournee;
    }

    public void setDate(Date date) {
        this.date_demande = date;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public Activite getActivite() {
        return activite;
    }

    @Override
    public String toString() {
        return "Demande{" + "id=" + id + ", Date=" + date_demande + ", momentJournee=" + momentJournee + ", Activite=" + activite.getDenomination() + '}';
    }

}
