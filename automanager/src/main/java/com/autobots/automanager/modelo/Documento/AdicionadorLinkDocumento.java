package com.autobots.automanager.modelo.Documento;

import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.DocumentoControle;
import com.autobots.automanager.dto.Documento.DocumentoExibirDTO;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class AdicionadorLinkDocumento implements AdicionadorLink<DocumentoExibirDTO> {

    @Override
    public void adicionarLink(List<DocumentoExibirDTO> lista) {
        for (DocumentoExibirDTO documento : lista) {
            adicionarLink(documento);
        }
    }

    @Override
    public void adicionarLink(DocumentoExibirDTO objeto) {
        long id = objeto.getId();
        // Link para o prprio recurso (Self)
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .obterDocumento(id))
                .withSelfRel();
        
        //listagem completa
        Link linkLista = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .obterDocumentos())
                .withRel("documentos");

        //criar um novo documento
        Link linkCriar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .cadastrar(null))
                .withRel("criar");

        //deletar documento
        Link linkDeletar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .excluirDocumento(id))
                .withRel("deletar");

        //editar documento
        Link linkEditar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .atualizarDocumento(id, null))
                .withRel("editar");

        objeto.add(linkProprio, linkLista, linkCriar, linkDeletar, linkEditar);
    }
}