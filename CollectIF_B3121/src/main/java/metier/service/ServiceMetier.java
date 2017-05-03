package metier.service;

import com.google.maps.errors.ApiException;
import dao.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.*;
import java.util.List;
import javax.persistence.RollbackException;
import static util.GeoTest.getLat;
import static util.GeoTest.getLng;

public class ServiceMetier {

    public static Adherent creerAdherent(String nom, String prenom, String mail, String adresse) {
        JpaUtil.init();
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();

        Adherent newA = null;
        AdherentDAO aDAO = new AdherentDAO();
        List<Adherent> listA = aDAO.queryMail(mail);
        if (listA.size() == 0) {
            newA = new Adherent(nom, prenom, mail, adresse);
            newA.setLatitudeLongitude(getLat(adresse), getLng(adresse));
            try {
                aDAO.CreerAdherent(newA);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                JpaUtil.annulerTransaction();
                JpaUtil.fermerEntityManager();
                return null;
            }

        } else {
            ServiceTechnique.envoieMailInscriptionEchec(mail, prenom);
            return null;
        }
        ServiceTechnique.envoieMailInscriptionSucces(newA);
        ServiceTechnique.envoieMailAdminInscriptionSucces(newA);
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();

        return newA;
    }

    public static Adherent seConnecter(String mail) {
        JpaUtil.init();
        JpaUtil.creerEntityManager();

        Adherent A = null;
        AdherentDAO aDAO = new AdherentDAO();
        List<Adherent> liste = aDAO.queryMail(mail);

        if (liste.size() == 1) {
            A = liste.get(0);
        } else {
            System.out.println("Cette utilisateur n'existe pas");
        }
        JpaUtil.fermerEntityManager();
        return A;
    }
    
    // AJOUT 
    public static Adherent getAdherent(long id) {
        JpaUtil.creerEntityManager();

        Adherent A = null;
        AdherentDAO aDAO = new AdherentDAO();
        try{
            A = aDAO.findById(id);
        }catch(Exception e){
        }
        JpaUtil.fermerEntityManager();
        return A;
    }
    
    

    public static Demande creerDemande(Adherent A, String activite, Date date, String moment) {
        JpaUtil.creerEntityManager();

        if (A == null) {
            return null;
        }
        Demande demande = null;
        boolean transactionARefaire = true;
        while (transactionARefaire) {
            try {
                JpaUtil.ouvrirTransaction();
                transactionARefaire = false;
                List<Activite> listeAct = null;
                ActiviteDAO aDAO = new ActiviteDAO();
                try {
                    listeAct = aDAO.findByDenomination(activite);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    JpaUtil.fermerEntityManager();
                    return null;
                }

                if (listeAct != null) {

                    List<Demande> duplicate = null;
                    DemandeDAO dDAo = new DemandeDAO();
                    try {
                        duplicate = dDAo.findDuplicateDemande(A, listeAct.get(0), date);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        JpaUtil.annulerTransaction();
                        JpaUtil.fermerEntityManager();
                        return null;
                    }

                    if (duplicate.size() != 0) {
                        System.out.println("demande déjà faite");
                        return null;
                    }

                    demande = new Demande(date, A, listeAct.get(0), moment);
                    List<Demande> demandes = null;
                    //GeoTest.fairePause();
                    try {
                        DemandeDAO dDAO = new DemandeDAO();
                        dDAO.CreerDemande(demande);
                        demandes = dDAO.findSame(date, demande.getActivite(), moment);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        JpaUtil.annulerTransaction();
                        JpaUtil.fermerEntityManager();
                        return null;
                    }

                    if (demandes.size() == demandes.get(0).getActivite().getNbParticipants()) {
                        if (demande.getActivite().getPayant()) {
                            Payant P = new Payant(date, demandes, listeAct.get(0), moment);
                            PayantDAO pDAO = new PayantDAO();
                            try {
                                pDAO.CreerPayant(P);
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                                JpaUtil.annulerTransaction();
                                JpaUtil.fermerEntityManager();
                                return null;
                            }
                            for (int i = 0; i < demandes.get(0).getActivite().getNbParticipants(); i++) {
                                demandes.get(i).setEvenement(P);
                            }

                        } else {
                            NonPayant NP = new NonPayant(date, demandes, listeAct.get(0), moment);
                            NonPayantDAO NpDAO = new NonPayantDAO();
                            try {
                                NpDAO.CreerEvenementNonPayant(NP);
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                                JpaUtil.annulerTransaction();
                                JpaUtil.fermerEntityManager();
                                return null;
                            }
                            for (int i = 0; i < demandes.get(0).getActivite().getNbParticipants(); i++) {
                                demandes.get(i).setEvenement(NP);
                            }
                        }
                    }

                }
                JpaUtil.validerTransaction();
            } catch (RollbackException e) {
                System.out.println("Evenement non créé avec cet Adhérent");
                JpaUtil.annulerTransaction();
                transactionARefaire = true;
            }

        }

        JpaUtil.fermerEntityManager();
        return demande;
    }

    public static void creerEvenement(Evenement E) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        EvenementDAO eDao = new EvenementDAO();

        try {
            eDao.CreerEvenement(E);
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }

        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }

    public static List<Adherent> consulterListeAdherent() {
        JpaUtil.creerEntityManager();

        AdherentDAO aDao = new AdherentDAO();
        List<Adherent> adherents = null;

        try {
            adherents = aDao.findAll();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            JpaUtil.fermerEntityManager();
            return null;
        }
        JpaUtil.fermerEntityManager();
        return adherents;

    }

    public static List<Lieu> consulterListeLieu() {
        JpaUtil.creerEntityManager();

        List<Lieu> lieu = null;
        LieuDAO lDAO = new LieuDAO();
        try {
            lieu = lDAO.findAll();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            JpaUtil.fermerEntityManager();
            return null;
        }
        JpaUtil.fermerEntityManager();
        return lieu;

    }

    public static List<Activite> consulterListeActivite() {
        JpaUtil.creerEntityManager();

        List<Activite> activite = null;
        ActiviteDAO aDAO = new ActiviteDAO();
        try {
            activite = aDAO.findAll();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            JpaUtil.fermerEntityManager();
            return null;
        }
        JpaUtil.fermerEntityManager();
        return activite;

    }

    public static List<Activite> getActivites() {
        JpaUtil.creerEntityManager();
        List<Activite> activites = null;
        ActiviteDAO aDAO = new ActiviteDAO();
        try {
            activites = aDAO.findAll();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            JpaUtil.fermerEntityManager();
            return null;
        }
        JpaUtil.fermerEntityManager();
        return activites;
    }

    public static List<Evenement> getEvenement() {
        JpaUtil.creerEntityManager();
        List<Evenement> evenement = null;
        EvenementDAO eDAO = new EvenementDAO();
        try {
            evenement = eDAO.findAll();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            JpaUtil.fermerEntityManager();
            return null;
        }
        JpaUtil.fermerEntityManager();
        return evenement;
    }

    public static List<Lieu> getLieux() {
        JpaUtil.creerEntityManager();
        List<Lieu> lieux = null;
        LieuDAO lDAO = new LieuDAO();
        try {
            lieux = lDAO.findAll();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            JpaUtil.fermerEntityManager();
            return null;
        }
        JpaUtil.fermerEntityManager();
        return lieux;
    }

    public static List<Adherent> consulterParticipants(Long id) {
        JpaUtil.creerEntityManager();
        Evenement E = null;
        EvenementDAO eDAO = new EvenementDAO();
        try {
            E = eDAO.findById(id);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            JpaUtil.fermerEntityManager();
            return null;
        }
        List<Adherent> participant = new ArrayList();
        if (E != null) {
            for (int i = 0; i < E.getListeDemande().size(); i++) {
                participant.add(E.getListeDemande().get(i).getAdherent());
            }
        }
        JpaUtil.fermerEntityManager();
        return participant;
    }

    public static List<Demande> demandesPersonnelles(Adherent A) {
        JpaUtil.creerEntityManager();
        DemandeDAO dDAO = new DemandeDAO();
        List<Demande> demandes = null;

        try {
            demandes = dDAO.findByAdherent(A);
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }

        return demandes;
    }

    public static Evenement affecterLieu(Long idEvenement, Long idLieu) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();

        Evenement aAffecter = null;
        Lieu leLieu = null;
        EvenementDAO eDAO = new EvenementDAO();
        LieuDAO lDAO = new LieuDAO();
        try {
            aAffecter = eDAO.findById(idEvenement);
            leLieu = lDAO.findById(idLieu);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            JpaUtil.annulerTransaction();
            JpaUtil.fermerEntityManager();
            return null;
        }

        if (leLieu == null) {
            return aAffecter;
        }

        if (aAffecter != null) {
            if (aAffecter.getLieu() == null) {
                aAffecter.setLieu(leLieu);
                List<Demande> listD = aAffecter.getListeDemande();
                try {
                    ServiceTechnique.envoieMailEvenement(aAffecter);
                } catch (ApiException ex) {
                    Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(aAffecter);
            }
        } else {
            System.out.println("id non existant");
        }

        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();

        return aAffecter;
    }

    public static Evenement affecterPAF(Long idEvenement, int cotisation) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        Evenement aAffecter = null;
        EvenementDAO eDAO = new EvenementDAO();
        try {
            aAffecter = eDAO.findById(idEvenement);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            JpaUtil.annulerTransaction();
            JpaUtil.fermerEntityManager();
            return null;
        }

        if (aAffecter != null) {
            if (aAffecter.getActivite().getPayant()) {
                return affecterPAFPayant(idEvenement, cotisation);
            } else {
                System.out.println("C'est une activite non payante, impossible d'affecter une cotisation");
                return null;
            }
        } else {
            System.out.println("id non existant");
        }

        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        return aAffecter;

    }

    public static Payant affecterPAFPayant(Long idPayant, int cotisation) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        PayantDAO pDAO = new PayantDAO();
        Payant aAffecter = null;
        try {
            aAffecter = pDAO.findById(idPayant);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            JpaUtil.annulerTransaction();
            JpaUtil.fermerEntityManager();
            return null;
        }
        if (aAffecter.getCotisation() == 0) {
            aAffecter.setCotisation(cotisation);
        } else {
            return null;
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();

        return aAffecter;
    }

    public static void seDeconnecter() {
        JpaUtil.destroy();
    }
}
