package br.com.emanuel.rest_study.jpa;

import br.com.emanuel.rest_study.jpa.entity.Funcionario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author Emanuel Cordeiro
 */
@Transactional
public class FuncionarioJPA {

    @PersistenceContext(unitName = "rest-study-pu")
    private EntityManager em;

    public Funcionario salvar(Funcionario f) {
        this.em.persist(f);
        this.em.flush();
        return f;
    }

    public Funcionario buscarPorId(int id) {
        return this.em.find(Funcionario.class, id);
    }

    public List< Funcionario> buscarTodos() {
        return this.em.createQuery("from Funcionario f order by 1").getResultList();
    }
    
    public void delete(Funcionario f) {
        this.em.remove(f);
    }
    
    public Funcionario modify(Funcionario f) {
       return this.em.merge(f);
    }

}

