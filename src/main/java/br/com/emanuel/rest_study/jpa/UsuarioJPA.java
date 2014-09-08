package br.com.emanuel.rest_study.jpa;

import br.com.emanuel.rest_study.jpa.entity.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author Emanuel Cordeiro
 */
@Transactional
public class UsuarioJPA {

    @PersistenceContext(unitName = "rest-study-pu")
    private EntityManager em;
    
    public Usuario buscarPorLogin(String login) {
        return this.em.createQuery("from Usuario u where u.login = ?1", Usuario.class).setParameter(1, login).getSingleResult();
    }

}
