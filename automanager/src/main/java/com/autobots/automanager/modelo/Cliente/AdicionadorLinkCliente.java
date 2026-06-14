package com.autobots.automanager.modelo.Cliente;

import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.ClienteControle;
import com.autobots.automanager.dto.Cliente.ClienteExibirDTO;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class AdicionadorLinkCliente implements AdicionadorLink<ClienteExibirDTO> {

    @Override
    public void adicionarLink(List<ClienteExibirDTO> lista) {
        for (ClienteExibirDTO cliente : lista) {
            adicionarLink(cliente);
        }
    }

    @Override
    public void adicionarLink(ClienteExibirDTO objeto) {
        long id = objeto.getId();
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(ClienteControle.class)
                        .obterPorId(id))
                .withSelfRel();
        
        //listagem completa
        Link linkLista = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(ClienteControle.class)
                        .listar())
                .withRel("clientes");

        //criar um novo cliente
        Link linkCriar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(ClienteControle.class)
                        .criar(null))
                .withRel("criar");
        
        //deletar cliente
        Link linkDeletar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(ClienteControle.class)
                        .deletar(id))
                .withRel("deletar");
        
        //editar cliente
        Link linkEditar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(ClienteControle.class)
                        .editar(id, null))
                .withRel("editar");

        objeto.add(linkProprio, linkLista, linkCriar, linkDeletar, linkEditar);
    }
}