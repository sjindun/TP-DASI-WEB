package vue;

import java.util.List;
import metier.modele.*;
import dao.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import metier.service.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        Adherent A = null;

        String entreeClavier = " ";
        while (A == null) {
            entreeClavier = faireSaisie("identification");

            if (entreeClavier.equals("/creer")) {
                A = createAdherent(faireSaisie("prenom"), faireSaisie("nom"), faireSaisie("mail"), faireSaisie("adresse"));
            } else if (entreeClavier.equals("/seConnecter")) { // CHECKED
                A = seConnecter(faireSaisie("mail"));
            }
        }
        System.out.println(A);
        while (!entreeClavier.equals("/quit")) {
            entreeClavier = faireSaisie("Service metier");

            switch (entreeClavier) {
                case "/consulterActivite": //checked
                    List<Activite> activite = listeActivite();
                    if (activite != null) {
                        for (int i = 0; i < activite.size(); i++) {
                            System.out.println(activite.get(i));
                        }
                    }
                    break;
                case "/creerDemande":

                    Demande demandeCree = faireDemande(A, faireSaisie("activite"), faireSaisie("date"), faireSaisie("Moment"));
                    System.out.println(demandeCree);
                    break;
                case "/historiqueDemande": //normally checked
                    List<Demande> demande = historiqueDemandes(A);
                    if (demande != null) {
                        for (int i = 0; i < demande.size(); i++) {
                            System.out.println(demande.get(i));//demande.get(i).getDate().getYear() + ":" + demande.get(i).getDate().getMonth() +":"+ demande.get(i).getDate().getDay()  );
                        }
                    }
                    break;
                case "/affecterPAF":
                    Evenement E = affecterPAF(faireSaisieEntier("idEvenement"), faireSaisieEntierInt("cotisation"));
                    System.out.println(E);
                    break;
                case "/affecterLieu":
                    Evenement affecterLieu = affecterLieu(faireSaisieEntier("id Evenement"), faireSaisieEntier("id Lieu"));
                    System.out.println(affecterLieu);
                    break;
                case "/consulterParticipant":
                    List<Adherent> listeA = consulterParticipants(faireSaisieEntier("idEvenement"));
                    for (int i = 0; i < listeA.size(); i++) {
                        System.out.println(listeA.get(i));
                    }

                    break;
                case "/consulterLieux": //checked
                    List<Lieu> lesLieux = listeLieux();
                    if (lesLieux != null) {
                        for (int i = 0; i < lesLieux.size(); i++) {
                            System.out.println(lesLieux.get(i));
                        }
                    }

                    break;
                case "/consulterEvenement":
                    List<Evenement> lesEvenements = listeEvenement();
                    if (lesEvenements != null) {
                        for (int i = 0; i < lesEvenements.size(); i++) {
                            System.out.println(lesEvenements.get(i));
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        seDeconnecter();
    }

    public static Adherent createAdherent(String prenom, String nom, String mail, String adresse) {

        Adherent A = null;

        A = ServiceMetier.creerAdherent(nom, prenom, mail, adresse);
        if (A == null) {

            System.out.println("Cette email est déjà utilisé, envoi du mail à ce dernier pour le prévenir");

        }
        return A;
    }

    public static Demande faireDemande(Adherent adherent, String activite, String sDate, String moment) {
        Demande D = null;
        Date laDate = null;

        try {
            laDate = dt.parse(sDate);
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        ServiceMetier servM = new ServiceMetier();
        D = servM.creerDemande(adherent, activite, laDate, moment);

        if (D == null) {
            System.out.println("Demande non créé");
        } else {
            System.out.println("Demande crée");
        }
        return D;

    }

    public static Adherent seConnecter(String mail) {

        Adherent A = null;
        ServiceMetier servM = new ServiceMetier();
        A = servM.seConnecter(mail);
        if (A != null) {
            System.out.println("nom :" + A.getNom() + "prenom :" + A.getPrenom() + "mail : " + A.getMail() + "Adresse :" + A.getAdresse());
        }
        return A;
    }

    public static String faireSaisie(String nomSaisie) {
        System.out.println("Entrer " + nomSaisie);
        Scanner scanner = new Scanner(System.in);
        String entreeClavier = scanner.nextLine();
        return entreeClavier;
    }

    public static Long faireSaisieEntier(String nomSaisie) {
        System.out.println("Entrer " + nomSaisie);
        Scanner scanner = new Scanner(System.in);
        Long entreeClavier = (Long) scanner.nextLong();
        return entreeClavier;
    }

    public static int faireSaisieEntierInt(String nomSaisie) {
        System.out.println("Entrer " + nomSaisie);
        Scanner scanner = new Scanner(System.in);
        int entreeClavier = scanner.nextInt();
        return entreeClavier;
    }

    public static List<Activite> listeActivite() {
        List<Activite> activites = null;
        ServiceMetier servM = new ServiceMetier();
        activites = servM.getActivites();
        return activites;
    }

    public static List<Lieu> listeLieux() {
        List<Lieu> lieux = null;
        ServiceMetier servM = new ServiceMetier();
        lieux = servM.getLieux();

        return lieux;
    }

    public static List<Evenement> listeEvenement() {
        List<Evenement> evenements = null;
        ServiceMetier servM = new ServiceMetier();
        evenements = servM.getEvenement();
        return evenements;
    }

    public static List<Adherent> consulterParticipants(Long idEvenement) {
        List<Adherent> participants = null;
        ServiceMetier servM = new ServiceMetier();
        participants = servM.consulterParticipants(idEvenement);
        return participants;
    }

    public static List<Demande> historiqueDemandes(Adherent adherent) {
        List<Demande> demandesPersonnelles = null;
        ServiceMetier servM = new ServiceMetier();
        demandesPersonnelles = servM.demandesPersonnelles(adherent);

        return demandesPersonnelles;
    }

    public static Evenement affecterLieu(Long idEvenement, Long idLieu) {
        Evenement aAffecter = null;

        ServiceMetier servM = new ServiceMetier();

        aAffecter = servM.affecterLieu(idEvenement, idLieu);

        return aAffecter;
    }

    public static Evenement affecterPAF(Long idEvenement, int cotisation) {
        Evenement aAffecter = null;
        ServiceMetier servM = new ServiceMetier();

        aAffecter = servM.affecterPAF(idEvenement, cotisation);
        return aAffecter;
    }

    public static void seDeconnecter() {
        JpaUtil.destroy();
    }

}
