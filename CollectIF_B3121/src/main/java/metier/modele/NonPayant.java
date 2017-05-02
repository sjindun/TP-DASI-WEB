/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

/**
 *
 * @author vmorel
 */
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;

@Entity
public class NonPayant extends Evenement {

    public NonPayant(Date date, List<Demande> demandes, Activite act, String moment) {
        super(date, demandes, act, moment);
    }

    protected NonPayant() {
    }

    @Override
    public int getCotisation() {
        return -1;
    }

    @Override
    public String toString() {
        return "Evenement{" + "id=" + getId() + ", Date=" + date_evenement + ", momentJournee=" + momentJournee + ", Activite=" + getActivite().getDenomination() + '}';
    }
}
