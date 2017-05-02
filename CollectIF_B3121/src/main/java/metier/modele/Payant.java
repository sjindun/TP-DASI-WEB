package metier.modele;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;

@Entity
public class Payant extends Evenement {

    int cotisation;

    protected Payant() {
    }

    public Payant(Date date, List<Demande> demandes, Activite act, String moment) {
        super(date, demandes, act, moment);
    }

    @Override
    public int getCotisation() {
        return cotisation;
    }

    public void setCotisation(int cotisation) {
        this.cotisation = cotisation;
    }

    @Override
    public String toString() {
        return "Evenement{" + "id=" + getId() + ", Date=" + date_evenement + ", momentJournee=" + momentJournee + ", Activite=" + getActivite().getDenomination() + ", cotisation=" + cotisation + '}';
    }

}
