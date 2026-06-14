package com.autobots.automanager.modelo.Empresa;

import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.EmpresaControle;
import com.autobots.automanager.dto.Empresa.EmpresaExibirDTO;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class AdicionadorLinkEmpresa implements AdicionadorLink<EmpresaExibirDTO> {

    @Override
    public void adicionarLink(List<EmpresaExibirDTO> lista) {
        for (EmpresaExibirDTO empresa : lista) {
            adicionarLink(empresa);
        }
    }

    @Override
    public void adicionarLink(EmpresaExibirDTO objeto) {
        long id = objeto.getId();
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EmpresaControle.class)
                        .obterPorId(id))
                .withSelfRel();
        
        //listagem completa
        Link linkLista = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EmpresaControle.class)
                        .obterTodos())
                .withRel("empresas");

        //criar uma nova empresa
        Link linkCriar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EmpresaControle.class)
                        .cadastrar(null))
                .withRel("criar");
        
        //deletar empresa
        Link linkDeletar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EmpresaControle.class)
                        .excluir(id))
                .withRel("deletar");
        
        //editar empresa
        Link linkEditar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EmpresaControle.class)
                        .atualizar(id, null))
                .withRel("editar");

        objeto.add(linkProprio, linkLista, linkCriar, linkDeletar, linkEditar);
    }
}