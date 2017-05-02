package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.*;

public class EvenementDAO {

    public Evenement findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Evenement even = null;
        try {
            even = em.find(Evenement.class, id);
        } catch (Exception e) {
            throw e;
        }
        return even;
    }

    public List<Evenement> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Evenement> evenements = null;
        try {
            Query q = em.createQuery("SELECT e FROM Evenement e");
            evenements = (List<Evenement>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }

        return evenements;

    }

    public void CreerEvenement(Evenement E) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(E);
        } catch (Exception e) {
            throw e;
        }
    }

    public void CreerEvenementPayant(Payant E) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(E);
        } catch (Exception e) {
            throw e;
        }
    }

}
