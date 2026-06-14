package com.autobots.automanager.modelo.Venda;

import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.VendaControle;
import com.autobots.automanager.dto.Venda.VendaExibirDTO;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class AdicionadorLinkVenda implements AdicionadorLink<VendaExibirDTO> {

    @Override
    public void adicionarLink(List<VendaExibirDTO> lista) {
        for (VendaExibirDTO venda : lista) {
            adicionarLink(venda);
        }
    }

    @Override
    public void adicionarLink(VendaExibirDTO objeto) {
        long id = objeto.getId();
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(VendaControle.class)
                        .obterPorId(id))
                .withSelfRel();
        
        //listagem completa
        Link linkLista = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(VendaControle.class)
                        .obterTodos())
                .withRel("vendas");

        //criar uma nova venda
        Link linkCriar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(VendaControle.class)
                        .cadastrar(null))
                .withRel("criar");
        
        //deletar venda
        Link linkDeletar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(VendaControle.class)
                        .excluir(id))
                .withRel("deletar");
        
        //editar venda
        Link linkEditar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(VendaControle.class)
                        .atualizar(id, null))
                .withRel("editar");

        objeto.add(linkProprio, linkLista, linkCriar, linkDeletar, linkEditar);
    }
}