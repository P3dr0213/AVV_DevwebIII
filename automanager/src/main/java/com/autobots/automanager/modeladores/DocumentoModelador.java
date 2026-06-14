package com.autobots.automanager.modeladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.Documento.DocumentoExibirDTO;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class DocumentoModelador implements RepresentationModelAssembler<DocumentoExibirDTO, DocumentoExibirDTO> {

    @Autowired
    private AdicionadorLink<DocumentoExibirDTO> adicionadorLink;

    @Override
    public DocumentoExibirDTO toModel(DocumentoExibirDTO documentoDto) {
        adicionadorLink.adicionarLink(documentoDto);
        return documentoDto;
    }
}
