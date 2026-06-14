package com.autobots.automanager.modelo.CredencialCodigoBarra;

import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.CredencialCodigoBarraControle; // Ajuste conforme seu pacote de controles
import com.autobots.automanager.dto.CredencialCodigoBarra.CredencialQRExibirDTO;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class AdicionadorLinkCredencialBarra implements AdicionadorLink<CredencialQRExibirDTO> {

    @Override
    public void adicionarLink(List<CredencialQRExibirDTO> lista) {
        for (CredencialQRExibirDTO dto : lista) {
            adicionarLink(dto);
        }
    }

    @Override
    public void adicionarLink(CredencialQRExibirDTO objeto) {
        long id = objeto.getId();
        
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(CredencialCodigoBarraControle.class)
                        .obterPorId(id))
                .withSelfRel();
        
        Link linkDeletar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(CredencialCodigoBarraControle.class)
                        .excluir(id))
                .withRel("deletar");

        objeto.add(linkProprio, linkDeletar);
    }
}