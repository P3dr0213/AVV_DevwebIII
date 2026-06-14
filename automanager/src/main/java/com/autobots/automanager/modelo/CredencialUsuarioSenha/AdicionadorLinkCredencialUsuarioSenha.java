package com.autobots.automanager.modelo.CredencialUsuarioSenha;

import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.CredencialUsuarioSenhaControle;
import com.autobots.automanager.dto.CredencialUsuarioSenha.CredencialUsuarioSenhaExibirDTO;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class AdicionadorLinkCredencialUsuarioSenha implements AdicionadorLink<CredencialUsuarioSenhaExibirDTO> {

    @Override
    public void adicionarLink(List<CredencialUsuarioSenhaExibirDTO> lista) {
        for (CredencialUsuarioSenhaExibirDTO dto : lista) {
            adicionarLink(dto);
        }
    }

    @Override
    public void adicionarLink(CredencialUsuarioSenhaExibirDTO objeto) {
        long id = objeto.getId();
        
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(CredencialUsuarioSenhaControle.class)
                        .obterPorId(id))
                .withSelfRel();
        
        Link linkLista = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(CredencialUsuarioSenhaControle.class)
                        .obterTodos())
                .withRel("credenciais-usuario");

        Link linkDeletar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(CredencialUsuarioSenhaControle.class)
                        .excluir(id))
                .withRel("excluir");

        objeto.add(linkProprio, linkLista, linkDeletar);
    }
}