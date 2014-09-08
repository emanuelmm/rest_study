package br.com.emanuel.rest_study.service;

import br.com.emanuel.rest_study.jpa.entity.Funcionario;
import br.com.emanuel.rest_study.jpa.FuncionarioJPA;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Emanuel Cordeiro
 */
@Stateless
@Path("funcionarios")
public class PessoalService {

    @Inject
    private FuncionarioJPA funcionarioJPA;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Funcionario> buscarTodos() {
        return funcionarioJPA.buscarTodos();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Funcionario buscarPorId(@PathParam("id") int id) {
        return funcionarioJPA.buscarPorId(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("novo")
    public Funcionario create(@QueryParam("nome") String nome, 
            @QueryParam("salario") double salario) {
        Funcionario f = new Funcionario();
        f.setNome(nome);
        f.setSalario(salario);
        f = funcionarioJPA.salvar(f);
        return f;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Funcionario modify(@PathParam("id") int id, 
            @QueryParam("nome") String nome, 
            @QueryParam("salario") double salario) {
        Funcionario f = funcionarioJPA.buscarPorId(id);
        if (f != null) {
            f.setNome(nome);
            f.setSalario(salario);
            return funcionarioJPA.modify(f);
        } else {
            return null;
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String delete(@PathParam("id") int id) {
        Funcionario f = this.buscarPorId(id);
        if (f != null) {
            funcionarioJPA.delete(f);
            return "success";
        } else {
            return "not success";
        }
    }

}
