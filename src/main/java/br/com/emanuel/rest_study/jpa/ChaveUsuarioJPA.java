package br.com.emanuel.rest_study.jpa;

import br.com.emanuel.rest_study.jpa.entity.ChaveUsuario;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Emanuel Cordeiro
 */
public class ChaveUsuarioJPA {
    @PersistenceContext(unitName = "rest-study-pu")
    private EntityManager em;
    
    public ChaveUsuario buscarPorChave(String chave) {
        return this.em.createQuery("from ChaveUsuario u where u.chave = ?1", ChaveUsuario.class).setParameter(1, chave).getSingleResult();
    }
}
