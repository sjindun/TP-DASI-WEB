package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.*;

public class PayantDAO {

    public Payant findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Payant even = null;
        try {

            even = em.find(Payant.class, id);
        } catch (Exception e) {
            throw e;
        }
        return even;
    }

    public List<Payant> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Payant> payant = null;
        try {
            Query q = em.createQuery("SELECT d FROM Demande d");
            payant = (List<Payant>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }

        return payant;

    }

    public void CreerPayant(Payant E) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(E);
        } catch (Exception e) {
            throw e;
        }
    }

}
