package com.autobots.automanager.modelo.Usuario;

import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.UsuarioControle;
import com.autobots.automanager.dto.Usuario.UsuarioExibirDTO;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class AdicionadorLinkUsuario implements AdicionadorLink<UsuarioExibirDTO> {

    @Override
    public void adicionarLink(List<UsuarioExibirDTO> lista) {
        for (UsuarioExibirDTO usuario : lista) {
            adicionarLink(usuario);
        }
    }

    @Override
    public void adicionarLink(UsuarioExibirDTO objeto) {
        long id = objeto.getId();
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(UsuarioControle.class)
                        .obterPorId(id))
                .withSelfRel();
        
        //listagem completa
        Link linkLista = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(UsuarioControle.class)
                        .obterTodos())
                .withRel("usuarios");

        //criar um novo usuario
        Link linkCriar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(UsuarioControle.class)
                        .cadastrar(null))
                .withRel("criar");
        
        //deletar usuario
        Link linkDeletar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(UsuarioControle.class)
                        .excluir(id))
                .withRel("deletar");
        
        //editar usuario
        Link linkEditar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(UsuarioControle.class)
                        .atualizar(id, null))
                .withRel("editar");

        objeto.add(linkProprio, linkLista, linkCriar, linkDeletar, linkEditar);
    }
}