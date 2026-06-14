package com.autobots.automanager.modeladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.CredencialCodigoBarra.CredencialQRExibirDTO;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class CredencialCodigoBarraModelador implements RepresentationModelAssembler<CredencialQRExibirDTO, CredencialQRExibirDTO> {

    @Autowired
    private AdicionadorLink<CredencialQRExibirDTO> adicionadorLink;

    @Override
    public CredencialQRExibirDTO toModel(CredencialQRExibirDTO dto) {
        adicionadorLink.adicionarLink(dto);
        return dto;
    }
}