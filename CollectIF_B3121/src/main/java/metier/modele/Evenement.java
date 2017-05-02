package metier.modele;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

@Entity
public class Evenement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date date_evenement;

    @OneToMany(mappedBy = "evenement")
    private List<Demande> listeDemande;

    @ManyToOne
    private Lieu lieu;

    @ManyToOne
    private Activite activite;

    String momentJournee;

    protected Evenement() {
    }

    public Evenement(Date date, List<Demande> demandes, Activite act, String moment) {
        this.date_evenement = date;
        this.listeDemande = demandes;
        this.activite = act;
        this.momentJournee = moment;
    }

    public String getMomentJournee() {
        return momentJournee;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    public List<Demande> getListeDemande() {
        return listeDemande;
    }

    public Long getId() {
        return id;
    }

    public Date getDate_evenement() {
        return date_evenement;
    }

    public Activite getActivite() {
        return activite;
    }

    public int getCotisation() {
        return -1;
    }

    @Override
    public String toString() {
        return "Evenement{" + "id=" + id + ", Date=" + date_evenement + ", momentJournee=" + momentJournee + ", Activite=" + activite.getDenomination() + '}';
    }
}
