package metier.service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import java.io.IOException;
import metier.modele.Adherent;
import metier.modele.Demande;
import metier.modele.Evenement;
import util.GeoTest;

/**
 *
 * @author mjochauddu
 */
public class ServiceTechnique {

    public static void envoieMailInscriptionSucces(Adherent unAdherent) {
        System.out.println("Expéditeur : collectif@collectif.org");
        System.out.println("Pour : " + unAdherent.getMail());
        System.out.println("Sujet : Bienvenue chez Collect'IF");
        System.out.println("Bonjour " + unAdherent.getPrenom() + ",");
        System.out.println("Nous vous confirmons votre adhésion à l'association COLLECT’IF. Votre numéro d'adhérent est : " + unAdherent.getId());
    }

    public static void envoieMailInscriptionEchec(String mail, String prenom) {
        System.out.println("Expéditeur : collectif@collectif.org");
        System.out.println("Pour : " + mail);
        System.out.println("Sujet : Bienvenue chez Collect'IF");
        System.out.println("Bonjour " + prenom + ",");
        System.out.println("Votre adhésion à l'association COLLECT’IF a malencontreusement échoué... Merci de recommencer ultérieurement.");
    }

    public static void envoieMailAdminInscriptionSucces(Adherent unAdherent) {
        System.out.println("Expéditeur : collectif@collectif.org");
        System.out.println("Pour : collectif@collectif.org");
        System.out.println("Sujet : Nouvel adherent chez Collect'IF");
        System.out.println(unAdherent.getPrenom() + "a adhérer à l'association COLLECT’IF. Son numéro d'adhérent est : " + unAdherent.getId());
    }

    public static void envoieMailEvenement(Evenement unEvenement) throws ApiException, InterruptedException, IOException {

        for (Demande demande : unEvenement.getListeDemande()) {
            Adherent unAdherent = demande.getAdherent();
            System.out.println("Expéditeur : collectif@collectif.org");
            System.out.println("Pour : " + unAdherent.getMail());
            System.out.println("Sujet :  Nouvel Évènement Collect'IF");
            System.out.println("Bonjour " + unAdherent.getPrenom() + ",");
            System.out.println("Comme vous l'aviez souhaité, COLLECT’IF organise un évènement de " + unEvenement.getActivite().getDenomination() + " le " + unEvenement.getDate_evenement().toLocaleString().replaceAll(":", "").replaceAll("000000", "") + ". Vous trouverez ci-dessous les détails de cet évènement.");
            System.out.println("Associativement vôtre,");
            System.out.println("Le Responsable de l'Association");
            System.out.println("Evénement : " + unEvenement.getActivite().getDenomination());
            System.out.println("Date : " + unEvenement.getDate_evenement().toLocaleString().replaceAll(":", "").replaceAll(" 000000", ""));
            System.out.println("Lieu : " + unEvenement.getLieu().getDenomination() + ", " + unEvenement.getLieu().getAdresse());
            System.out.println(afficherDistance(unAdherent.getAdresse(), unEvenement.getLieu().getAdresse()));
            System.out.println("Vous jouerez avec :");
            for (Demande dem : unEvenement.getListeDemande()) {
                Adherent adh = dem.getAdherent();
                if (adh.getId() != unAdherent.getId()) {
                    System.out.println(adh.getPrenom() + " " + adh.getNom());
                }
            }
        }
    }

    public static LatLng trouverCoordonnees(String adresse) {
        LatLng coords = getLatLng(adresse);
        return coords;
    }

    public static String afficherDistance(String origine, String destination) throws ApiException, InterruptedException, IOException {
        String[] origins = {origine};
        String[] destinations = {destination};
        DistanceMatrixApiRequest request = DistanceMatrixApi.getDistanceMatrix(MON_CONTEXTE_GEOAPI, origins, destinations);
        request.mode(TravelMode.WALKING);
        request.language("fr");
        DistanceMatrixRow[] result = request.await().rows;
        String retour = "(à " + result[0].elements[0].distance + " de chez vous soit environ " + result[0].elements[0].duration + " à pied)";
        return retour;
    }

    final static GeoApiContext MON_CONTEXTE_GEOAPI
            = new GeoApiContext().setApiKey("AIzaSyDcVVJjfmxsNdbdUYeg9MjQoJJ6THPuap4");

    public static LatLng getLatLng(String adresse) {
        try {
            GeocodingResult[] results
                    = GeocodingApi.geocode(MON_CONTEXTE_GEOAPI, adresse).await();
            return results[0].geometry.location;
        } catch (Exception ex) {
            return null;
        }
    }
}
