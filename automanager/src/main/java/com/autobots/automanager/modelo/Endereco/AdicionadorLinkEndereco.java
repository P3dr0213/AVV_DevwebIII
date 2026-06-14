package com.autobots.automanager.modelo.Endereco;

import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.EnderecoControle;
import com.autobots.automanager.dto.Endereco.EnderecoExibirDTO;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class AdicionadorLinkEndereco implements AdicionadorLink<EnderecoExibirDTO> {

    @Override
    public void adicionarLink(List<EnderecoExibirDTO> lista) {
        for (EnderecoExibirDTO endereco : lista) {
            adicionarLink(endereco);
        }
    }

    @Override
    public void adicionarLink(EnderecoExibirDTO objeto) {
        long id = objeto.getId();
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EnderecoControle.class)
                        .obterPorId(id))
                .withSelfRel();
        
        //listagem completa
        Link linkLista = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EnderecoControle.class)
                        .listar())
                .withRel("enderecos");

        //criar um novo endereco
        Link linkCriar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EnderecoControle.class)
                        .criar(null))
                .withRel("criar");
        
        //deletar endereco
        Link linkDeletar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EnderecoControle.class)
                        .deletar(id))
                .withRel("deletar");

        //editar endereco
        Link linkEditar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EnderecoControle.class)
                        .editar(id, null))
                .withRel("editar");

        objeto.add(linkProprio, linkLista, linkCriar, linkDeletar, linkEditar);
    }
}