package metier.modele;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Adherent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String prenom;
    private String mail;
    private String adresse;
    private Double longitude;
    private Double latitude;
    @OneToMany(mappedBy = "adherent")  // référence la relation dans la classe Commune
    private List<Demande> demandes;

    protected Adherent() {
    }

    public Adherent(String nom, String prenom, String mail, String adresse) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.adresse = adresse;
        this.longitude = null;
        this.latitude = null;
    }

    public void AfficherDemandes() {
        for (int i = 0; i < demandes.size(); i++) {
            System.out.println("Le " + demandes.get(i).getDate() + " à " + demandes.get(i).getDate());
        }
    }

    public void AddToList(Demande D) {
        demandes.add(D);
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }

    public String getAdresse() {
        return adresse;
    }

    public List<Demande> getDemandes() {
        return demandes;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void ajouterDemande(Demande d) {
        this.demandes.add(d);
    }

    public void setLatitudeLongitude(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Adherent{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", adresse=" + adresse + ", longitude=" + longitude + ", latitude=" + latitude + '}';
    }

}
