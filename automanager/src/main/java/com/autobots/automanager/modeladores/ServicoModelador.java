package com.autobots.automanager.modeladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.Servico.ServicoExibirDTO;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class ServicoModelador implements RepresentationModelAssembler<ServicoExibirDTO, ServicoExibirDTO> {

    @Autowired
    private AdicionadorLink<ServicoExibirDTO> adicionadorLink;

    @Override
    public ServicoExibirDTO toModel(ServicoExibirDTO dto) {
        adicionadorLink.adicionarLink(dto);
        return dto;
    }
}