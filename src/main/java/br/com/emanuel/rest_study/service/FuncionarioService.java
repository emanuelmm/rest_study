/*
 * Copyright (C) 2014 emanuel
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package br.com.emanuel.rest_study.service;

import br.com.emanuel.rest_study.entity.Funcionario;
import br.com.emanuel.rest_study.entity.FuncionarioJPA;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author emanuel
 */
@Path("funcionario")
@Stateless
public class FuncionarioService {

    @Inject
    private FuncionarioJPA funcionarioJPA;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Funcionario buscarPorId(@QueryParam("id") int id) {
        return funcionarioJPA.buscarPorId(id);
    }

    @Path("criar")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Funcionario create(@QueryParam("nome") String nome, @QueryParam("salario") double salario) {
        Funcionario f = new Funcionario();
        f.setNome(nome);
        f.setSalario(salario);
        f = funcionarioJPA.salvar(f);
        return f;
    }

}
