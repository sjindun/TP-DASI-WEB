package dao;

import javax.persistence.EntityManager;
import metier.modele.NonPayant;

public class NonPayantDAO {

    public void CreerEvenementNonPayant(NonPayant E) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(E);
        } catch (Exception e) {
            throw e;
        }
    }
}
