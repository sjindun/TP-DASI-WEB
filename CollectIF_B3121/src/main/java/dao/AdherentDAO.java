package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Adherent;

public class AdherentDAO {

    public Adherent findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Adherent adherent = null;
        try {
            adherent = em.find(Adherent.class, id);
        } catch (Exception e) {
            throw e;
        }
        return adherent;
    }

    public List<Adherent> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Adherent> adherents = null;
        try {
            Query q = em.createQuery("SELECT a FROM Adherent a");
            adherents = (List<Adherent>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }

        return adherents;
    }

    public Adherent CreerAdherent(Adherent A) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(A);
        } catch (Exception e) {
            throw e;
        }
        return A;
    }

    public List<Adherent> queryMail(String mail) {
        String requete = "SELECT a FROM Adherent a WHERE a.mail = :mail";

        List<Adherent> listReq = null;
        EntityManager em = JpaUtil.obtenirEntityManager();

        try {
            Query q = em.createQuery(requete);
            q.setParameter("mail", mail);
            listReq = (List<Adherent>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }

        return listReq;
    }

}
