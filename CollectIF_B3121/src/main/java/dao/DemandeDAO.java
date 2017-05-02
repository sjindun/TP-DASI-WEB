package dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.modele.Demande;

public class DemandeDAO {

    public Demande findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Demande dem = null;
        try {
            dem = em.find(Demande.class, id);
        } catch (Exception e) {
            throw e;
        }
        return dem;
    }

    public List<Demande> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Demande> demandes = null;
        try {
            Query q = em.createQuery("SELECT d FROM Demande d");
            demandes = (List<Demande>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }

        return demandes;
    }

    public void CreerDemande(Demande D) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(D);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Demande> findSame(Date date, Activite activite, String moment) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Demande> demandes = null;
        try {
            Query q = em.createQuery("SELECT d FROM Demande d WHERE d.date_demande = :date AND d.activite = :activite AND d.momentJournee = :moment");
            q.setParameter("date", date);
            q.setParameter("activite", activite);
            q.setParameter("moment", moment);
            demandes = (List<Demande>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }

        return demandes;
    }

    public List<Demande> findByAdherent(Adherent a) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Demande> demandes = null;
        try {
            Query q = em.createQuery("SELECT d FROM Demande d WHERE d.adherent = :aid");
            q.setParameter("aid", a);
            demandes = (List<Demande>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return demandes;
    }

    public List<Demande> findDuplicateDemande(Adherent adherent, Activite activite, Date date) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Demande> demandes = null;
        try {
            Query q = em.createQuery("SELECT d FROM Demande d WHERE d.date_demande = :date AND d.activite = :activite AND d.adherent = :adherent");
            q.setParameter("date", date);
            q.setParameter("activite", activite);
            q.setParameter("adherent", adherent);
            demandes = (List<Demande>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }

        return demandes;
    }
}
